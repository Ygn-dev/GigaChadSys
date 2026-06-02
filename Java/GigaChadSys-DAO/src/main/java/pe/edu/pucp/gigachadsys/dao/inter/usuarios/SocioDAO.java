package pe.edu.pucp.gigachadsys.dao.inter.usuarios;


import pe.edu.pucp.gigachadsys.dao.base.BaseDAO;
import pe.edu.pucp.gigachadsys.model.personas.Entrenador;
import pe.edu.pucp.gigachadsys.model.personas.Socio;

import java.util.List;

public interface SocioDAO extends BaseDAO<Socio, Integer> {
    List<Socio> listAll();
    public Socio save(Socio socio);
    public Socio load(Integer id);
    public Socio update(Socio socio);
    public void remove(Socio socio);
}
