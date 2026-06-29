using System.Net.Http.Json;
using System.Text.Json;
using System.Text.Json.Serialization;
using System.Text.RegularExpressions;
using Microsoft.Extensions.Configuration;
using GigaChadSys.Servicios.DTO;

namespace GigaChadSys.Servicios;

public class AsistenteSocioServicio
{
    private readonly HttpClient _httpClient;
    private readonly IConfiguration _configuration;
    private readonly SessionServicio _session;
    private readonly SocioServicio _socioServicio;
    private readonly SuscripcionServicio _suscripcionServicio;
    private readonly ReservaServicio _reservaServicio;
    private readonly SesionClaseServicio _sesionClaseServicio;
    private readonly PagoServicio _pagoServicio;
    private readonly MetodoPagoServicio _metodoPagoServicio;
    private readonly MembresiaBasicServicio _membresiaBasicServicio;
    private readonly MembresiaBlackServicio _membresiaBlackServicio;

    private int _usuarioIdContextoCargado;
    private bool _esperandoSeleccionReserva;

    public AsistenteSocioServicio(
        HttpClient httpClient,
        IConfiguration configuration,
        SessionServicio session,
        SocioServicio socioServicio,
        SuscripcionServicio suscripcionServicio,
        ReservaServicio reservaServicio,
        SesionClaseServicio sesionClaseServicio,
        PagoServicio pagoServicio,
        MetodoPagoServicio metodoPagoServicio,
        MembresiaBasicServicio membresiaBasicServicio,
        MembresiaBlackServicio membresiaBlackServicio)
    {
        _httpClient = httpClient;
        _configuration = configuration;
        _session = session;
        _socioServicio = socioServicio;
        _suscripcionServicio = suscripcionServicio;
        _reservaServicio = reservaServicio;
        _sesionClaseServicio = sesionClaseServicio;
        _pagoServicio = pagoServicio;
        _metodoPagoServicio = metodoPagoServicio;
        _membresiaBasicServicio = membresiaBasicServicio;
        _membresiaBlackServicio = membresiaBlackServicio;
    }

    public List<AsistenteMensajeDTO> Conversacion { get; } = new();

    public string NombreUsuario => ObtenerNombreUsuario();

    public SocioDTO? SocioActual { get; private set; }
    public SuscripcionDTO? SuscripcionActual { get; private set; }
    public List<ReservaDTO> ReservasActuales { get; private set; } = new();
    public List<SesionClaseDTO> ClasesDisponibles { get; private set; } = new();
    public List<PagoDTO> PagosActuales { get; private set; } = new();
    public List<MetodoPagoDTO> MetodosPagoActuales { get; private set; } = new();
    public List<MembresiaBasicDTO> MembresiasBasic { get; private set; } = new();
    public List<MembresiaBlackDTO> MembresiasBlack { get; private set; } = new();

    public async Task CargarContextoAsync(bool forzarRecarga = false)
    {
        if (!_session.EstaAutenticado || !_session.EsSocio)
        {
            LimpiarContextoSiCambioDeSesion();
            return;
        }

        if (!forzarRecarga && _usuarioIdContextoCargado == _session.IdUsuario && SocioActual is not null && SuscripcionActual is not null)
            return;

        var tareas = new List<Task>
        {
            CargarSocioActualAsync(),
            CargarSuscripcionActualAsync(),
            CargarReservasActualesAsync(),
            CargarClasesDisponiblesAsync(),
            CargarPagosActualesAsync(),
            CargarCatalogosMembresiaAsync()
        };

        await Task.WhenAll(tareas);

        if (Conversacion.Count == 0)
        {
            AgregarMensajeSistema($"Hola, {NombreUsuario}. Puedo resumirte tu membresia, decirte tu estado actual y orientarte sobre pagos o reservas.");
        }

        _usuarioIdContextoCargado = _session.IdUsuario;
    }

