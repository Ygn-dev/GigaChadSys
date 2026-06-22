using System.Text.Json.Serialization;

namespace GigaChadSys.Servicios.DTO;

/// <summary>
/// DTO que mapea al JSON de Java para la entidad Administrador.
/// Hereda todos los campos de UsuarioDTO y añade sede, sueldo, cargo.
/// </summary>
public class AdministradorDTO : UsuarioDTO
{
    [JsonPropertyName("sede")]
    public string Sede { get; set; } = "";

    [JsonPropertyName("sueldo")]
    public double Sueldo { get; set; }

    [JsonPropertyName("cargo")]
    public string Cargo { get; set; } = "";
}
