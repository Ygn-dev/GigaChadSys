package pe.edu.pucp.gigachadsys.bl.impl.clases;

import pe.edu.pucp.gigachadsys.dao.impl.clases.SesionClaseDAOImpl;
import pe.edu.pucp.gigachadsys.dao.inter.clases.SesionClaseDAO;
import pe.edu.pucp.gigachadsys.model.clases.SesionClase;

import pe.edu.pucp.gigachadsys.bl.inter.clases.ReservaBL;
import pe.edu.pucp.gigachadsys.dao.impl.clases.ReservaDAOImpl;
import pe.edu.pucp.gigachadsys.dao.inter.clases.ReservaDAO;
import pe.edu.pucp.gigachadsys.model.clases.Reserva;

import java.util.List;

public class ReservaBLImpl implements ReservaBL {

    private final ReservaDAO reservaDAO = new ReservaDAOImpl();
    private final SesionClaseDAO sesionClaseDAO = new SesionClaseDAOImpl();

    @Override
    public List<Reserva> listAll() {
        return reservaDAO.listAll();
    }

    @Override
    public Reserva obtenerPorId(int idReserva) {
        return reservaDAO.load(idReserva);
    }

    @Override
    public String registrar(Reserva reserva) {
        try {
            reservaDAO.save(reserva);

            // AGREGAR: decrementar cupos disponibles
            int idSesion = reserva.getSesionClase() != null
                    ? reserva.getSesionClase().getIdSesion() : 0;

            if (idSesion > 0) {
                SesionClase sesion = sesionClaseDAO.load(idSesion);
                if (sesion != null && sesion.getCuposDisponibles() > 0) {
                    sesion.setCuposDisponibles(sesion.getCuposDisponibles() - 1);
                    sesionClaseDAO.update(sesion);
                }
            }

            return "Reserva registrada correctamente.";
        } catch (Exception e) {
            return "Error al registrar reserva: " + e.getMessage();
        }
    }

    @Override
    public String actualizar(int idReserva, Reserva reserva) {
        try {
            reserva.setIdReserva(idReserva);
            reservaDAO.update(reserva);
            return "Reserva actualizada correctamente.";
        } catch (Exception e) {
            return "Error al actualizar reserva: " + e.getMessage();
        }
    }

    @Override
    public String eliminar(int idReserva) {
        try {
            Reserva reserva = reservaDAO.load(idReserva); // CAMBIAR: cargar primero para obtener idSesion

            if (reserva == null) return "Reserva no encontrada.";

            reservaDAO.remove(reserva);

            // AGREGAR: devolver el cupo al cancelar
            int idSesion = reserva.getSesionClase() != null
                    ? reserva.getSesionClase().getIdSesion()
                    : 0;
            if (idSesion > 0) {
                SesionClase sesion = sesionClaseDAO.load(idSesion);
                if (sesion != null) {
                    sesion.setCuposDisponibles(sesion.getCuposDisponibles() + 1);
                    sesionClaseDAO.update(sesion);
                }
            }

            return "Reserva eliminada correctamente.";
        } catch (Exception e) {
            return "Error al eliminar reserva: " + e.getMessage();
        }
    }
}