    public async Task<string> ResponderAsync(string pregunta)
    {
        await CargarContextoAsync(true);

        string texto = pregunta.Trim();
        if (string.IsNullOrWhiteSpace(texto))
        {
            return "Escribe una pregunta corta sobre tu membresia, pagos o reservas.";
        }

        if (EsConsultaMisReservas(texto))
        {
            string respuestaReservas = FormatoMisReservas();
            AgregarMensajeUsuario(texto);
            AgregarMensajeSistema(respuestaReservas);
            return respuestaReservas;
        }

        if (EsConsultaClasesDisponibles(texto))
        {
            string respuestaClases = FormatoClasesDisponibles();
            AgregarMensajeUsuario(texto);
            AgregarMensajeSistema(respuestaClases);
            return respuestaClases;
        }

        if (_esperandoSeleccionReserva || EsSolicitudReservaNueva(texto))
        {
            string respuestaReserva = await IntentarReservarClaseAsync(texto);
            AgregarMensajeUsuario(texto);
            AgregarMensajeSistema(respuestaReserva);
            return respuestaReserva;
        }

        if (Contiene(texto, "pago", "pagos", "cobro", "monto", "cuota", "factura", "metodo de pago", "método de pago"))
        {
            string respuestaPagos = FormatoPagosActuales();
            AgregarMensajeUsuario(texto);
            AgregarMensajeSistema(respuestaPagos);
            return respuestaPagos;
        }

        AgregarMensajeUsuario(texto);

        string respuesta = await GenerarRespuestaConGeminiAsync(texto);
        AgregarMensajeSistema(respuesta);

        return respuesta;
    }

    public void ReiniciarConversacion()
    {
        Conversacion.Clear();
        _usuarioIdContextoCargado = 0;
        _esperandoSeleccionReserva = false;
    }

    private async Task CargarSocioActualAsync()
    {
        if (_session.IdUsuario <= 0)
            return;

        SocioActual = await _socioServicio.ObtenerPorIdAsync(_session.IdUsuario);
    }

    private async Task CargarSuscripcionActualAsync()
    {
        if (_session.IdUsuario <= 0)
            return;

        var suscripciones = await _suscripcionServicio.ListarSuscripcionesAsync();

        SuscripcionActual = suscripciones
            .Where(s => s.IdUsuario == _session.IdUsuario)
            .OrderByDescending(s => s.FechaIngreso ?? DateTime.MinValue)
            .FirstOrDefault();
    }

    private async Task CargarReservasActualesAsync()
    {
        if (_session.IdUsuario <= 0)
            return;

        var reservas = await _reservaServicio.ListarReservasAsync();

        ReservasActuales = reservas
            .Where(r => r.IdUsuario == _session.IdUsuario)
            .OrderByDescending(r => r.FechaHoraReserva ?? DateTime.MinValue)
            .ToList();
    }

    private async Task CargarClasesDisponiblesAsync()
    {
        var sesiones = await _sesionClaseServicio.ListarSesionesAsync();

        ClasesDisponibles = sesiones
            .Where(s => s.Activo && s.CuposDisponibles > 0 && (s.FechaHoraInicio == default || s.FechaHoraInicio >= DateTime.Now))
            .OrderBy(s => s.FechaHoraInicio == default ? DateTime.MaxValue : s.FechaHoraInicio)
            .Take(10)
            .ToList();
    }

    private async Task CargarPagosActualesAsync()
    {
        if (_session.IdUsuario <= 0)
            return;

        var suscripciones = await _suscripcionServicio.ListarSuscripcionesAsync();
        var misSuscripciones = suscripciones
            .Where(s => s.IdUsuario == _session.IdUsuario)
            .ToList();

        var misIdsPagos = misSuscripciones
            .Select(s => s.IdPago)
            .Distinct()
            .ToList();

        if (misIdsPagos.Count == 0)
        {
            PagosActuales = new List<PagoDTO>();
            MetodosPagoActuales = new List<MetodoPagoDTO>();
            return;
        }

        var todosPagos = await _pagoServicio.ListarPagosAsync();
        var metodos = await _metodoPagoServicio.ListarMetodosDePagoAsync();

        MetodosPagoActuales = metodos
            .Where(m => m.Activo)
            .ToList();

        PagosActuales = todosPagos
            .Where(p => misIdsPagos.Contains(p.IdPago))
            .OrderByDescending(p => p.FechaPago ?? DateTime.MinValue)
            .ThenByDescending(p => p.IdPago)
            .ToList();
    }

