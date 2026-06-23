using System.Text.Json.Serialization;

namespace GigaChadSys.Servicios.DTO
{
    public class SesionClaseDTO
    {
        [JsonPropertyName("idSesion")]
        public int IdSesion { get; set; }

        [JsonPropertyName("fechaSesion")]
        public DateTime FechaSesion { get; set; }

        [JsonPropertyName("horaInicio")]
        public DateTime HoraInicio { get; set; }

        [JsonPropertyName("horaFin")]
        public DateTime HoraFin { get; set; }

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
