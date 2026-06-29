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
    private readonly MembresiaBasicServicio _membresiaBasicServicio;
    private readonly MembresiaBlackServicio _membresiaBlackServicio;

    private bool _contextoCargado;

    public AsistenteSocioServicio(
        HttpClient httpClient,
        IConfiguration configuration,
        SessionServicio session,
        SocioServicio socioServicio,
        SuscripcionServicio suscripcionServicio,
        MembresiaBasicServicio membresiaBasicServicio,
        MembresiaBlackServicio membresiaBlackServicio)
    {
        _httpClient = httpClient;
        _configuration = configuration;
        _session = session;
        _socioServicio = socioServicio;
        _suscripcionServicio = suscripcionServicio;
        _membresiaBasicServicio = membresiaBasicServicio;
        _membresiaBlackServicio = membresiaBlackServicio;
    }

    public List<AsistenteMensajeDTO> Conversacion { get; } = new();

    public string NombreUsuario => ObtenerNombreUsuario();

    public SocioDTO? SocioActual { get; private set; }
    public SuscripcionDTO? SuscripcionActual { get; private set; }
    public List<MembresiaBasicDTO> MembresiasBasic { get; private set; } = new();
    public List<MembresiaBlackDTO> MembresiasBlack { get; private set; } = new();

    public async Task CargarContextoAsync()
    {
        if (_contextoCargado)
            return;

        if (!_session.EstaAutenticado || !_session.EsSocio)
        {
            AgregarMensajeSistema("Inicia sesion como socio para que pueda ayudarte con tu cuenta.");
            _contextoCargado = true;
            return;
        }

        var tareas = new List<Task>
        {
            CargarSocioActualAsync(),
            CargarSuscripcionActualAsync(),
            CargarCatalogosMembresiaAsync()
        };

        await Task.WhenAll(tareas);

        if (Conversacion.Count == 0)
        {
            AgregarMensajeSistema($"Hola, {NombreUsuario}. Puedo resumirte tu membresia, decirte tu estado actual y orientarte sobre pagos o reservas.");
        }

        _contextoCargado = true;
    }

    public async Task<string> ResponderAsync(string pregunta)
    {
        await CargarContextoAsync();

        string texto = pregunta.Trim();
        if (string.IsNullOrWhiteSpace(texto))
        {
            return "Escribe una pregunta corta sobre tu membresia, pagos o reservas.";
        }

        AgregarMensajeUsuario(texto);

        string respuesta = await GenerarRespuestaConGeminiAsync(texto);
        AgregarMensajeSistema(respuesta);

        return respuesta;
    }

    public void ReiniciarConversacion()
    {
        Conversacion.Clear();
        _contextoCargado = false;
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

        if (Contiene(texto, "reserva", "clase", "horario"))
            return "Para reservas y clases, revisa Reservar Clase y Mis Reservas. Si quieres, te resumo primero tu estado de membresia para confirmar si ya puedes reservar.";

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
        if (SuscripcionActual is null)
            return "No encontre una suscripcion asociada todavia. Desde Mis Pagos podras revisar tu historial y confirmar tu ultimo pago.";

        return $"Tu suscripcion actual usa el pago #{SuscripcionActual.IdPago}. Si necesitas revisar el historial completo, entra a Mis Pagos o dime si quieres que te resuma el estado de tu plan.";
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