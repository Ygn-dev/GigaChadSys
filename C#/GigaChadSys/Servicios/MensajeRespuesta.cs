using System.Text.Json.Serialization;

namespace GigaChadSys.Servicios;

/// <summary>
/// DTO que representa la respuesta genérica del backend Java REST.
/// Todos los endpoints POST, PUT y DELETE devuelven: { "mensaje": "..." }
/// </summary>
public class MensajeRespuesta
{
    [JsonPropertyName("mensaje")]
    public string Mensaje { get; set; } = string.Empty;

    [JsonPropertyName("idPago")]
    public int? IdPago { get; set; }
}
