<<<<<<<< Updated upstream:gigachadsys-dao/src/main/java/pe/edu/pucp/gigachadsys/SuscripcionDAO.java
package pe.edu.pucp.gigachadsys;
========
package pe.edu.pucp.gigachadsys.dao;
>>>>>>>> Stashed changes:gigachadsys-dao/src/main/java/pe/edu/pucp/gigachadsys/dao/SuscripcionDAO.java

import pe.edu.pucp.gigachadsys.dao.base.BaseDAO;
import pe.edu.pucp.gigachadsys.model.Suscripcion;

import java.util.List;

public interface SuscripcionDAO extends BaseDAO<Suscripcion, Integer> {
    List<Suscripcion> listAll();
}
