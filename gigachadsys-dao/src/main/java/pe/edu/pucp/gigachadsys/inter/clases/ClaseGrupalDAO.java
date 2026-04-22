package pe.edu.pucp.gigachadsys.inter.clases;

<<<<<<<< Updated upstream:gigachadsys-dao/src/main/java/pe/edu/pucp/gigachadsys/ClaseGrupalDAO.java
package pe.edu.pucp.gigachadsys;
========
package pe.edu.pucp.gigachadsys.dao;
>>>>>>>> Stashed changes:gigachadsys-dao/src/main/java/pe/edu/pucp/gigachadsys/dao/ClaseGrupalDAO.java
import pe.edu.pucp.gigachadsys.dao.base.BaseDAO;
import pe.edu.pucp.gigachadsys.model.ClaseGrupal;

import java.util.List;

public interface ClaseGrupalDAO extends BaseDAO<ClaseGrupal, Integer> {
    List<ClaseGrupal> listAll();
}

