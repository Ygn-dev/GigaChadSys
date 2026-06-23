using System.Text.Json.Serialization;

namespace GigaChadSys.Servicios.DTO;

/// <summary>
/// DTO que mapea al JSON de Java para la entidad MetodoPago.
/// BD: MetodoPago(idMetodoPago, tipo, detalle, activo)
/// Endpoint: GET /MetodoPagoRS
/// </summary>
public class MetodoPagoDTO
{
    [JsonPropertyName("idMetodoPago")]
    public int IdMetodoPago { get; set; }

    /// <summary>
    /// Ej: "Efectivo", "Tarjeta Débito", "Tarjeta Crédito"
    /// </summary>
    [JsonPropertyName("tipo")]
    public string Tipo { get; set; } = "";

    [JsonPropertyName("detalle")]
    public string Detalle { get; set; } = "";

    [JsonPropertyName("activo")]
    public bool Activo { get; set; }
}
