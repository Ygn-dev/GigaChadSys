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

        // Compatibilidad por si Java también manda "active".
        [JsonPropertyName("active")]
        public bool Active
        {
            get => Activo;
            set => Activo = value;
        }

        [JsonIgnore]
        public string Nombre => ObtenerNombreClase(NombreDisciplina);

        [JsonIgnore]
        public string Disciplina => ObtenerDisciplina(NombreDisciplina);

        [JsonIgnore]
        public string NivelDificultad => Nivel;

        [JsonIgnore]
        public int Vacantes => 0;

        private static string ObtenerDisciplina(string texto)
        {
            if (string.IsNullOrWhiteSpace(texto))
                return "";

            if (texto.Contains(" - "))
            {
                var partes = texto.Split(new[] { " - " }, 2, StringSplitOptions.None);
                return partes[0].Trim();
            }

            return texto.Trim();
        }

        private static string ObtenerNombreClase(string texto)
        {
            if (string.IsNullOrWhiteSpace(texto))
                return "";

            if (texto.Contains(" - "))
            {
                var partes = texto.Split(new[] { " - " }, 2, StringSplitOptions.None);
                return partes[1].Trim();
            }

            return texto.Trim();
        }
    }
}