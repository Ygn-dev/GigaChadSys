using GigaChadSysDAO;
using GigaChadSysDBManager;
using GigaChadSysModel;
using MySql.Data.MySqlClient;

namespace GigaChadSysBL;

public class SuscripcionBL : SuscripcionIBL
{
    private readonly SuscripcionDAO suscripcionDAO;
    private readonly PagoDAO pagoDAO;
    private readonly SocioDAO socioDAO;

    public SuscripcionBL()
    {
        suscripcionDAO = new SuscripcionDAOImpl();
        pagoDAO = new PagoDAOImpl();
        socioDAO = new SocioDAOImpl();
    }

    public List<Suscripcion> ListAll()
    {
        return suscripcionDAO.ListAll();
    }

    public List<Suscripcion> ListBySocio(int idUsuario)
    {
        if (idUsuario <= 0)
        {
            throw new Exception("El id del socio no es válido.");
        }

        return suscripcionDAO.ListBySocio(idUsuario);
    }

    public Suscripcion? Load(int id)
    {
        if (id <= 0)
        {
            throw new Exception("El id de la suscripción no es válido.");
        }

        return suscripcionDAO.Load(id);
    }

    public Suscripcion? LoadActivaBySocio(int idUsuario)
    {
        if (idUsuario <= 0)
        {
            throw new Exception("El id del socio no es válido.");
        }

        return suscripcionDAO.LoadActivaBySocio(idUsuario);
    }

    public Suscripcion Save(Suscripcion suscripcion)
    {
        ValidarSuscripcion(suscripcion);

        if (suscripcion.IdPago <= 0)
        {
            throw new Exception("La suscripción debe tener un pago asociado.");
        }

        return suscripcionDAO.Save(suscripcion);
    }

    public Suscripcion RegistrarConPago(Suscripcion suscripcion, Pago pago)
    {
        ValidarSuscripcion(suscripcion);
        ValidarPago(pago);

        using MySqlConnection conexion = DBManager.Instance.GetConnection();
        using MySqlTransaction transaccion = conexion.BeginTransaction();

        try
        {
            pago.FechaPago ??= DateTime.Now;
            pago.Activo = true;

            pagoDAO.Save(pago, conexion, transaccion);

            suscripcion.IdPago = pago.IdPago;
            suscripcion.EstadoMembresia = true;
            suscripcion.Activo = true;

            suscripcionDAO.Save(suscripcion, conexion, transaccion);

            socioDAO.ActualizarEstadoMembresia(
                suscripcion.IdUsuario,
                true,
                conexion,
                transaccion
            );

            transaccion.Commit();

            return suscripcion;
        }
        catch (Exception)
        {
            transaccion.Rollback();
            throw;
        }
    }

    public Suscripcion? Update(Suscripcion suscripcion)
    {
        ValidarSuscripcion(suscripcion);

        if (suscripcion.IdSuscripcion <= 0)
        {
            throw new Exception("El id de la suscripción no es válido.");
        }

        if (suscripcion.IdPago <= 0)
        {
            throw new Exception("La suscripción debe tener un pago asociado.");
        }

        return suscripcionDAO.Update(suscripcion);
    }

    public void Remove(Suscripcion suscripcion)
    {
        if (suscripcion == null)
        {
            throw new Exception("La suscripción no puede ser nula.");
        }

        if (suscripcion.IdSuscripcion <= 0)
        {
            throw new Exception("El id de la suscripción no es válido.");
        }

        suscripcionDAO.Remove(suscripcion);
    }

    private void ValidarSuscripcion(Suscripcion suscripcion)
    {
        if (suscripcion == null)
        {
            throw new Exception("La suscripción no puede ser nula.");
        }

        if (suscripcion.IdUsuario <= 0)
        {
            throw new Exception("Debe seleccionar un socio válido.");
        }

        if (suscripcion.FechaIngreso == default)
        {
            throw new Exception("La fecha de ingreso es obligatoria.");
        }

        if (suscripcion.FechaFinMembresia == default)
        {
            throw new Exception("La fecha de fin de membresía es obligatoria.");
        }

        if (suscripcion.FechaFinMembresia <= suscripcion.FechaIngreso)
        {
            throw new Exception("La fecha de fin debe ser mayor a la fecha de ingreso.");
        }

        bool tieneBasic = suscripcion.MembresiaIdBasic.HasValue;
        bool tieneBlack = suscripcion.MembresiaIdBlack.HasValue;

        if (!tieneBasic && !tieneBlack)
        {
            throw new Exception("Debe seleccionar una membresía Basic o Black.");
        }

        if (tieneBasic && tieneBlack)
        {
            throw new Exception("La suscripción no puede tener Basic y Black al mismo tiempo.");
        }
    }

    private void ValidarPago(Pago pago)
    {
        if (pago == null)
        {
            throw new Exception("El pago no puede ser nulo.");
        }

        if (pago.MontoTotal <= 0)
        {
            throw new Exception("El monto del pago debe ser mayor a cero.");
        }

        if (pago.IdMetodoPago <= 0)
        {
            throw new Exception("Debe seleccionar un método de pago válido.");
        }

        if (string.IsNullOrWhiteSpace(pago.Tipo))
        {
            throw new Exception("El tipo de pago es obligatorio.");
        }
    }
}