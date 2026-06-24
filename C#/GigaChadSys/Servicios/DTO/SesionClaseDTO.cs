using System.Globalization;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace GigaChadSys.Servicios.DTO
{
    public class SesionClaseDTO
    {
        [JsonPropertyName("idSesion")]
        public int IdSesion { get; set; }

        [JsonPropertyName("fechaSesion")]
        [JsonConverter(typeof(FlexibleSesionDateTimeConverter))]
        public DateTime FechaSesion { get; set; }

        // Campo extra para Java. Este evita el bug de zona horaria.
        [JsonPropertyName("fechaSesionTexto")]
        public string FechaSesionTexto
        {
            get => FechaSesion == default
                ? ""
                : FechaSesion.ToString("yyyy-MM-dd", CultureInfo.InvariantCulture);

            set
            {
                if (DateTime.TryParseExact(
                        value,
                        "yyyy-MM-dd",
                        CultureInfo.InvariantCulture,
                        DateTimeStyles.None,
                        out var fecha))
                {
                    FechaSesion = fecha.Date;
                }
            }
        }

        [JsonPropertyName("horaInicio")]
        [JsonConverter(typeof(FlexibleSesionDateTimeConverter))]
        public DateTime HoraInicio { get; set; }

        [JsonPropertyName("horaFin")]
        [JsonConverter(typeof(FlexibleSesionDateTimeConverter))]
        public DateTime HoraFin { get; set; }

        [JsonPropertyName("cuposDisponibles")]
        public int CuposDisponibles { get; set; }

        [JsonPropertyName("activo")]
        public bool Activo { get; set; } = true;

        [JsonPropertyName("active")]
        public bool Active
        {
            get => Activo;
            set => Activo = value;
        }

        [JsonPropertyName("salon")]
        public SalonDTO? Salon { get; set; }

        [JsonPropertyName("entrenador")]
        public EntrenadorDTO? Entrenador { get; set; }

        [JsonPropertyName("claseGrupal")]
        public ClaseGrupalDTO? ClaseGrupal { get; set; }

        [JsonIgnore]
        public string FechaTexto => FechaSesion == default
            ? "—"
            : FechaSesion.ToString("yyyy-MM-dd");

        [JsonIgnore]
        public string HorarioTexto => HoraInicio == default || HoraFin == default
            ? "—"
            : $"{HoraInicio:HH:mm} - {HoraFin:HH:mm}";

        [JsonIgnore]
        public DateTime FechaHoraInicio =>
            FechaSesion == default || HoraInicio == default
                ? default
                : FechaSesion.Date.Add(HoraInicio.TimeOfDay);

        [JsonIgnore]
        public DateTime FechaHoraFin =>
            FechaSesion == default || HoraFin == default
                ? default
                : FechaSesion.Date.Add(HoraFin.TimeOfDay);

        [JsonIgnore]
        public string EstadoTexto
        {
            get
            {
                if (!Activo)
                    return "Cancelada";

                if (FechaHoraFin != default && FechaHoraFin < DateTime.Now)
                    return "Finalizada";

                return "Programada";
            }
        }
    }

    public class FlexibleSesionDateTimeConverter : JsonConverter<DateTime>
    {
        public override DateTime Read(
            ref Utf8JsonReader reader,
            Type typeToConvert,
            JsonSerializerOptions options
        )
        {
            if (reader.TokenType == JsonTokenType.Null)
                return default;

            if (reader.TokenType == JsonTokenType.String)
            {
                string? text = reader.GetString();

                if (string.IsNullOrWhiteSpace(text))
                    return default;

                text = text.Replace("[UTC]", "").Trim();

                if (text.EndsWith("Z", StringComparison.OrdinalIgnoreCase))
                    text = text[..^1];

                string[] formats =
                {
                    "yyyy-MM-dd",
                    "yyyy-MM-ddTHH:mm:ss",
                    "yyyy-MM-ddTHH:mm:ss.fff",
                    "yyyy-MM-dd HH:mm:ss",
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
                    return DateTime.SpecifyKind(exactDate, DateTimeKind.Unspecified);
                }

                if (DateTime.TryParse(
                        text,
                        CultureInfo.InvariantCulture,
                        DateTimeStyles.None,
                        out DateTime parsedDate
                    ))
                {
                    return DateTime.SpecifyKind(parsedDate, DateTimeKind.Unspecified);
                }

                if (DateTime.TryParse(text, out DateTime fallbackDate))
                    return DateTime.SpecifyKind(fallbackDate, DateTimeKind.Unspecified);

                return default;
            }

            if (reader.TokenType == JsonTokenType.Number)
            {
                long value = reader.GetInt64();

                DateTime fechaUtc;

                if (value > 100000000000)
                {
                    fechaUtc = DateTimeOffset
                        .FromUnixTimeMilliseconds(value)
                        .UtcDateTime;
                }
                else
                {
                    fechaUtc = DateTimeOffset
                        .FromUnixTimeSeconds(value)
                        .UtcDateTime;
                }

                return DateTime.SpecifyKind(fechaUtc, DateTimeKind.Unspecified);
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

                    int hour = array.GetArrayLength() >= 4 ? array[3].GetInt32() : 0;
                    int minute = array.GetArrayLength() >= 5 ? array[4].GetInt32() : 0;
                    int second = array.GetArrayLength() >= 6 ? array[5].GetInt32() : 0;

                    return DateTime.SpecifyKind(
                        new DateTime(year, month, day, hour, minute, second),
                        DateTimeKind.Unspecified
                    );
                }

                return default;
            }

            if (reader.TokenType == JsonTokenType.StartObject)
            {
                using JsonDocument document = JsonDocument.ParseValue(ref reader);
                JsonElement root = document.RootElement;

                int year = ObtenerEntero(root, "year");
                int month = ObtenerEntero(root, "monthValue");
                int day = ObtenerEntero(root, "dayOfMonth");

                if (month == 0 && root.TryGetProperty("month", out JsonElement monthElement))
                {
                    if (monthElement.ValueKind == JsonValueKind.Number)
                        month = monthElement.GetInt32();
                    else if (monthElement.ValueKind == JsonValueKind.String)
                        month = ConvertMonthName(monthElement.GetString());
                }

                if (day == 0)
                    day = ObtenerEntero(root, "day");

                int hour = ObtenerEntero(root, "hour");
                int minute = ObtenerEntero(root, "minute");
                int second = ObtenerEntero(root, "second");

                if (year > 0 && month > 0 && day > 0)
                {
                    return DateTime.SpecifyKind(
                        new DateTime(year, month, day, hour, minute, second),
                        DateTimeKind.Unspecified
                    );
                }

                return default;
            }

            return default;
        }

        public override void Write(
            Utf8JsonWriter writer,
            DateTime value,
            JsonSerializerOptions options
        )
        {
            if (value == default)
            {
                writer.WriteNullValue();
                return;
            }

            var limpio = DateTime.SpecifyKind(value, DateTimeKind.Unspecified);

            writer.WriteStringValue(
                limpio.ToString("yyyy-MM-dd'T'HH:mm:ss", CultureInfo.InvariantCulture)
            );
        }

        private static int ObtenerEntero(JsonElement root, string propertyName)
        {
            if (root.TryGetProperty(propertyName, out JsonElement element) &&
                element.ValueKind == JsonValueKind.Number)
            {
                return element.GetInt32();
            }

            return 0;
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
}