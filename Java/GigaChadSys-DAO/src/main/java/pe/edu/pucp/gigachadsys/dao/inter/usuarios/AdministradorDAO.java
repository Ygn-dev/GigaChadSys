package pe.edu.pucp.gigachadsys.dao.inter.usuarios;

import pe.edu.pucp.gigachadsys.dao.base.BaseDAO;
import pe.edu.pucp.gigachadsys.dao.manager.DBManager;
import pe.edu.pucp.gigachadsys.model.personas.Administrador;

import java.sql.*;
import java.util.List;

public interface AdministradorDAO extends BaseDAO<Administrador, Integer> {
    List<Administrador> listAll();
    public Administrador save(Administrador administrador);
    public Administrador load(Integer id);
    public Administrador update(Administrador administrador);
    public void remove(Administrador administrador);
}
