using System.Text.Json.Serialization;

namespace GigaChadSys.Servicios.DTO;

/// <summary>
/// DTO que mapea al JSON de Java para la entidad Entrenador.
/// Hereda todos los campos de UsuarioDTO y añade especialidad, sueldo, tiempoTrabajado.
/// </summary>
public class EntrenadorDTO : UsuarioDTO
{
    [JsonPropertyName("especialidad")]
    public string Especialidad { get; set; } = "";

    [JsonPropertyName("sueldo")]
    public double Sueldo { get; set; }

    [JsonIgnore]
    public string? TiempoTrabajado { get; set; }
}
