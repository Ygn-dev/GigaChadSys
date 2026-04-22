package pe.edu.pucp.gigachadsys;

import pe.edu.pucp.gigachadsys.dao.base.BaseDAO;
import pe.edu.pucp.gigachadsys.model.Administrador;

import java.util.List;

public interface AdministradorDAO extends BaseDAO<Administrador, Integer> {
    List<Administrador> listAll();
}
