namespace GigaChadSysModel;

public class Reserva
{
    public int IdReserva { get; set; }
    public DateTime FechaHoraReserva { get; set; }
    public bool Asistio { get; set; }

    public int IdSesion { get; set; }
    public int IdUsuario { get; set; }

    public SesionClase? SesionClase { get; set; }
    public Socio? Socio { get; set; }

    public bool Activo { get; set; }

    public Reserva() { }

    public Reserva(int idReserva, DateTime fechaHoraReserva, bool asistio,
                   int idSesion, int idUsuario, bool activo)
    {
        IdReserva = idReserva;
        FechaHoraReserva = fechaHoraReserva;
        Asistio = asistio;
        IdSesion = idSesion;
        IdUsuario = idUsuario;
        Activo = activo;
    }

    public Reserva(int idReserva, DateTime fechaHoraReserva, bool asistio,
                   SesionClase sesionClase, Socio socio, bool activo)
    {
        IdReserva = idReserva;
        FechaHoraReserva = fechaHoraReserva;
        Asistio = asistio;

        SesionClase = sesionClase;
        Socio = socio;

        IdSesion = sesionClase.IdSesion;
        IdUsuario = socio.IdUsuario;

        Activo = activo;
    }
}