    private async Task CargarCatalogosMembresiaAsync()
    {
        var basic = await _membresiaBasicServicio.ListarMembresiasBasicAsync();
        var black = await _membresiaBlackServicio.ListarMembresiasBlackAsync();

        MembresiasBasic = basic
            .Where(m => m.Activa)
            .OrderBy(m => m.CostoMantenimientoMensual)
            .ToList();

        MembresiasBlack = black
            .Where(m => m.Activa)
            .OrderBy(m => m.CostoMantenimientoAnual)
            .ToList();
    }

    private void LimpiarContextoSiCambioDeSesion()
    {
        if (_usuarioIdContextoCargado == 0)
            return;

        _usuarioIdContextoCargado = 0;
        SocioActual = null;
        SuscripcionActual = null;
        ReservasActuales.Clear();
        ClasesDisponibles.Clear();
        PagosActuales.Clear();
        MetodosPagoActuales.Clear();
        MembresiasBasic.Clear();
        MembresiasBlack.Clear();
    }

    private async Task<string> GenerarRespuestaConGeminiAsync(string pregunta)
    {
        string apiKey = ObtenerApiKeyGemini();
        if (string.IsNullOrWhiteSpace(apiKey))
            return GenerarRespuestaLocal(pregunta, "Falta configurar Gemini: agrega tu API key en appsettings.json en la seccion Gemini:ApiKey.");

        string model = ObtenerModeloGemini();
        string systemPrompt = ObtenerSystemPrompt();
        string contexto = ConstruirContextoParaIA();

        var request = new GeminiGenerateContentRequest
        {
            SystemInstruction = new GeminiContent
            {
                Parts = new List<GeminiPart>
                {
                    new() { Text = systemPrompt }
                }
            },
            Contents = new List<GeminiContent>
            {
                new()
                {
                    Role = "user",
                    Parts = new List<GeminiPart>
                    {
                        new()
                        {
                            Text = $"Contexto del socio:\n{contexto}\n\nPregunta del usuario: {pregunta}"
                        }
                    }
                }
            }
        };

        try
        {
            string url = $"https://generativelanguage.googleapis.com/v1beta/models/{model}:generateContent?key={apiKey}";
            var response = await _httpClient.PostAsJsonAsync(url, request);

            if (!response.IsSuccessStatusCode)
            {
                string error = await response.Content.ReadAsStringAsync();
                if (response.StatusCode == System.Net.HttpStatusCode.TooManyRequests)
                    return GenerarRespuestaLocal(pregunta, "Gemini no tiene cuota disponible en este momento.");

                return GenerarRespuestaLocal(pregunta, $"Gemini respondio con error HTTP {(int)response.StatusCode}.");
            }

            var result = await response.Content.ReadFromJsonAsync<GeminiGenerateContentResponse>();
            string? respuesta = result?.Candidates?
                .FirstOrDefault()?
                .Content?
                .Parts?
                .FirstOrDefault()?
                .Text;

            if (string.IsNullOrWhiteSpace(respuesta))
                return GenerarRespuestaLocal(pregunta, "Gemini no devolvio texto util.");

            return respuesta.Trim();
        }
        catch (Exception ex)
        {
            return GenerarRespuestaLocal(pregunta, ex.Message);
        }
    }

    private string GenerarRespuestaLocal(string pregunta, string motivo)
    {
        string texto = pregunta.ToLowerInvariant();

        if (Contiene(texto, "hola", "buenas", "saludos", "ayuda"))
            return $"Hola, {NombreUsuario}. {FormatoEstadoCuenta()}";

        if (Contiene(texto, "nombre", "quien soy", "quién soy", "perfil"))
            return $"Tu perfil actual es {NombreUsuario}. {FormatoEstadoCuenta()}";

        if (Contiene(texto, "membres", "plan", "estado", "suscrip", "renovar"))
            return FormatoMembresia();

        if (Contiene(texto, "pago", "pagos", "cobro", "monto"))
            return FormatoPagos();

        if (EsConsultaMisReservas(texto))
            return FormatoMisReservas();

        if (EsConsultaClasesDisponibles(texto))
            return FormatoClasesDisponibles();

        if (EsSolicitudReservaNueva(texto))
            return FormatoClasesDisponibles();

        if (Contiene(texto, "basic", "black"))
            return FormatoPlanesDisponibles();

        return $"Ahora mismo Gemini no pudo responder ({motivo}). {FormatoEstadoCuenta()} Puedo ayudarte con membresia, pagos, reservas o perfil.";
    }

