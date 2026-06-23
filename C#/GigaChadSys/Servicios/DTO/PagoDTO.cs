using System.Globalization;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace GigaChadSys.Servicios.DTO;

/// <summary>
/// DTO que mapea al JSON de Java para la entidad Pago.
/// Java model: Pago.java — campos: idPago, fechaPago, monto, tipo, metodoPago, activo.
/// Endpoint: GET/POST/PUT /PagoRS
/// </summary>
public class PagoDTO
{
    [JsonPropertyName("idPago")]
    public int IdPago { get; set; }

    [JsonPropertyName("fechaPago")]
    [JsonConverter(typeof(FlexibleNullableDateTimeConverter))]
    public DateTime? FechaPago { get; set; }

    /// <summary>
    /// Java expone este campo como "monto".
    /// </summary>
    [JsonPropertyName("monto")]
    public double Monto { get; set; }

    /// <summary>
    /// Alias para pantallas que usen MontoTotal.
    /// No se serializa porque Java espera "monto".
    /// </summary>
    [JsonIgnore]
    public double MontoTotal
    {
        get => Monto;
        set => Monto = value;
    }

    [JsonPropertyName("tipo")]
    public string Tipo { get; set; } = "";

    /// <summary>
    /// Java expone este campo como "metodoPago".
    /// </summary>
    [JsonPropertyName("metodoPago")]
    public int MetodoPago { get; set; }

    /// <summary>
    /// Alias para pantallas que usen IdMetodoPago.
    /// No se serializa porque Java espera "metodoPago".
    /// </summary>
    [JsonIgnore]
    public int IdMetodoPago
    {
        get => MetodoPago;
        set => MetodoPago = value;
    }

    [JsonPropertyName("activo")]
    public bool Activo { get; set; }

    public string FechaPagoTexto => FechaPago.HasValue
        ? FechaPago.Value.ToString("yyyy-MM-dd")
        : "—";

    public string MontoTexto => $"S/{Monto:0.00}";

    public string EstadoTexto => Activo ? "Activo" : "Inactivo";
}

public class FlexibleNullableDateTimeConverter : JsonConverter<DateTime?>
{
    public override DateTime? Read(
        ref Utf8JsonReader reader,
        Type typeToConvert,
        JsonSerializerOptions options
    )
    {
        if (reader.TokenType == JsonTokenType.Null)
            return null;

        if (reader.TokenType == JsonTokenType.String)
        {
            string? text = reader.GetString();

            if (string.IsNullOrWhiteSpace(text))
                return null;

            string[] formats =
            {
                "yyyy-MM-dd",
                "yyyy-MM-ddTHH:mm:ss",
                "yyyy-MM-ddTHH:mm:ss.fff",
                "dd/MM/yyyy",
                "MM/dd/yyyy"
            };

            if (DateTime.TryParseExact(
                    text,
                    formats,
                    CultureInfo.InvariantCulture,
                    DateTimeStyles.None,
                    out DateTime exactDate
                ))
            {
                return exactDate;
            }

            if (DateTime.TryParse(text, out DateTime parsedDate))
                return parsedDate;

            return null;
        }

        if (reader.TokenType == JsonTokenType.Number)
        {
            long value = reader.GetInt64();

            // Por si Java manda timestamp en milisegundos.
            if (value > 100000000000)
            {
                return DateTimeOffset
                    .FromUnixTimeMilliseconds(value)
                    .DateTime;
            }

            // Por si manda timestamp en segundos.
            return DateTimeOffset
                .FromUnixTimeSeconds(value)
                .DateTime;
        }

        if (reader.TokenType == JsonTokenType.StartArray)
        {
            using JsonDocument document = JsonDocument.ParseValue(ref reader);
            JsonElement array = document.RootElement;

            if (array.GetArrayLength() >= 3)
            {
                int year = array[0].GetInt32();
                int month = array[1].GetInt32();
                int day = array[2].GetInt32();

                return new DateTime(year, month, day);
            }

            return null;
        }

        if (reader.TokenType == JsonTokenType.StartObject)
        {
            using JsonDocument document = JsonDocument.ParseValue(ref reader);
            JsonElement root = document.RootElement;

            int year = 0;
            int month = 0;
            int day = 0;

            if (root.TryGetProperty("year", out JsonElement yearElement))
                year = yearElement.GetInt32();

            if (root.TryGetProperty("month", out JsonElement monthElement))
            {
                if (monthElement.ValueKind == JsonValueKind.Number)
                    month = monthElement.GetInt32();
                else if (monthElement.ValueKind == JsonValueKind.String)
                    month = ConvertMonthName(monthElement.GetString());
            }

            if (root.TryGetProperty("monthValue", out JsonElement monthValueElement))
                month = monthValueElement.GetInt32();

            if (root.TryGetProperty("day", out JsonElement dayElement))
                day = dayElement.GetInt32();

            if (root.TryGetProperty("dayOfMonth", out JsonElement dayOfMonthElement))
                day = dayOfMonthElement.GetInt32();

            if (year > 0 && month > 0 && day > 0)
                return new DateTime(year, month, day);

            return null;
        }

        return null;
    }

    public override void Write(
        Utf8JsonWriter writer,
        DateTime? value,
        JsonSerializerOptions options
    )
    {
        if (value.HasValue)
            writer.WriteStringValue(value.Value.ToString("yyyy-MM-dd"));
        else
            writer.WriteNullValue();
    }

    private static int ConvertMonthName(string? monthName)
    {
        return monthName?.ToUpperInvariant() switch
        {
            "JANUARY" => 1,
            "FEBRUARY" => 2,
            "MARCH" => 3,
            "APRIL" => 4,
            "MAY" => 5,
            "JUNE" => 6,
            "JULY" => 7,
            "AUGUST" => 8,
            "SEPTEMBER" => 9,
            "OCTOBER" => 10,
            "NOVEMBER" => 11,
            "DECEMBER" => 12,
            _ => 0
        };
    }
}