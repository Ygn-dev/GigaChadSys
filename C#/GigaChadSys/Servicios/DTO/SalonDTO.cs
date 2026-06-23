using System.Text.Json.Serialization;

namespace GigaChadSys.Servicios.DTO
{
    public class SalonDTO
    {
        [JsonPropertyName("idSalon")]
        public int IdSalon { get; set; }

        [JsonPropertyName("nombreSalon")]
        public string NombreSalon { get; set; } = string.Empty;

        [JsonPropertyName("aforoMaximo")]
        public int AforoMaximo { get; set; }

        [JsonPropertyName("active")]
        public bool Estado { get; set; } // Java uses isActive() / setActive() mapping to "active"

        // ── Alias de compatibilidad para código existente ──────────────────
        [JsonIgnore]
        public string Nombre => NombreSalon;

        [JsonIgnore]
        public int CapacidadMax => AforoMaximo;
    }
}
