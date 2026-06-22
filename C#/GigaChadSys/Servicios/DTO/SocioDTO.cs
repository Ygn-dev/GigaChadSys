using System.Text.Json.Serialization;

namespace GigaChadSys.Servicios.DTO;

/// <summary>
/// DTO que mapea al JSON de Java para la entidad Socio.
/// Hereda todos los campos de UsuarioDTO y añade estadoMembresia.
/// </summary>
public class SocioDTO : UsuarioDTO
{
    [JsonPropertyName("estadoMembresia")]
    public bool EstadoMembresia { get; set; }
}
