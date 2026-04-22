package pe.edu.pucp.gigachadsys;

import pe.edu.pucp.gigachadsys.dao.base.BaseDAO;
import pe.edu.pucp.gigachadsys.model.MetodoPago;

import java.util.List;

public interface MetodoPagoDAO extends BaseDAO<MetodoPago, Integer> {

    List<MetodoPago> listAll();
}
