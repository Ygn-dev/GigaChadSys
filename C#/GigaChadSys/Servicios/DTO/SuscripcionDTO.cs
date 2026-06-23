using System.Text.Json.Serialization;

namespace GigaChadSys.Servicios.DTO;

/// <summary>
/// DTO que mapea al JSON de Java para la entidad Suscripcion.
/// Java: Suscripcion.java
/// BD: Suscripcion(idSuscripcion, estadoMembresia, fechaIngreso, fechaFinMembresia,
///                 idPago, idUsuario, membresia_ID_basic, membresia_ID_black, activo)
/// Endpoint: GET/POST/PUT /SuscripcionRS
/// </summary>
public class SuscripcionDTO
{
    [JsonPropertyName("idSuscripcion")]
    public int IdSuscripcion { get; set; }

    /// <summary>
    /// Java: getEstadoMembresia() → string ("activa" / "inactiva" / etc.)
    /// BD: estadoMembresia BOOLEAN → Java lo mapea como String en el modelo.
    /// </summary>
    [JsonPropertyName("estadoMembresia")]
    public string EstadoMembresia { get; set; } = "";

    [JsonPropertyName("fechaIngreso")]
    public DateTime? FechaIngreso { get; set; }

    [JsonPropertyName("fechaFinMembresia")]
    public DateTime? FechaFinMembresia { get; set; }

    /// <summary>FK al Pago asociado.</summary>
    [JsonPropertyName("idPago")]
    public int IdPago { get; set; }

    /// <summary>FK al Socio (idUsuario en tabla Socio).</summary>
    [JsonPropertyName("idUsuario")]
    public int IdUsuario { get; set; }

    /// <summary>FK opcional a MembresiaBasic (null si es Black).</summary>
    [JsonPropertyName("idMembresiaBasic")]
    public int? IdMembresiaBasic { get; set; }

    /// <summary>FK opcional a MembresiaBlack (null si es Basic).</summary>
    [JsonPropertyName("idMembresiaBlack")]
    public int? IdMembresiaBlack { get; set; }

    [JsonPropertyName("activo")]
    public bool Activo { get; set; }

    // ── Propiedades calculadas para la UI ──────────────────────────────────
    public bool EsActiva => EstadoMembresia?.ToLower() == "true" || EstadoMembresia == "activa";
    public bool EsBlack => IdMembresiaBlack.HasValue;
    public bool EsBasic => IdMembresiaBasic.HasValue;
    public string TipoPlan => EsBlack ? "Black" : EsBasic ? "Basic" : "Sin plan";

    public string FechaFinTexto => FechaFinMembresia.HasValue
        ? FechaFinMembresia.Value.ToString("yyyy-MM-dd")
        : "—";

    public string FechaInicioTexto => FechaIngreso.HasValue
        ? FechaIngreso.Value.ToString("yyyy-MM-dd")
        : "—";
}
