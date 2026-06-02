using GigaChadSysDAO;
using GigaChadSysDBManager;
using GigaChadSysModel;
using MySql.Data.MySqlClient;

namespace GigaChadSysBL;

public class ReservaBL : ReservaIBL
{
    private readonly ReservaDAO reservaDAO;
    private readonly SesionClaseDAO sesionClaseDAO;
    private readonly SuscripcionDAO suscripcionDAO;

    public ReservaBL()
    {
        reservaDAO = new ReservaDAOImpl();
        sesionClaseDAO = new SesionClaseDAOImpl();
        suscripcionDAO = new SuscripcionDAOImpl();
    }

    public List<Reserva> ListAll()
    {
        return reservaDAO.ListAll();
    }

    public List<Reserva> ListBySocio(int idUsuario)
    {
        if (idUsuario <= 0)
        {
            throw new Exception("El id del socio no es válido.");
        }

        return reservaDAO.ListBySocio(idUsuario);
    }

    public List<Reserva> ListBySesion(int idSesion)
    {
        if (idSesion <= 0)
        {
            throw new Exception("El id de la sesión no es válido.");
        }

        return reservaDAO.ListBySesion(idSesion);
    }

    public Reserva? Load(int id)
    {
        if (id <= 0)
        {
            throw new Exception("El id de la reserva no es válido.");
        }

        return reservaDAO.Load(id);
    }

    public Reserva Save(Reserva reserva)
    {
        if (reserva == null)
        {
            throw new Exception("La reserva no puede ser nula.");
        }

        if (reserva.IdUsuario <= 0)
        {
            throw new Exception("Debe indicar un socio válido para la reserva.");
        }

        if (reserva.IdSesion <= 0)
        {
            throw new Exception("Debe indicar una sesión válida para la reserva.");
        }

        Suscripcion? suscripcionActiva = suscripcionDAO.LoadActivaBySocio(reserva.IdUsuario);

        if (suscripcionActiva == null)
        {
            throw new Exception("El socio no tiene una membresía activa. No puede reservar clases.");
        }

        SesionClase? sesion = sesionClaseDAO.Load(reserva.IdSesion);

        if (sesion == null || !sesion.Activo)
        {
            throw new Exception("La sesión seleccionada no existe o no está activa.");
        }

        if (sesion.CuposDisponibles <= 0)
        {
            throw new Exception("La sesión no tiene cupos disponibles.");
        }

        List<Reserva> reservasSocio = reservaDAO.ListBySocio(reserva.IdUsuario);

        bool yaTieneReserva = reservasSocio.Any(r =>
            r.IdSesion == reserva.IdSesion &&
            r.Activo
        );

        if (yaTieneReserva)
        {
            throw new Exception("El socio ya tiene una reserva activa para esta sesión.");
        }

        if (reserva.FechaHoraReserva == default)
        {
            reserva.FechaHoraReserva = DateTime.Now;
        }

        reserva.Asistio = false;
        reserva.Activo = true;

        using MySqlConnection conexion = DBManager.Instance.GetConnection();
        using MySqlTransaction transaccion = conexion.BeginTransaction();

        try
        {
            reservaDAO.Save(reserva, conexion, transaccion);
            sesionClaseDAO.DisminuirCupo(reserva.IdSesion, conexion, transaccion);

            transaccion.Commit();

            return reserva;
        }
        catch (Exception)
        {
            transaccion.Rollback();
            throw;
        }
    }

    public Reserva? Update(Reserva reserva)
    {
        if (reserva == null)
        {
            throw new Exception("La reserva no puede ser nula.");
        }

        if (reserva.IdReserva <= 0)
        {
            throw new Exception("El id de la reserva no es válido.");
        }

        if (reserva.IdUsuario <= 0)
        {
            throw new Exception("Debe indicar un socio válido para la reserva.");
        }

        if (reserva.IdSesion <= 0)
        {
            throw new Exception("Debe indicar una sesión válida para la reserva.");
        }

        return reservaDAO.Update(reserva);
    }

    public void Remove(Reserva reserva)
    {
        if (reserva == null)
        {
            throw new Exception("La reserva no puede ser nula.");
        }

        if (reserva.IdReserva <= 0)
        {
            throw new Exception("El id de la reserva no es válido.");
        }

        reservaDAO.Remove(reserva);
    }
}