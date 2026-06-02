namespace GigaChadSysModel;

public class Socio : Usuario
{
    public bool EstadoMembresia { get; set; }

    public List<Reserva> Reservas { get; set; } = new();
    public List<Suscripcion> Suscripciones { get; set; } = new();

    public Socio() { }

    public Socio(int idUsuario, string nombreCompleto, string dni, string email,
                 string telefono, string contrasenia, bool estadoMembresia,
                 bool activo)
        : base(idUsuario, nombreCompleto, dni, email, telefono, contrasenia, activo)
    {
        EstadoMembresia = estadoMembresia;
    }
}