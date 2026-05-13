package pe.edu.pucp.gigachadsys.dao.inter.usuarios;

import pe.edu.pucp.gigachadsys.dao.base.BaseDAO;
import pe.edu.pucp.gigachadsys.model.personas.Administrador;

import java.util.List;

public interface AdministradorDAO extends BaseDAO<Administrador, Integer> {
    List<Administrador> listAll();
}
