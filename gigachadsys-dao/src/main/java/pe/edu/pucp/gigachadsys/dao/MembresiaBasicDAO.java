<<<<<<<< Updated upstream:gigachadsys-dao/src/main/java/pe/edu/pucp/gigachadsys/MembresiaBasicDAO.java
package pe.edu.pucp.gigachadsys;
========
package pe.edu.pucp.gigachadsys.dao;
>>>>>>>> Stashed changes:gigachadsys-dao/src/main/java/pe/edu/pucp/gigachadsys/dao/MembresiaBasicDAO.java

import pe.edu.pucp.gigachadsys.dao.base.BaseDAO;
import pe.edu.pucp.gigachadsys.model.MembresiaBasic;

import java.util.List;

public interface MembresiaBasicDAO extends BaseDAO<MembresiaBasic, Integer> {
    List<MembresiaBasic> listAll();
}