    private string ObtenerApiKeyGemini() =>
        _configuration["Gemini:ApiKey"]?.Trim() ?? string.Empty;

    private string ObtenerModeloGemini()
    {
        string modelo = _configuration["Gemini:Model"]?.Trim() ?? string.Empty;
        return string.IsNullOrWhiteSpace(modelo)
            ? "gemini-3.1-flash-lite"
            : modelo;
    }

    private string ObtenerSystemPrompt() =>
        _configuration["Gemini:SystemPrompt"]?.Trim()
        ?? "Eres un asistente breve, claro y convincente para un gimnasio. Solo ayudas con membresias, pagos, reservas, perfil y dudas del socio. Usa el contexto dado. Si falta un dato, dilo con honestidad. Nunca inventes pagos, fechas ni planes.";

    private string ConstruirContextoParaIA()
    {
        var partes = new List<string>
        {
            $"Nombre: {NombreUsuario}",
            $"Autenticado: {_session.EstaAutenticado}",
            $"Rol: {_session.Rol}",
            $"ID usuario: {_session.IdUsuario}",
            $"Estado socio: {(SocioActual is null ? "no cargado" : (SocioActual.EstadoMembresia ? "membresia activa" : "membresia inactiva"))}",
            $"Suscripcion: {(SuscripcionActual is null ? "sin suscripcion encontrada" : $"{SuscripcionActual.TipoPlan} | pago #{SuscripcionActual.IdPago} | {SuscripcionActual.FechaInicioTexto} a {SuscripcionActual.FechaFinTexto}")}",
            $"Pagos: {(PagosActuales.Count == 0 ? "sin pagos encontrados" : string.Join("; ", PagosActuales.Take(5).Select(p => $"#{p.IdPago} {p.Tipo} | {p.MontoTexto} | {p.FechaPagoTexto} | {(p.Activo ? "pagado" : "pendiente")}")))}",
            $"Reservas: {(ReservasActuales.Count == 0 ? "sin reservas encontradas" : string.Join("; ", ReservasActuales.Take(5).Select(r => $"#{r.IdReserva} {r.FechaTexto} | {r.EstadoTexto} | {r.SesionClase?.ClaseGrupal?.Nombre ?? "Sesion"} | {r.SesionClase?.FechaTexto ?? "—"} {r.SesionClase?.HorarioTexto ?? "—"}")))}",
            $"Clases disponibles: {(ClasesDisponibles.Count == 0 ? "no hay sesiones activas" : string.Join("; ", ClasesDisponibles.Take(5).Select(s => $"{s.ClaseGrupal?.Nombre ?? "Clase"} | {s.FechaTexto} | {s.HorarioTexto} | cupos {s.CuposDisponibles} | {s.Salon?.NombreSalon ?? "Sin salon"}")))}",
            $"Planes Basic: {(MembresiasBasic.Count == 0 ? "no disponibles" : string.Join("; ", MembresiasBasic.Select(m => $"{m.Nombre} S/{m.CostoMantenimientoMensual:0.00}/mes")))}",
            $"Planes Black: {(MembresiasBlack.Count == 0 ? "no disponibles" : string.Join("; ", MembresiasBlack.Select(m => $"{m.Nombre} S/{m.CostoMantenimientoAnual:0.00}/anio")))}"
        };

        var ultimosMensajes = Conversacion
            .TakeLast(8)
            .Select(m => $"{m.Rol}: {m.Texto}");

        partes.Add("Historial reciente: " + string.Join(" | ", ultimosMensajes));

        return string.Join("\n", partes);
    }

    private string RespuestaSaludo()
    {
        if (_session.EstaAutenticado)
            return $"Hola, {NombreUsuario}. {FormatoEstadoCuenta()}";

        return "Hola. Inicia sesion para que pueda leer tu perfil y tu estado de membresia.";
    }

    private string FormatoEstadoCuenta()
    {
        if (SocioActual is null)
            return "No pude leer tu ficha de socio todavia.";

        string estado = SocioActual.EstadoMembresia ? "activa" : "inactiva";
        string plan = SuscripcionActual?.TipoPlan ?? (SocioActual.EstadoMembresia ? "activo" : "sin plan confirmado");

        if (SuscripcionActual is not null)
        {
            return $"Tu membresia figura como {estado} y tu plan registrado es {plan}. Vigencia: {SuscripcionActual.FechaInicioTexto} a {SuscripcionActual.FechaFinTexto}.";
        }

        return $"Tu membresia figura como {estado}, pero todavia no encontre una suscripcion activa para tu usuario.";
    }

