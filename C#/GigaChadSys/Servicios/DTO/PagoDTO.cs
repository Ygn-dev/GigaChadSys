using System.Text.Json.Serialization;

namespace GigaChadSys.Servicios.DTO;

/// <summary>
/// DTO que mapea al JSON de Java para la entidad Pago.
/// Java model: Pago.java — campos: idPago, fechaPago, monto, tipo, metodoPago, activo
/// Endpoint: GET/POST/PUT /PagoRS
/// </summary>
public class PagoDTO
{
    [JsonPropertyName("idPago")]
    public int IdPago { get; set; }

    [JsonPropertyName("fechaPago")]
    public DateTime? FechaPago { get; set; }

    /// <summary>
    /// El campo en Java se llama 'monto' (getter getMonto()).
    /// La BD tiene 'montoTotal' pero Java lo expone como 'monto'.
    /// </summary>
    [JsonPropertyName("monto")]
    public double Monto { get; set; }

    /// <summary>
    /// Tipo de pago: ej. "Membresía", "Inscripción".
    /// </summary>
    [JsonPropertyName("tipo")]
    public string Tipo { get; set; } = "";

    /// <summary>
    /// FK al id de MetodoPago (int). No es un objeto anidado.
    /// Ej: 1 = Efectivo, 2 = Tarjeta Débito, 3 = Tarjeta Crédito.
    /// </summary>
    [JsonPropertyName("metodoPago")]
    public int MetodoPago { get; set; }

    [JsonPropertyName("activo")]
    public bool Activo { get; set; }

    // ── Propiedades calculadas para la UI ──────────────────────────────────
    public string FechaPagoTexto => FechaPago.HasValue
        ? FechaPago.Value.ToString("yyyy-MM-dd")
        : "—";

    public string MontoTexto => $"S/{Monto:0.00}";
}
