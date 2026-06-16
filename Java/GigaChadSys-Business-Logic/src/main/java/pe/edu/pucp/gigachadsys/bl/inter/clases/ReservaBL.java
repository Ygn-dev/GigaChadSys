package pe.edu.pucp.gigachadsys.bl.inter.clases;

import pe.edu.pucp.gigachadsys.model.clases.Reserva;

import java.util.List;

public interface ReservaBL {
    List<Reserva> listAll();
    Reserva obtenerPorId(int idReserva);
    String registrar(Reserva reserva);
    String actualizar(int idReserva, Reserva reserva);
    String eliminar(int idReserva);
}
