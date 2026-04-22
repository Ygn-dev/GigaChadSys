package pe.edu.pucp.gigachadsys.inter.usuarios;


import pe.edu.pucp.gigachadsys.dao.base.BaseDAO;
import pe.edu.pucp.gigachadsys.model.Socio;

import java.util.List;

public interface SocioDAO extends BaseDAO<Socio, Integer> {
    List<Socio> listAll();
}
