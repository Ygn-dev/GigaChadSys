namespace GigaChadSysModel;

public class Pago
{
    public int IdPago { get; set; }
    public DateTime? FechaPago { get; set; }
    public decimal MontoTotal { get; set; }
    public string Tipo { get; set; } = "";

    public int IdMetodoPago { get; set; }
    public MetodoPago? MetodoPago { get; set; }

    public bool Activo { get; set; }

    public Pago() { }

    public Pago(int idPago, DateTime? fechaPago, decimal montoTotal, string tipo,
                int idMetodoPago, bool activo)
    {
        IdPago = idPago;
        FechaPago = fechaPago;
        MontoTotal = montoTotal;
        Tipo = tipo;
        IdMetodoPago = idMetodoPago;
        Activo = activo;
    }

    public Pago(int idPago, DateTime? fechaPago, decimal montoTotal, string tipo,
                MetodoPago metodoPago, bool activo)
    {
        IdPago = idPago;
        FechaPago = fechaPago;
        MontoTotal = montoTotal;
        Tipo = tipo;
        MetodoPago = metodoPago;
        IdMetodoPago = metodoPago.IdMetodoPago;
        Activo = activo;
    }
}