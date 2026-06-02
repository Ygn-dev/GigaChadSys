namespace GigaChadSysModel;

public class MembresiaBasic : Membresia
{
    public decimal CostoMantenimientoMensual { get; set; }

    public MembresiaBasic() { }

    public MembresiaBasic(int membresiaId, string nombrePlan,
                          decimal costoMantenimientoMensual, bool activo)
        : base(membresiaId, nombrePlan, activo)
    {
        CostoMantenimientoMensual = costoMantenimientoMensual;
    }
}