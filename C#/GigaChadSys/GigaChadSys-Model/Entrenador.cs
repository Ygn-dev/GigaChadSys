namespace GigaChadSysModel;

public class Entrenador : Usuario
{
    public string Especialidad { get; set; } = "";
    public decimal Sueldo { get; set; }
    public TimeSpan TiempoTrabajo { get; set; }

    public Entrenador() { }

    public Entrenador(int idUsuario, string nombreCompleto, string dni, string email,
                      string telefono, string contrasenia, string especialidad,
                      decimal sueldo, TimeSpan tiempoTrabajo, bool activo)
        : base(idUsuario, nombreCompleto, dni, email, telefono, contrasenia, activo)
    {
        Especialidad = especialidad;
        Sueldo = sueldo;
        TiempoTrabajo = tiempoTrabajo;
    }
}