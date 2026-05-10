package pe.edu.pucp.gigachadsys.inter.clases;


import pe.edu.pucp.gigachadsys.dao.base.BaseDAO;
import pe.edu.pucp.gigachadsys.model.clases.SesionClase;

import java.util.List;

public interface SesionClaseDAO extends BaseDAO<SesionClase, Integer>{
    List<SesionClase> listAll();
}
