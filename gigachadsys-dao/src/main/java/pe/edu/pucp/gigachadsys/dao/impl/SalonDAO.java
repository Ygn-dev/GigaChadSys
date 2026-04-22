package pe.edu.pucp.gigachadsys.dao.impl;

import pe.edu.pucp.gigachadsys.dao.base.BaseDAO;
import pe.edu.pucp.gigachadsys.model.Salon;

import java.util.List;

public interface SalonDAO extends BaseDAO<Salon, Integer> {
    List<Salon> listAll();
}
