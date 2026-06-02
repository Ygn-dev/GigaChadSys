namespace GigaChadSysModel;

public class SesionClase
{
    public int IdSesion { get; set; }
    public DateTime FechaSesion { get; set; }
    public DateTime HoraInicio { get; set; }
    public DateTime HoraFin { get; set; }
    public int CuposDisponibles { get; set; }
    public bool Activo { get; set; }

    public int IdSalon { get; set; }
    public int IdEntrenador { get; set; }
    public int IdClase { get; set; }

    public Salon? Salon { get; set; }
    public Entrenador? Entrenador { get; set; }
    public ClaseGrupal? ClaseGrupal { get; set; }

    public List<Reserva> Reservas { get; set; } = new();

    public SesionClase() { }

    public SesionClase(int idSesion, DateTime fechaSesion, DateTime horaInicio,
                       DateTime horaFin, int cuposDisponibles, int idSalon,
                       int idEntrenador, int idClase, bool activo)
    {
        IdSesion = idSesion;
        FechaSesion = fechaSesion;
        HoraInicio = horaInicio;
        HoraFin = horaFin;
        CuposDisponibles = cuposDisponibles;
        IdSalon = idSalon;
        IdEntrenador = idEntrenador;
        IdClase = idClase;
        Activo = activo;
    }

    public SesionClase(int idSesion, DateTime fechaSesion, DateTime horaInicio,
                       DateTime horaFin, int cuposDisponibles, Salon salon,
                       Entrenador entrenador, ClaseGrupal claseGrupal, bool activo)
    {
        IdSesion = idSesion;
        FechaSesion = fechaSesion;
        HoraInicio = horaInicio;
        HoraFin = horaFin;
        CuposDisponibles = cuposDisponibles;

        Salon = salon;
        Entrenador = entrenador;
        ClaseGrupal = claseGrupal;

        IdSalon = salon.IdSalon;
        IdEntrenador = entrenador.IdUsuario;
        IdClase = claseGrupal.IdClase;

        Activo = activo;
    }
}