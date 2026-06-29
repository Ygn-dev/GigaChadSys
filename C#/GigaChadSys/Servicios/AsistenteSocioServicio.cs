using System.Net.Http.Json;
using System.Text.Json;
using System.Text.Json.Serialization;
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

    public string NombreUsuario => !string.IsNullOrWhiteSpace(_session.Nombres) ? _session.Nombres : (SocioActual?.Nombres ?? "Socio");

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

        await Task.WhenAll(
            CargarSocioActualAsync(), CargarSuscripcionActualAsync(), CargarReservasActualesAsync(),
            CargarClasesDisponiblesAsync(), CargarPagosActualesAsync(), CargarCatalogosMembresiaAsync()
        );

        if (Conversacion.Count == 0)
        {
            Conversacion.Add(new AsistenteMensajeDTO("asistente", $"Hola, {NombreUsuario}. Puedo resumirte tu membresía, decirte tu estado actual y orientarte sobre pagos o gestionar tus reservas de forma automática."));
        }

        _usuarioIdContextoCargado = _session.IdUsuario;
    }

    public async Task<string> ResponderAsync(string pregunta)
    {
        await CargarContextoAsync(true);

        string texto = pregunta.Trim();
        if (string.IsNullOrWhiteSpace(texto)) return "Escribe una pregunta válida.";

        Conversacion.Add(new AsistenteMensajeDTO("usuario", texto));

        string respuesta = await GenerarRespuestaConGeminiAsync(texto);

        Conversacion.Add(new AsistenteMensajeDTO("asistente", respuesta));
        return respuesta;
    }

    public void ReiniciarConversacion()
    {
        Conversacion.Clear();
        _usuarioIdContextoCargado = 0;
    }

    private async Task CargarSocioActualAsync() { if (_session.IdUsuario > 0) SocioActual = await _socioServicio.ObtenerPorIdAsync(_session.IdUsuario); }
    private async Task CargarSuscripcionActualAsync() { if (_session.IdUsuario > 0) SuscripcionActual = (await _suscripcionServicio.ListarSuscripcionesAsync()).Where(s => s.IdUsuario == _session.IdUsuario).OrderByDescending(s => s.FechaIngreso ?? DateTime.MinValue).FirstOrDefault(); }
    private async Task CargarReservasActualesAsync() { if (_session.IdUsuario > 0) ReservasActuales = (await _reservaServicio.ListarReservasAsync()).Where(r => r.IdUsuario == _session.IdUsuario).OrderByDescending(r => r.FechaHoraReserva ?? DateTime.MinValue).ToList(); }
    private async Task CargarClasesDisponiblesAsync() { ClasesDisponibles = (await _sesionClaseServicio.ListarSesionesAsync()).Where(s => s.Activo && s.CuposDisponibles > 0 && (s.FechaHoraInicio == default || s.FechaHoraInicio >= DateTime.Now)).OrderBy(s => s.FechaHoraInicio == default ? DateTime.MaxValue : s.FechaHoraInicio).Take(15).ToList(); }
    private async Task CargarPagosActualesAsync()
    {
        if (_session.IdUsuario <= 0) return;
        var misSuscripciones = (await _suscripcionServicio.ListarSuscripcionesAsync()).Where(s => s.IdUsuario == _session.IdUsuario).ToList();
        var misIdsPagos = misSuscripciones.Select(s => s.IdPago).Distinct().ToList();
        if (misIdsPagos.Count == 0) { PagosActuales = new(); MetodosPagoActuales = new(); return; }
        MetodosPagoActuales = (await _metodoPagoServicio.ListarMetodosDePagoAsync()).Where(m => m.Activo).ToList();
        PagosActuales = (await _pagoServicio.ListarPagosAsync()).Where(p => misIdsPagos.Contains(p.IdPago)).OrderByDescending(p => p.FechaPago ?? DateTime.MinValue).ThenByDescending(p => p.IdPago).ToList();
    }
    private async Task CargarCatalogosMembresiaAsync()
    {
        MembresiasBasic = (await _membresiaBasicServicio.ListarMembresiasBasicAsync()).Where(m => m.Activa).OrderBy(m => m.CostoMantenimientoMensual).ToList();
        MembresiasBlack = (await _membresiaBlackServicio.ListarMembresiasBlackAsync()).Where(m => m.Activa).OrderBy(m => m.CostoMantenimientoAnual).ToList();
    }

    private void LimpiarContextoSiCambioDeSesion() { _usuarioIdContextoCargado = 0; SocioActual = null; SuscripcionActual = null; ReservasActuales.Clear(); ClasesDisponibles.Clear(); PagosActuales.Clear(); MetodosPagoActuales.Clear(); MembresiasBasic.Clear(); MembresiasBlack.Clear(); }

    private async Task<string> GenerarRespuestaConGeminiAsync(string pregunta)
    {
        string apiKey = _configuration["Gemini:ApiKey"]?.Trim() ?? string.Empty;
        if (string.IsNullOrWhiteSpace(apiKey)) return "Falta configurar Gemini: agrega tu API key en appsettings.json.";

        string model = string.IsNullOrWhiteSpace(_configuration["Gemini:Model"]?.Trim()) ? "gemini-3.1-flash-lite" : _configuration["Gemini:Model"]!.Trim();
        string systemPrompt = "Eres el asistente inteligente de un gimnasio. Responde de forma clara. NUNCA confirmes una reserva o cancelación usando texto directamente; DEBES usar siempre las herramientas/funciones proporcionadas (RegistrarReserva o CancelarReserva). Usa tu comprensión de lenguaje natural para tolerar errores ortográficos en los nombres de las clases que pide el usuario y mapearlos a los IDs correctos que están en tu contexto. Si el usuario pide una clase y hay ambigüedad (ej. hay 2 horarios de la misma clase), pregúntale a qué hora la prefiere antes de llamar a la herramienta.";
        string contexto = ConstruirContextoParaIA();

        var request = new GeminiGenerateContentRequest
        {
            SystemInstruction = new GeminiContent { Parts = new List<GeminiPart> { new() { Text = systemPrompt } } },
            Contents = new List<GeminiContent>
            {
                new() { Role = "user", Parts = new List<GeminiPart> { new() { Text = $"Contexto estricto del sistema:\n{contexto}\n\nUsuario dice: {pregunta}" } } }
            },
            // AQUI ESTA LA MAGIA: Le damos a la IA la capacidad de usar tu backend real
            Tools = new List<GeminiTool>
            {
                new GeminiTool
                {
                    FunctionDeclarations = new List<GeminiFunctionDeclaration>
                    {
                        new GeminiFunctionDeclaration
                        {
                            Name = "RegistrarReserva",
                            Description = "Ejecuta el registro de una reserva. Úsala SOLO cuando el usuario indique qué clase desea y estés seguro del ID. Extrae el idSesion del contexto 'Clases disponibles'.",
                            Parameters = new GeminiSchema
                            {
                                Type = "OBJECT",
                                Properties = new Dictionary<string, GeminiSchemaProperty>
                                {
                                    { "idSesion", new GeminiSchemaProperty { Type = "INTEGER", Description = "El ID numérico exacto de la sesión." } }
                                },
                                Required = new List<string> { "idSesion" }
                            }
                        },
                        new GeminiFunctionDeclaration
                        {
                            Name = "CancelarReserva",
                            Description = "Ejecuta la cancelación de una reserva. Usa el idReserva del contexto 'Reservas activas'.",
                            Parameters = new GeminiSchema
                            {
                                Type = "OBJECT",
                                Properties = new Dictionary<string, GeminiSchemaProperty>
                                {
                                    { "idReserva", new GeminiSchemaProperty { Type = "INTEGER", Description = "El ID numérico exacto de la reserva a cancelar." } }
                                },
                                Required = new List<string> { "idReserva" }
                            }
                        }
                    }
                }
            }
        };

        try
        {
            string url = $"https://generativelanguage.googleapis.com/v1beta/models/{model}:generateContent?key={apiKey}";
            var response = await _httpClient.PostAsJsonAsync(url, request);

            if (!response.IsSuccessStatusCode) return $"Gemini respondió con error HTTP {(int)response.StatusCode}.";

            var result = await response.Content.ReadFromJsonAsync<GeminiGenerateContentResponse>();
            var part = result?.Candidates?.FirstOrDefault()?.Content?.Parts?.FirstOrDefault();

            // Si Gemini decide que tiene los datos para ejecutar la función (Function Calling)
            if (part?.FunctionCall != null)
            {
                string functionName = part.FunctionCall.Name;
                if (functionName == "RegistrarReserva" && part.FunctionCall.Args != null && part.FunctionCall.Args.TryGetValue("idSesion", out JsonElement sesionElem))
                {
                    int idSesion = sesionElem.GetInt32();
                    return await EjecutarReservaRealAsync(idSesion);
                }
                else if (functionName == "CancelarReserva" && part.FunctionCall.Args != null && part.FunctionCall.Args.TryGetValue("idReserva", out JsonElement reservaElem))
                {
                    int idReserva = reservaElem.GetInt32();
                    return await EjecutarCancelacionRealAsync(idReserva);
                }
            }

            // Si Gemini responde solo con texto conversacional (preguntando dudas, o saludando)
            return !string.IsNullOrWhiteSpace(part?.Text) ? part.Text.Trim() : "Gemini no devolvió una respuesta válida.";
        }
        catch (Exception ex)
        {
            return $"Error al conectar con la IA: {ex.Message}";
        }
    }

    private async Task<string> EjecutarReservaRealAsync(int idSesion)
    {
        if (!_session.EstaAutenticado || !_session.EsSocio) return "Debes iniciar sesión como socio.";
        if (SocioActual is null || !SocioActual.EstadoMembresia || SuscripcionActual is null || !SuscripcionActual.EsActiva)
            return "Tu membresía no está activa en el sistema. No se puede procesar la reserva.";

        var sesion = ClasesDisponibles.FirstOrDefault(s => s.IdSesion == idSesion);
        if (sesion == null) return "Esa clase ya no está disponible o el ID no es válido.";
        if (sesion.CuposDisponibles <= 0) return "Lo siento, esa clase está completamente llena.";

        // Evitar dobles reservas reales en la base de datos
        var reservas = await _reservaServicio.ListarReservasAsync();
        if (reservas.Any(r => r.Activo && r.IdUsuario == _session.IdUsuario && r.SesionClase?.ClaseGrupal?.IdClase == sesion.ClaseGrupal?.IdClase && r.SesionClase.FechaHoraFin >= DateTime.Now))
        {
            return $"Ya cuentas con una reserva activa para la clase de {sesion.ClaseGrupal?.Nombre}.";
        }

        // Armamos el Payload PERFECTO directo a tu backend
        var nuevaReserva = new ReservaDTO
        {
            IdUsuario = _session.IdUsuario,
            FechaHoraReserva = DateTime.Now,
            Asistio = false,
            Activo = true,
            SesionClase = new SesionClaseDTO { IdSesion = idSesion }
        };

        var resultado = await _reservaServicio.RegistrarAsync(nuevaReserva);

        if (resultado.StartsWith("Error", StringComparison.OrdinalIgnoreCase)) return resultado;

        await CargarContextoAsync(true);
        return $"¡Listo! Ejecuté la reserva en el sistema correctamente.\nClase: **{sesion.ClaseGrupal?.Nombre}**\nFecha: {sesion.FechaTexto} a las {sesion.HorarioTexto}.";
    }

    private async Task<string> EjecutarCancelacionRealAsync(int idReserva)
    {
        if (!_session.EstaAutenticado || !_session.EsSocio) return "Debes iniciar sesión.";

        var reserva = ReservasActuales.FirstOrDefault(r => r.IdReserva == idReserva && r.Activo);
        if (reserva == null) return "No se encontró una reserva activa con ese identificador en tu cuenta.";

        var resultado = await _reservaServicio.EliminarAsync(idReserva);
        if (resultado.StartsWith("Error", StringComparison.OrdinalIgnoreCase)) return resultado;

        await CargarContextoAsync(true);
        return $"Acabo de cancelar oficialmente tu reserva para **{reserva.SesionClase?.ClaseGrupal?.Nombre}** en el sistema.";
    }

    private string ConstruirContextoParaIA()
    {
        var partes = new List<string>
        {
            $"Usuario logueado: {_session.EstaAutenticado}",
            $"Nombre: {NombreUsuario}",
            $"Membresía Activa: {(SocioActual?.EstadoMembresia == true ? "Sí" : "No")}",
            $"Suscripción: {(SuscripcionActual is null ? "Ninguna" : $"{SuscripcionActual.TipoPlan} al {SuscripcionActual.FechaFinTexto}")}",
            $"Reservas activas del usuario (Úsalas si pide cancelar): {(ReservasActuales.Count(r => r.Activo && r.SesionClase?.FechaHoraFin >= DateTime.Now) == 0 ? "Ninguna" : string.Join("; ", ReservasActuales.Where(r => r.Activo && r.SesionClase?.FechaHoraFin >= DateTime.Now).Select(r => $"[ID_RESERVA: {r.IdReserva}] {r.SesionClase?.ClaseGrupal?.Nombre} {r.SesionClase?.FechaTexto} {r.SesionClase?.HorarioTexto}")))}",
            $"Clases disponibles en el sistema (Úsalas si pide reservar): {(ClasesDisponibles.Count == 0 ? "No hay clases" : string.Join("; ", ClasesDisponibles.Select(s => $"[ID_SESION: {s.IdSesion}] {s.ClaseGrupal?.Nombre} ({s.ClaseGrupal?.Disciplina}) | {s.FechaTexto} a las {s.HorarioTexto} | {s.CuposDisponibles} cupos libres")))}"
        };

        partes.Add("Historial reciente: " + string.Join(" | ", Conversacion.TakeLast(6).Select(m => $"{m.Rol}: {m.Texto}")));
        return string.Join("\n", partes);
    }
}

