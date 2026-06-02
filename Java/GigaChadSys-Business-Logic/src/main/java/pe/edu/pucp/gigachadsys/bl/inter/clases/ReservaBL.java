package pe.edu.pucp.gigachadsys.bl.inter.clases;

import pe.edu.pucp.gigachadsys.dao.base.BaseDAO;
import pe.edu.pucp.gigachadsys.model.clases.Reserva;

import java.util.List;

public interface ReservaBL {
    List<Reserva> listAll();
}

