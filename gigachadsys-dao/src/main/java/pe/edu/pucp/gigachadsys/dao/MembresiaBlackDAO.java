<<<<<<<< Updated upstream:gigachadsys-dao/src/main/java/pe/edu/pucp/gigachadsys/MembresiaBlackDAO.java
package pe.edu.pucp.gigachadsys;
========
package pe.edu.pucp.gigachadsys.dao;
>>>>>>>> Stashed changes:gigachadsys-dao/src/main/java/pe/edu/pucp/gigachadsys/dao/MembresiaBlackDAO.java

import pe.edu.pucp.gigachadsys.dao.base.BaseDAO;
import pe.edu.pucp.gigachadsys.model.MembresiaBlack;

import java.util.List;

public interface MembresiaBlackDAO extends BaseDAO<MembresiaBlack, Integer> {
    List<MembresiaBlack> listAll();
}
