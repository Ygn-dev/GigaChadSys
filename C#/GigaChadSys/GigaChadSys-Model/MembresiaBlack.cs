namespace GigaChadSysModel;

public class MembresiaBlack : Membresia
{
    public decimal CostoMantenimientoAnual { get; set; }
    public int CantidadInvitadosPorMes { get; set; }

    public MembresiaBlack() { }

    public MembresiaBlack(int membresiaId, string nombrePlan,
                          decimal costoMantenimientoAnual,
                          int cantidadInvitadosPorMes, bool activo)
        : base(membresiaId, nombrePlan, activo)
    {
        CostoMantenimientoAnual = costoMantenimientoAnual;
        CantidadInvitadosPorMes = cantidadInvitadosPorMes;
    }
}