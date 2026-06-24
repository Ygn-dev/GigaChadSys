package pe.edu.pucp.gigachadsys.bl.impl.clases;

import pe.edu.pucp.gigachadsys.bl.inter.clases.ReservaBL;
import pe.edu.pucp.gigachadsys.dao.impl.clases.ReservaDAOImpl;
import pe.edu.pucp.gigachadsys.dao.inter.clases.ReservaDAO;
import pe.edu.pucp.gigachadsys.model.clases.Reserva;

import java.util.List;

public class ReservaBLImpl implements ReservaBL {

    private final ReservaDAO reservaDAO = new ReservaDAOImpl();

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
            Reserva reserva = new Reserva();
            reserva.setIdReserva(idReserva);
            reservaDAO.remove(reserva);
            return "Reserva eliminada correctamente.";
        } catch (Exception e) {
            return "Error al eliminar reserva: " + e.getMessage();
        }
    }
}