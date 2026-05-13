package pe.edu.pucp.gigachadsys.dao.inter.suscripcion;


import pe.edu.pucp.gigachadsys.dao.base.BaseDAO;
import pe.edu.pucp.gigachadsys.model.membresias.Suscripcion;

import java.util.List;

public interface SuscripcionDAO extends BaseDAO<Suscripcion, Integer> {
    List<Suscripcion> listAll();
}
