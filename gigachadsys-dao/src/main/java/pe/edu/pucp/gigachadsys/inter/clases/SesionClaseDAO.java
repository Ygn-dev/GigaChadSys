package pe.edu.pucp.gigachadsys.inter.clases;

<<<<<<<< Updated upstream:gigachadsys-dao/src/main/java/pe/edu/pucp/gigachadsys/SesionClaseDAO.java
package pe.edu.pucp.gigachadsys;
========
package pe.edu.pucp.gigachadsys.dao;
>>>>>>>> Stashed changes:gigachadsys-dao/src/main/java/pe/edu/pucp/gigachadsys/dao/SesionClaseDAO.java

import pe.edu.pucp.gigachadsys.dao.base.BaseDAO;
import pe.edu.pucp.gigachadsys.model.SesionClase;

import java.util.List;

public interface SesionClaseDAO extends BaseDAO<SesionClase, Integer>{
    List<SesionClase> listAll();
}