    private string FormatoMembresia()
    {
        if (SuscripcionActual is not null)
        {
            string plan = SuscripcionActual.TipoPlan;
            string vigencia = $"Vigencia {SuscripcionActual.FechaInicioTexto} a {SuscripcionActual.FechaFinTexto}";
            return $"Tu plan actual es {plan}. {vigencia}. {FormatoPlanesDisponibles()}";
        }

        return $"Aun no veo una suscripcion activa para tu usuario. {FormatoPlanesDisponibles()}";
    }

    private string FormatoPagos()
    {
        if (PagosActuales.Count == 0)
            return "No encontre pagos asociados todavia. Desde Mis Pagos podras revisar tu historial y confirmar tu ultimo pago.";

        return FormatoPagosActuales();
    }

    private string FormatoPagosActuales()
    {
        if (PagosActuales.Count == 0)
            return "No encontre pagos asociados todavia. Desde Mis Pagos podras revisar tu historial y confirmar tu ultimo pago.";

        var metodos = MetodosPagoActuales
            .ToDictionary(m => m.IdMetodoPago, m => m.Tipo);

        var resumen = PagosActuales.Take(5).Select(p =>
        {
            string metodo = metodos.TryGetValue(p.MetodoPago, out var nombreMetodo)
                ? nombreMetodo
                : (p.MetodoPago == 1 ? "Efectivo" : p.MetodoPago == 2 ? "Tarjeta Debito" : p.MetodoPago == 3 ? "Tarjeta Credito" : "No definido");

            string estado = p.Activo ? "pagado" : "pendiente";
            return $"{p.IdPago}. {p.Tipo}\n   Monto: {p.MontoTexto}\n   Fecha: {p.FechaPagoTexto}\n   Metodo: {metodo}\n   Estado: {estado}";
        });

        return "Pagos actualizados:\n" + string.Join("\n\n", resumen);
    }

    private string FormatoMisReservas()
    {
        if (ReservasActuales.Count == 0)
            return "No encuentro reservas activas ni historial para tu cuenta. Si quieres, puedo mostrarte clases disponibles para reservar.";

        var ahora = DateTime.Now;

        var activas = ReservasActuales
            .Where(r => r.Activo && r.SesionClase != null && r.SesionClase.FechaHoraFin != default && r.SesionClase.FechaHoraFin >= ahora)
            .OrderBy(r => r.SesionClase!.FechaHoraInicio)
            .ThenBy(r => r.IdReserva)
            .Take(5)
            .ToList();

        var historial = ReservasActuales
            .Where(r => !r.Activo || r.Asistio || r.SesionClase == null || r.SesionClase.FechaHoraFin == default || r.SesionClase.FechaHoraFin < ahora)
            .OrderByDescending(r => r.SesionClase?.FechaHoraInicio ?? DateTime.MinValue)
            .ThenByDescending(r => r.IdReserva)
            .Take(5)
            .ToList();

        string FormatearReserva(ReservaDTO r)
        {
            string nombreClase = r.SesionClase?.ClaseGrupal?.Nombre ?? r.SesionClase?.ClaseGrupal?.NombreDisciplina ?? "Clase";
            string fecha = r.SesionClase?.FechaTexto ?? "—";
            string hora = r.SesionClase?.HorarioTexto ?? "—";
            string salon = r.SesionClase?.Salon?.NombreSalon ?? "Sin salon";
            string estado = r.Activo ? (r.Asistio ? "Completada" : "Confirmada") : "Cancelada";
            return $"#{r.IdReserva} {nombreClase}\n   Fecha: {fecha}\n   Hora: {hora}\n   Salon: {salon}\n   Estado: {estado}";
        }

        string resumen = activas.Count > 0
            ? "Reservas activas:\n" + string.Join("\n", activas.Select((r, i) => $"{i + 1}. {FormatearReserva(r)}"))
            : "Reservas activas:\n- No tienes reservas activas.";

        string detalleHistorial = historial.Count > 0
            ? "\nHistorial reciente:\n" + string.Join("\n", historial.Select((r, i) => $"{i + 1}. {FormatearReserva(r)}"))
            : string.Empty;

        return resumen + detalleHistorial;
    }

