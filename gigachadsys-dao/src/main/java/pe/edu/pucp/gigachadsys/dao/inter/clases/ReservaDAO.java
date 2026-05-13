package pe.edu.pucp.gigachadsys.dao.inter.clases;

import pe.edu.pucp.gigachadsys.dao.base.BaseDAO;
import pe.edu.pucp.gigachadsys.model.reservas.Reserva;

import java.util.List;

public interface ReservaDAO extends BaseDAO<Reserva, Integer> {
    List<Reserva> listAll();
}

