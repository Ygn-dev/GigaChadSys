package pe.edu.pucp.gigachadsys.dao.impl;
import pe.edu.pucp.gigachadsys.dao.base.BaseDAO;
import pe.edu.pucp.gigachadsys.model.Entrenador;

import java.util.List;

public interface EntrenadorDAO extends BaseDAO<Entrenador, Integer> {
    List<Entrenador> listAll();
}

