using System.Text.Json.Serialization;

namespace GigaChadSys.Servicios.DTO
{
    public class SesionClaseDTO
    {
        [JsonPropertyName("idSesion")]
        public int IdSesion { get; set; }

        [JsonIgnore]
        public DateTime FechaSesion { get; set; }

        [JsonPropertyName("fechaSesion")]
        public string? FechaSesionStr
        {
            get => FechaSesion.ToString("yyyy-MM-ddTHH:mm:ssZ");
            set
            {
                if (DateTime.TryParse(value?.Replace("[UTC]", ""), out var d))
                    FechaSesion = d;
            }
        }

        [JsonIgnore]
        public DateTime HoraInicio { get; set; }

        [JsonPropertyName("horaInicio")]
        public string? HoraInicioStr
        {
            get => HoraInicio.ToString("yyyy-MM-ddTHH:mm:ssZ");
            set
            {
                if (DateTime.TryParse(value?.Replace("[UTC]", ""), out var d))
                    HoraInicio = d;
            }
        }

        [JsonIgnore]
        public DateTime HoraFin { get; set; }

        [JsonPropertyName("horaFin")]
        public string? HoraFinStr
        {
            get => HoraFin.ToString("yyyy-MM-ddTHH:mm:ssZ");
            set
            {
                if (DateTime.TryParse(value?.Replace("[UTC]", ""), out var d))
                    HoraFin = d;
            }
        }

        [JsonPropertyName("cuposDisponibles")]
        public int CuposDisponibles { get; set; }

        [JsonPropertyName("salon")]
        public SalonDTO? Salon { get; set; }

        [JsonPropertyName("entrenador")]
        public EntrenadorDTO? Entrenador { get; set; }

        [JsonPropertyName("claseGrupal")]
        public ClaseGrupalDTO? ClaseGrupal { get; set; }
    }
}
