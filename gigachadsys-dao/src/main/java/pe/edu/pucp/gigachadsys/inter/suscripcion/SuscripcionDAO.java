package pe.edu.pucp.gigachadsys.inter.suscripcion;


import pe.edu.pucp.gigachadsys.dao.base.BaseDAO;
import pe.edu.pucp.gigachadsys.model.Suscripcion;

import java.util.List;

public interface SuscripcionDAO extends BaseDAO<Suscripcion, Integer> {
    List<Suscripcion> listAll();
}
