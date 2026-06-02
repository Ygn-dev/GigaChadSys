namespace GigaChadSysModel;

public abstract class Usuario
{
    public int IdUsuario { get; set; }
    public string NombreCompleto { get; set; } = "";
    public string Dni { get; set; } = "";
    public string Email { get; set; } = "";
    public string Telefono { get; set; } = "";
    public string Contrasenia { get; set; } = "";
    public bool Activo { get; set; }

    public Usuario() { }

    public Usuario(int idUsuario, string nombreCompleto, string dni, string email,
                   string telefono, string contrasenia, bool activo)
    {
        IdUsuario = idUsuario;
        NombreCompleto = nombreCompleto;
        Dni = dni;
        Email = email;
        Telefono = telefono;
        Contrasenia = contrasenia;
        Activo = activo;
    }
}