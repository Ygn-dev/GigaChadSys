namespace GigaChadSysModel;

public class MetodoPago
{
    public int IdMetodoPago { get; set; }
    public string Tipo { get; set; } = "";
    public string Detalle { get; set; } = "";
    public bool Activo { get; set; }

    public MetodoPago() { }

    public MetodoPago(int idMetodoPago, string tipo, string detalle, bool activo)
    {
        IdMetodoPago = idMetodoPago;
        Tipo = tipo;
        Detalle = detalle;
        Activo = activo;
    }
}