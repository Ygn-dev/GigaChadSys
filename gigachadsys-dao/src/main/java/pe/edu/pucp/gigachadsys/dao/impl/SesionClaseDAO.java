package pe.edu.pucp.gigachadsys.dao.impl;

import pe.edu.pucp.gigachadsys.dao.base.BaseDAO;
import pe.edu.pucp.gigachadsys.model.SesionClase;

import java.util.List;

public interface SesionClaseDAO extends BaseDAO<SesionClase, Integer>{
    List<SesionClase> listAll();
}
