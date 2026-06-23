using System.Text.Json.Serialization;

namespace GigaChadSys.Servicios.DTO;

/// <summary>
/// DTO que mapea al JSON de Java para la entidad Reserva.
/// Java: Reserva.java — campos: idReserva, fechaHoraReserva, asistio, sesionClase
/// BD: Reserva(idReserva, fechaHoraReserva, asistio, idSesion, idUsuario, activo)
/// Endpoint: GET/POST /ReservaRS
/// </summary>
public class ReservaDTO
{
    [JsonPropertyName("idReserva")]
    public int IdReserva { get; set; }

    /// <summary>
    /// Java: Timestamp fechaHoraReserva → se serializa como string ISO.
    /// </summary>
    [JsonPropertyName("fechaHoraReserva")]
    public DateTime? FechaHoraReserva { get; set; }

    /// <summary>
    /// true = el socio asistió a la clase.
    /// Java getter: isAsistio()
    /// </summary>
    [JsonPropertyName("asistio")]
    public bool Asistio { get; set; }

    /// <summary>
    /// Objeto anidado de la sesión de clase.
    /// Java getter: getSesionClase()
    /// </summary>
    [JsonPropertyName("sesionClase")]
    public SesionClaseDTO? SesionClase { get; set; }

    /// <summary>
    /// FK del socio que realizó la reserva.
    /// No está en el modelo Java como campo directo, pero sí en la BD.
    /// Se incluye aquí para cuando se crea una nueva reserva (POST).
    /// </summary>
    [JsonPropertyName("idUsuario")]
    public int IdUsuario { get; set; }

    // ── Propiedades calculadas para la UI ──────────────────────────────────
    public string FechaTexto => FechaHoraReserva.HasValue
        ? FechaHoraReserva.Value.ToString("yyyy-MM-dd HH:mm")
        : "—";

    public string EstadoTexto => Asistio ? "Asistió" : "Confirmadas";
}
