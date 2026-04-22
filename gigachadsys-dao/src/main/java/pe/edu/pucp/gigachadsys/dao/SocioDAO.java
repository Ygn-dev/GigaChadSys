<<<<<<<< Updated upstream:gigachadsys-dao/src/main/java/pe/edu/pucp/gigachadsys/SocioDAO.java
package pe.edu.pucp.gigachadsys;
========
package pe.edu.pucp.gigachadsys.dao;
>>>>>>>> Stashed changes:gigachadsys-dao/src/main/java/pe/edu/pucp/gigachadsys/dao/SocioDAO.java

import pe.edu.pucp.gigachadsys.dao.base.BaseDAO;
import pe.edu.pucp.gigachadsys.model.Socio;

import java.util.List;

public interface SocioDAO extends BaseDAO<Socio, Integer> {
    List<Socio> listAll();
}