    private string FormatoClasesDisponibles()
    {
        if (ClasesDisponibles.Count == 0)
            return "No hay clases disponibles para reservar en este momento.";

        var resumen = ClasesDisponibles.Take(5).Select((s, index) =>
        {
            string nombreClase = s.ClaseGrupal?.Nombre ?? s.ClaseGrupal?.NombreDisciplina ?? "Clase";
            string disciplina = s.ClaseGrupal?.Disciplina ?? s.ClaseGrupal?.NombreDisciplina ?? "General";
            string salon = s.Salon?.NombreSalon ?? "Sin salon";
            return $"{index + 1}. #{s.IdSesion} {nombreClase} ({disciplina})\n   Fecha: {s.FechaTexto}\n   Hora: {s.HorarioTexto}\n   Cupos: {s.CuposDisponibles}\n   Salon: {salon}";
        });

        return "Clases disponibles:\n" + string.Join("\n", resumen);
    }

    private async Task<string> IntentarReservarClaseAsync(string texto)
    {
        if (!_session.EstaAutenticado || !_session.EsSocio)
            return "Debes iniciar sesion como socio para reservar clases.";

        if (SocioActual is null)
            return "No pude leer tu ficha de socio todavia.";

        if (!SocioActual.EstadoMembresia || SuscripcionActual is null || !SuscripcionActual.EsActiva)
            return "Tu membresia no esta activa, asi que no puedo crear la reserva desde el bot. Primero debes activar tu plan.";

        var sesionSeleccionada = ResolverSesionParaReserva(texto);

        if (sesionSeleccionada is null)
        {
            _esperandoSeleccionReserva = true;
            string opciones = ClasesDisponibles.Count == 0
                ? "No encontre clases disponibles en este momento."
                : string.Join("\n", ClasesDisponibles.Take(5).Select((s, index) =>
                {
                    string nombreClase = s.ClaseGrupal?.Nombre ?? s.ClaseGrupal?.NombreDisciplina ?? "Clase";
                    return $"{index + 1}. #{s.IdSesion} {nombreClase}\n   Fecha: {s.FechaTexto}\n   Hora: {s.HorarioTexto}\n   Cupos: {s.CuposDisponibles}";
                }));

            return $"No pude identificar la clase exacta. Responde con el numero de sesion o el nombre de la clase.\n{opciones}";
        }

        if (sesionSeleccionada.CuposDisponibles <= 0)
        {
            _esperandoSeleccionReserva = true;
            return "Esa clase esta llena y no puedo registrarte desde el bot.";
        }

        var reservas = await _reservaServicio.ListarReservasAsync();
        bool yaExisteMismaClase = reservas.Any(r =>
            r.Activo &&
            r.IdUsuario == _session.IdUsuario &&
            r.SesionClase?.ClaseGrupal?.IdClase == sesionSeleccionada.ClaseGrupal?.IdClase &&
            r.SesionClase.FechaHoraFin != default &&
            r.SesionClase.FechaHoraFin >= DateTime.Now);

        if (yaExisteMismaClase)
        {
            _esperandoSeleccionReserva = false;
            return "Ya tienes una reserva activa para esa misma clase.";
        }

        var nuevaReserva = new ReservaDTO
        {
            IdUsuario = _session.IdUsuario,
            FechaHoraReserva = DateTime.Now,
            Asistio = false,
            Activo = true,
            SesionClase = new SesionClaseDTO { IdSesion = sesionSeleccionada.IdSesion }
        };

        var resultado = await _reservaServicio.RegistrarAsync(nuevaReserva);

        if (resultado.StartsWith("Error", StringComparison.OrdinalIgnoreCase) || resultado.Contains("error", StringComparison.OrdinalIgnoreCase))
        {
            _esperandoSeleccionReserva = true;
            return resultado;
        }

        await CargarContextoAsync(true);
        _esperandoSeleccionReserva = false;

        string nombreClase = sesionSeleccionada.ClaseGrupal?.Nombre ?? sesionSeleccionada.ClaseGrupal?.NombreDisciplina ?? "Clase";
        return $"Reserva creada correctamente:\nClase: {nombreClase}\nSesion: #{sesionSeleccionada.IdSesion}\nFecha: {sesionSeleccionada.FechaTexto}\nHorario: {sesionSeleccionada.HorarioTexto}\nSalon: {sesionSeleccionada.Salon?.NombreSalon ?? "Sin salon"}";
    }

