package pe.edu.pucp.gigachadsys.bl.inter.pagos;


import pe.edu.pucp.gigachadsys.dao.base.BaseDAO;
import pe.edu.pucp.gigachadsys.model.pagos.Pago;

import java.util.List;

public interface PagoDAO extends BaseDAO<Pago, Integer> {
    List<Pago> listAll();
}
