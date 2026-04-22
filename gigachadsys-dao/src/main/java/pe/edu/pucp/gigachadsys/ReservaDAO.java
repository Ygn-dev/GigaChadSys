package pe.edu.pucp.gigachadsys;
import pe.edu.pucp.gigachadsys.dao.base.BaseDAO;
import pe.edu.pucp.gigachadsys.model.Reserva;

import java.util.List;

public interface ReservaDAO extends BaseDAO<Reserva, Integer> {
    List<Reserva> listAll();
}