    private SesionClaseDTO? ResolverSesionParaReserva(string texto)
    {
        var numero = Regex.Match(texto, @"\b\d+\b");
        if (numero.Success && int.TryParse(numero.Value, out int idSesion))
        {
            var porId = ClasesDisponibles.FirstOrDefault(s => s.IdSesion == idSesion);
            if (porId is not null)
                return porId;
        }

        string textoNormalizado = NormalizarTexto(texto);
        var coincidencias = ClasesDisponibles
            .Select(s => new
            {
                Sesion = s,
                Puntaje = CalcularPuntajeSesion(textoNormalizado, s)
            })
            .Where(x => x.Puntaje > 0)
            .OrderByDescending(x => x.Puntaje)
            .ThenBy(x => x.Sesion.FechaHoraInicio == default ? DateTime.MaxValue : x.Sesion.FechaHoraInicio)
            .ToList();

        if (coincidencias.Count == 0)
            return null;

        int mejorPuntaje = coincidencias[0].Puntaje;
        var mejores = coincidencias
            .Where(x => x.Puntaje == mejorPuntaje)
            .Select(x => x.Sesion)
            .OrderBy(s => s.FechaHoraInicio == default ? DateTime.MaxValue : s.FechaHoraInicio)
            .ThenBy(s => s.IdSesion)
            .ToList();

        return mejores.FirstOrDefault();
    }

    private static bool EsConsultaMisReservas(string texto)
    {
        return Contiene(texto,
            "mis reservas",
            "que reservas tengo",
            "cuales son mis reservas",
            "cuáles son mis reservas",
            "reservas tengo",
            "ver mis reservas",
            "historial de reservas");
    }

    private static bool EsConsultaClasesDisponibles(string texto)
    {
        return Contiene(texto,
            "clases disponibles",
            "que clases hay",
            "qué clases hay",
            "clases hay para reservar",
            "ver clases",
            "ver sesiones");
    }

    private static bool EsSolicitudReservaNueva(string texto)
    {
        return Regex.IsMatch(texto, @"\breservar\b|\breservame\b|\breserva la\b|\breserva el\b|\bapartar\b|\binscrib")
               && !EsConsultaMisReservas(texto)
               && !EsConsultaClasesDisponibles(texto);
    }

    private static string NormalizarTexto(string texto)
    {
        string textoNormalizado = texto.ToLowerInvariant().Trim();
        textoNormalizado = textoNormalizado.Normalize(System.Text.NormalizationForm.FormD);

        var filtrado = textoNormalizado
            .Where(c => System.Globalization.CharUnicodeInfo.GetUnicodeCategory(c) != System.Globalization.UnicodeCategory.NonSpacingMark)
            .ToArray();

        textoNormalizado = new string(filtrado);

        textoNormalizado = Regex.Replace(textoNormalizado, @"[^a-z0-9\s]", " ");
        textoNormalizado = Regex.Replace(textoNormalizado, @"\s+", " ").Trim();

        return textoNormalizado;
    }

    private static int CalcularPuntajeSesion(string textoNormalizado, SesionClaseDTO sesion)
    {
        var etiquetas = new[]
        {
            sesion.ClaseGrupal?.Nombre,
            sesion.ClaseGrupal?.NombreDisciplina,
            sesion.Salon?.NombreSalon,
            sesion.Entrenador is null ? null : $"{sesion.Entrenador.Nombres} {sesion.Entrenador.ApellidoPaterno}".Trim()
        };

        int mejorPuntaje = 0;

        foreach (var etiqueta in etiquetas)
        {
            if (string.IsNullOrWhiteSpace(etiqueta))
                continue;

            string etiquetaNormalizada = NormalizarTexto(etiqueta);

            if (textoNormalizado.Contains(etiquetaNormalizada))
                return 100;

            foreach (var palabra in etiquetaNormalizada.Split(' ', StringSplitOptions.RemoveEmptyEntries))
            {
                if (textoNormalizado.Contains(palabra))
                    mejorPuntaje = Math.Max(mejorPuntaje, 40);

                foreach (var palabraTexto in textoNormalizado.Split(' ', StringSplitOptions.RemoveEmptyEntries))
                {
                    int distancia = CalcularDistanciaEdicion(palabraTexto, palabra);
                    if (distancia <= 1)
                        mejorPuntaje = Math.Max(mejorPuntaje, 70);
                    else if (distancia == 2)
                        mejorPuntaje = Math.Max(mejorPuntaje, 50);
                }
            }
        }

        return mejorPuntaje;
    }

