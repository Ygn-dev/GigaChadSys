package pe.edu.pucp.gigachadsys.dao.inter.maquinas;

import pe.edu.pucp.gigachadsys.dao.base.BaseDAO;
import pe.edu.pucp.gigachadsys.model.clases.Maquina;

import java.util.List;

public interface MaquinaDAO extends BaseDAO<Maquina, Integer> {
    List<Maquina> listAll();
}
