using System.Text.Json.Serialization;

namespace GigaChadSys.Servicios.DTO;

/// <summary>
/// DTO base que mapea exactamente al JSON que devuelve el backend Java
/// para cualquier subtipo de Usuario (Socio, Entrenador, Administrador).
///
/// Campos JSON de Java (JSON-B convierte getters a camelCase):
///   idUsuario, nombres, apellidoPaterno, apellidoMaterno, edad, dni, email,
///   telefono, contrasenia, rol, activo
/// </summary>
public class UsuarioDTO
{
    [JsonPropertyName("idUsuario")]
    public int IdUsuario { get; set; }

    [JsonPropertyName("nombres")]
    public string Nombres { get; set; } = "";

    [JsonPropertyName("apellidoPaterno")]
    public string ApellidoPaterno { get; set; } = "";

    [JsonPropertyName("apellidoMaterno")]
    public string ApellidoMaterno { get; set; } = "";

    [JsonPropertyName("edad")]
    public int Edad { get; set; }

    [JsonPropertyName("dni")]
    public int Dni { get; set; }

    [JsonPropertyName("email")]
    public string Email { get; set; } = "";

    [JsonPropertyName("telefono")]
    public int Telefono { get; set; }

    [JsonPropertyName("contrasenia")]
    public string Contrasenia { get; set; } = "";

    [JsonPropertyName("rol")]
    public string Rol { get; set; } = "";

    [JsonPropertyName("activo")]
    public bool Activo { get; set; }

    /// <summary>
    /// Nombre completo formateado: "Nombres ApellidoPaterno ApellidoMaterno"
    /// </summary>
    [JsonIgnore]
    public string NombreCompleto =>
        $"{Nombres} {ApellidoPaterno} {ApellidoMaterno}".Trim();
}
