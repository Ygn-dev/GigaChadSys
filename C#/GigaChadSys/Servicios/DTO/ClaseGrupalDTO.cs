using System.Text.Json.Serialization;

namespace GigaChadSys.Servicios.DTO
{
    public class ClaseGrupalDTO
    {
        [JsonPropertyName("idClase")]
        public int IdClase { get; set; }

        [JsonPropertyName("nombreDisciplina")]
        public string NombreDisciplina { get; set; } = string.Empty;

        [JsonPropertyName("descripcion")]
        public string Descripcion { get; set; } = string.Empty;

        [JsonPropertyName("duracionMinutos")]
        public int DuracionMinutos { get; set; }

        [JsonPropertyName("nivel")]
        public string Nivel { get; set; } = string.Empty;

        [JsonPropertyName("activo")]
        public bool Activo { get; set; }

        // ── Alias de compatibilidad para código existente ──────────────────
        [JsonIgnore]
        public string Nombre => NombreDisciplina;

        [JsonIgnore]
        public string NivelDificultad => Nivel;

        [JsonIgnore]
        public int Vacantes => 0; // Placeholder: calculado en el frontend
    }
}
