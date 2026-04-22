package pe.edu.pucp.gigachadsys.dao.impl;

import pe.edu.pucp.gigachadsys.dao.base.BaseDAO;
import pe.edu.pucp.gigachadsys.model.Pago;

import java.util.List;

public interface PagoDAO extends BaseDAO<Pago, Integer> {
    List<Pago> listAll();
}