    private static int CalcularDistanciaEdicion(string izquierda, string derecha)
    {
        if (string.IsNullOrEmpty(izquierda))
            return derecha.Length;

        if (string.IsNullOrEmpty(derecha))
            return izquierda.Length;

        var matriz = new int[izquierda.Length + 1, derecha.Length + 1];

        for (int i = 0; i <= izquierda.Length; i++)
            matriz[i, 0] = i;

        for (int j = 0; j <= derecha.Length; j++)
            matriz[0, j] = j;

        for (int i = 1; i <= izquierda.Length; i++)
        {
            for (int j = 1; j <= derecha.Length; j++)
            {
                int costo = izquierda[i - 1] == derecha[j - 1] ? 0 : 1;
                matriz[i, j] = Math.Min(
                    Math.Min(matriz[i - 1, j] + 1, matriz[i, j - 1] + 1),
                    matriz[i - 1, j - 1] + costo);
            }
        }

        return matriz[izquierda.Length, derecha.Length];
    }

    private string FormatoPlanesDisponibles()
    {
        string basic = MembresiasBasic.Count > 0
            ? string.Join(", ", MembresiasBasic.Select(m => $"{m.Nombre} S/{m.CostoMantenimientoMensual:0.00}/mes"))
            : "Basic no disponible";

        string black = MembresiasBlack.Count > 0
            ? string.Join(", ", MembresiasBlack.Select(m => $"{m.Nombre} S/{m.CostoMantenimientoAnual:0.00}/anio"))
            : "Black no disponible";

        return $"Planes disponibles: {basic}. {black}.";
    }

    private void AgregarMensajeUsuario(string texto) =>
        Conversacion.Add(new AsistenteMensajeDTO("usuario", texto));

    private void AgregarMensajeSistema(string texto) =>
        Conversacion.Add(new AsistenteMensajeDTO("asistente", texto));

    private string ObtenerNombreUsuario()
    {
        if (!string.IsNullOrWhiteSpace(_session.Nombres))
            return _session.Nombres;

        return SocioActual?.Nombres ?? "Socio";
    }

    private static bool Contiene(string texto, params string[] palabras)
    {
        return palabras.Any(palabra => texto.Contains(palabra, StringComparison.OrdinalIgnoreCase));
    }
}

public sealed record AsistenteMensajeDTO(string Rol, string Texto);

public sealed class GeminiGenerateContentRequest
{
    [JsonPropertyName("systemInstruction")]
    public GeminiContent? SystemInstruction { get; set; }

    [JsonPropertyName("contents")]
    public List<GeminiContent> Contents { get; set; } = new();

    [JsonPropertyName("generationConfig")]
    public GeminiGenerationConfig? GenerationConfig { get; set; } = new()
    {
        Temperature = 0.4,
        MaxOutputTokens = 512,
        TopP = 0.9
    };
}

public sealed class GeminiContent
{
    [JsonPropertyName("role")]
    public string? Role { get; set; }

    [JsonPropertyName("parts")]
    public List<GeminiPart> Parts { get; set; } = new();
}

public sealed class GeminiPart
{
    [JsonPropertyName("text")]
    public string Text { get; set; } = string.Empty;
}

public sealed class GeminiGenerationConfig
{
    [JsonPropertyName("temperature")]
    public double Temperature { get; set; }

    [JsonPropertyName("maxOutputTokens")]
    public int MaxOutputTokens { get; set; }

    [JsonPropertyName("topP")]
    public double TopP { get; set; }
}

public sealed class GeminiGenerateContentResponse
{
    [JsonPropertyName("candidates")]
    public List<GeminiCandidate>? Candidates { get; set; }
}

public sealed class GeminiCandidate
{
    [JsonPropertyName("content")]
    public GeminiContent? Content { get; set; }
}