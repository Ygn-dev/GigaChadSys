using System.Globalization;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace GigaChadSys.Servicios.DTO;

public class SuscripcionDTO
{
    [JsonPropertyName("idSuscripcion")]
    public int IdSuscripcion { get; set; }

    [JsonPropertyName("estadoMembresia")]
    [JsonConverter(typeof(FlexibleEstadoMembresiaConverter))]
    public string EstadoMembresia { get; set; } = "";

    [JsonIgnore]
    public DateTime? FechaIngreso { get; set; }

    [JsonIgnore]
    public DateTime? FechaFinMembresia { get; set; }

    [JsonPropertyName("fechaIngresoTexto")]
    public string FechaIngresoTexto
    {
        get => FechaIngreso.HasValue
            ? FechaIngreso.Value.ToString("yyyy-MM-dd", CultureInfo.InvariantCulture)
            : "";

        set => FechaIngreso = ParseFecha(value);
    }

    [JsonPropertyName("fechaFinMembresiaTexto")]
    public string FechaFinMembresiaTexto
    {
        get => FechaFinMembresia.HasValue
            ? FechaFinMembresia.Value.ToString("yyyy-MM-dd", CultureInfo.InvariantCulture)
            : "";

        set => FechaFinMembresia = ParseFecha(value);
    }

    [JsonPropertyName("idPago")]
    public int IdPago { get; set; }

    [JsonPropertyName("idUsuario")]
    public int IdUsuario { get; set; }

    [JsonPropertyName("idMembresiaBasic")]
    public int? IdMembresiaBasic { get; set; }

    [JsonPropertyName("idMembresiaBlack")]
    public int? IdMembresiaBlack { get; set; }

    [JsonPropertyName("activo")]
    public bool Activo { get; set; }

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

    private static DateTime? ParseFecha(string? value)
    {
        if (string.IsNullOrWhiteSpace(value))
            return null;

        value = value.Replace("[UTC]", "").Trim();

        if (value.EndsWith("Z", StringComparison.OrdinalIgnoreCase))
            value = value[..^1];

        string[] formatos =
        {
            "yyyy-MM-dd",
            "yyyy-MM-ddTHH:mm:ss",
            "yyyy-MM-ddTHH:mm:ss.fff",
            "yyyy-MM-dd HH:mm:ss",
            "dd/MM/yyyy",
            "MM/dd/yyyy"
        };

        if (DateTime.TryParseExact(
                value,
                formatos,
                CultureInfo.InvariantCulture,
                DateTimeStyles.None,
                out DateTime fechaExacta))
        {
            return fechaExacta.Date;
        }

        if (DateTime.TryParse(value, CultureInfo.InvariantCulture, DateTimeStyles.None, out DateTime fecha))
            return fecha.Date;

        if (DateTime.TryParse(value, out DateTime fallback))
            return fallback.Date;

        return null;
    }
}

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