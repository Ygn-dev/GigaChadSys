package pe.edu.pucp.gigachadsys.inter.clases;

import pe.edu.pucp.gigachadsys.dao.base.BaseDAO;
import pe.edu.pucp.gigachadsys.model.ClaseGrupal;

import java.util.List;

public interface ClaseGrupalDAO extends BaseDAO<ClaseGrupal, Integer> {
    List<ClaseGrupal> listAll();
}