// --- DTOs ACTUALIZADOS PARA SOPORTAR FUNCTION CALLING DE GEMINI ---

public sealed record AsistenteMensajeDTO(string Rol, string Texto);

public sealed class GeminiGenerateContentRequest
{
    [JsonPropertyName("systemInstruction")]
    public GeminiContent? SystemInstruction { get; set; }

    [JsonPropertyName("contents")]
    public List<GeminiContent> Contents { get; set; } = new();

    [JsonPropertyName("tools")]
    public List<GeminiTool>? Tools { get; set; }
}

public sealed class GeminiTool
{
    [JsonPropertyName("functionDeclarations")]
    public List<GeminiFunctionDeclaration> FunctionDeclarations { get; set; } = new();
}

public sealed class GeminiFunctionDeclaration
{
    [JsonPropertyName("name")]
    public string Name { get; set; } = string.Empty;

    [JsonPropertyName("description")]
    public string Description { get; set; } = string.Empty;

    [JsonPropertyName("parameters")]
    public GeminiSchema? Parameters { get; set; }
}

public sealed class GeminiSchema
{
    [JsonPropertyName("type")]
    public string Type { get; set; } = string.Empty;

    [JsonPropertyName("properties")]
    public Dictionary<string, GeminiSchemaProperty>? Properties { get; set; }

    [JsonPropertyName("required")]
    public List<string>? Required { get; set; }
}

public sealed class GeminiSchemaProperty
{
    [JsonPropertyName("type")]
    public string Type { get; set; } = string.Empty;

    [JsonPropertyName("description")]
    public string? Description { get; set; }
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
    public string? Text { get; set; }

    [JsonPropertyName("functionCall")]
    public GeminiFunctionCall? FunctionCall { get; set; }
}

public sealed class GeminiFunctionCall
{
    [JsonPropertyName("name")]
    public string Name { get; set; } = string.Empty;

    [JsonPropertyName("args")]
    public Dictionary<string, JsonElement>? Args { get; set; }
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