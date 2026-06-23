using System.Text.Json;
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
    /// Puede venir como string ("activa", "true") o como boolean desde Java.
    /// </summary>
    [JsonPropertyName("estadoMembresia")]
    [JsonConverter(typeof(FlexibleEstadoMembresiaConverter))]
    public string EstadoMembresia { get; set; } = "";

    [JsonPropertyName("fechaIngreso")]
    [JsonConverter(typeof(FlexibleNullableDateTimeConverter))]
    public DateTime? FechaIngreso { get; set; }

    [JsonPropertyName("fechaFinMembresia")]
    [JsonConverter(typeof(FlexibleNullableDateTimeConverter))]
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

    public bool EsActiva =>
        EstadoMembresia?.ToLower() == "true"
        || EstadoMembresia?.ToLower() == "activa"
        || EstadoMembresia == "1";

    public bool EsBlack => IdMembresiaBlack.HasValue;

    public bool EsBasic => IdMembresiaBasic.HasValue;

    public string TipoPlan =>
        EsBlack ? "Black" :
        EsBasic ? "Basic" :
        "Sin plan";

    public string FechaFinTexto => FechaFinMembresia.HasValue
        ? FechaFinMembresia.Value.ToString("yyyy-MM-dd")
        : "—";

    public string FechaInicioTexto => FechaIngreso.HasValue
        ? FechaIngreso.Value.ToString("yyyy-MM-dd")
        : "—";
}

/// <summary>
/// Convierte estadoMembresia aunque Java lo mande como string, boolean o número.
/// </summary>
public class FlexibleEstadoMembresiaConverter : JsonConverter<string>
{
    public override string Read(
        ref Utf8JsonReader reader,
        Type typeToConvert,
        JsonSerializerOptions options
    )
    {
        if (reader.TokenType == JsonTokenType.Null)
            return "";

        if (reader.TokenType == JsonTokenType.String)
            return reader.GetString() ?? "";

        if (reader.TokenType == JsonTokenType.True)
            return "true";

        if (reader.TokenType == JsonTokenType.False)
            return "false";

        if (reader.TokenType == JsonTokenType.Number)
            return reader.GetInt32().ToString();

        return "";
    }

    public override void Write(
        Utf8JsonWriter writer,
        string value,
        JsonSerializerOptions options
    )
    {
        writer.WriteStringValue(value);
    }
}