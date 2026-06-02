package pe.edu.pucp.gigachadsys.dao.inter.usuarios;

import pe.edu.pucp.gigachadsys.dao.base.BaseDAO;
import pe.edu.pucp.gigachadsys.model.personas.Administrador;
import pe.edu.pucp.gigachadsys.model.personas.Entrenador;

import java.util.List;

public interface EntrenadorDAO extends BaseDAO<Entrenador, Integer> {
    List<Entrenador> listAll();
    public Entrenador save(Entrenador entrenador);
    public Entrenador load(Integer id);
    public Entrenador update(Entrenador entrenador);
    public void remove(Entrenador entrenador);
}

