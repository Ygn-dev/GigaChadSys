namespace GigaChadSysModel;

public class Suscripcion
{
    public int IdSuscripcion { get; set; }
    public bool EstadoMembresia { get; set; }
    public DateTime FechaIngreso { get; set; }
    public DateTime FechaFinMembresia { get; set; }

    public int IdPago { get; set; }
    public int IdUsuario { get; set; }

    public int? MembresiaIdBasic { get; set; }
    public int? MembresiaIdBlack { get; set; }

    public Pago? Pago { get; set; }
    public Socio? Socio { get; set; }
    public MembresiaBasic? MembresiaBasic { get; set; }
    public MembresiaBlack? MembresiaBlack { get; set; }

    public bool Activo { get; set; }

    public Suscripcion() { }

    public Suscripcion(int idSuscripcion, bool estadoMembresia,
                       DateTime fechaIngreso, DateTime fechaFinMembresia,
                       int idPago, int idUsuario, int? membresiaIdBasic,
                       int? membresiaIdBlack, bool activo)
    {
        IdSuscripcion = idSuscripcion;
        EstadoMembresia = estadoMembresia;
        FechaIngreso = fechaIngreso;
        FechaFinMembresia = fechaFinMembresia;
        IdPago = idPago;
        IdUsuario = idUsuario;
        MembresiaIdBasic = membresiaIdBasic;
        MembresiaIdBlack = membresiaIdBlack;
        Activo = activo;
    }

    public Suscripcion(int idSuscripcion, bool estadoMembresia,
                       DateTime fechaIngreso, DateTime fechaFinMembresia,
                       Pago pago, Socio socio, MembresiaBasic? membresiaBasic,
                       MembresiaBlack? membresiaBlack, bool activo)
    {
        IdSuscripcion = idSuscripcion;
        EstadoMembresia = estadoMembresia;
        FechaIngreso = fechaIngreso;
        FechaFinMembresia = fechaFinMembresia;

        Pago = pago;
        Socio = socio;
        MembresiaBasic = membresiaBasic;
        MembresiaBlack = membresiaBlack;

        IdPago = pago.IdPago;
        IdUsuario = socio.IdUsuario;
        MembresiaIdBasic = membresiaBasic?.MembresiaId;
        MembresiaIdBlack = membresiaBlack?.MembresiaId;

        Activo = activo;
    }
}