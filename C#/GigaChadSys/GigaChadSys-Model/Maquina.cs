namespace GigaChadSysModel;

public class Maquina
{
    public int IdMaquina { get; set; }
    public string Nombre { get; set; } = "";
    public string Marca { get; set; } = "";
    public string Estado { get; set; } = "";
    public DateTime? FechaUltimoMantenimiento { get; set; }
    public bool Activo { get; set; }

    public Maquina() { }

    public Maquina(int idMaquina, string nombre, string marca, string estado,
                   DateTime? fechaUltimoMantenimiento, bool activo)
    {
        IdMaquina = idMaquina;
        Nombre = nombre;
        Marca = marca;
        Estado = estado;
        FechaUltimoMantenimiento = fechaUltimoMantenimiento;
        Activo = activo;
    }
}