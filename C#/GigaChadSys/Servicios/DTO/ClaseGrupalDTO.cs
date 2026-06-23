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
    }
}
