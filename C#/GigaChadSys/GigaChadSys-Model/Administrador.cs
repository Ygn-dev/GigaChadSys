namespace GigaChadSysModel;

public class Administrador : Usuario
{
    public string Sede { get; set; } = "";
    public decimal Sueldo { get; set; }
    public string Cargo { get; set; } = "";

    public Administrador() { }

    public Administrador(int idUsuario, string nombreCompleto, string dni, string email,
                         string telefono, string contrasenia, string sede,
                         decimal sueldo, string cargo, bool activo)
        : base(idUsuario, nombreCompleto, dni, email, telefono, contrasenia, activo)
    {
        Sede = sede;
        Sueldo = sueldo;
        Cargo = cargo;
    }
}