using System.Text.Json.Serialization;

namespace GigaChadSys.Servicios.DTO;

/// <summary>
/// DTO que mapea al JSON de Java para la entidad Entrenador.
/// Hereda todos los campos de UsuarioDTO y añade especialidad, sueldo y tiempoTrabajadoTexto.
/// </summary>
public class EntrenadorDTO : UsuarioDTO
{
    [JsonPropertyName("especialidad")]
    public string Especialidad { get; set; } = "";

    [JsonPropertyName("sueldo")]
    public double Sueldo { get; set; }

    /// <summary>
    /// Java expone este campo como tiempoTrabajadoTexto porque el Time real está oculto con @JsonbTransient.
    /// Ejemplo: "08:00:00", "04:00:00", "02:00:00".
    /// </summary>
    [JsonPropertyName("tiempoTrabajadoTexto")]
    public string? TiempoTrabajadoTexto { get; set; }

    /// <summary>
    /// Alias de compatibilidad para pantallas antiguas que usan TiempoTrabajado.
    /// No se serializa directamente; usa TiempoTrabajadoTexto.
    /// </summary>
    [JsonIgnore]
    public string? TiempoTrabajado
    {
        get => TiempoTrabajadoTexto;
        set => TiempoTrabajadoTexto = value;
    }

    [JsonIgnore]
    public string TiempoTrabajoDescripcion
    {
        get
        {
            return TiempoTrabajadoTexto switch
            {
                "08:00:00" => "Tiempo completo - 8 horas",
                "04:00:00" => "Medio tiempo - 4 horas",
                "02:00:00" => "Por horas - 2 horas",
                null or "" => "—",
                _ => TiempoTrabajadoTexto
            };
        }
    }

    [JsonIgnore]
    public string SueldoTexto => $"S/ {Sueldo:0.00}";
}