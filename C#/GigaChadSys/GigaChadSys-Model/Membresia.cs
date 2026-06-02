namespace GigaChadSysModel;

public abstract class Membresia
{
    public int MembresiaId { get; set; }
    public string NombrePlan { get; set; } = "";
    public bool Activo { get; set; }

    public Membresia() { }

    public Membresia(int membresiaId, string nombrePlan, bool activo)
    {
        MembresiaId = membresiaId;
        NombrePlan = nombrePlan;
        Activo = activo;
    }
}