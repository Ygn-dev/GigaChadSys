package pe.edu.pucp.gigachadsys.dao.inter.pagos;

import pe.edu.pucp.gigachadsys.dao.base.BaseDAO;
import pe.edu.pucp.gigachadsys.model.pagos.MetodoPago;

import java.util.List;

public interface MetodoPagoDAO extends BaseDAO<MetodoPago, Integer> {

    List<MetodoPago> listAll();
}
