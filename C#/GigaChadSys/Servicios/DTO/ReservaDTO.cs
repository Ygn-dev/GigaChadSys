using System.Text.Json.Serialization;

namespace GigaChadSys.Servicios.DTO;

/// <summary>
/// DTO que mapea al JSON de Java para la entidad Reserva.
/// Java: Reserva.java
/// BD: Reserva(idReserva, fechaHoraReserva, asistio, idSesion, idUsuario, activo)
/// </summary>
public class ReservaDTO
{
    [JsonPropertyName("idReserva")]
    public int IdReserva { get; set; }

    [JsonIgnore]
    public DateTime? FechaHoraReserva { get; set; }

    [JsonPropertyName("fechaHoraReserva")]
    public string? FechaHoraReservaStr
    {
        get => FechaHoraReserva?.ToString("yyyy-MM-dd'T'HH:mm:ss");
        set
        {
            if (string.IsNullOrWhiteSpace(value))
            {
                FechaHoraReserva = null;
                return;
            }

            var limpio = value.Replace("[UTC]", "").Replace("Z", "").Trim();

            if (DateTime.TryParse(limpio, out var fecha))
                FechaHoraReserva = DateTime.SpecifyKind(fecha, DateTimeKind.Unspecified);
            else
                FechaHoraReserva = null;
        }
    }

    [JsonPropertyName("asistio")]
    public bool Asistio { get; set; }

    [JsonPropertyName("sesionClase")]
    public SesionClaseDTO? SesionClase { get; set; }

    [JsonPropertyName("idUsuario")]
    public int IdUsuario { get; set; }

    [JsonPropertyName("activo")]
    public bool Activo { get; set; } = true;

    [JsonPropertyName("active")]
    public bool Active
    {
        get => Activo;
        set => Activo = value;
    }

    [JsonIgnore]
    public string FechaTexto => FechaHoraReserva.HasValue
        ? FechaHoraReserva.Value.ToString("yyyy-MM-dd HH:mm")
        : "—";

    [JsonIgnore]
    public string EstadoTexto
    {
        get
        {
            if (!Activo)
                return "Canceladas";

            if (Asistio)
                return "Asistió";

            if (SesionClase != null && SesionClase.FechaHoraFin != default && SesionClase.FechaHoraFin < DateTime.Now)
                return "Pendientes";

            return "Confirmadas";
        }
    }
}