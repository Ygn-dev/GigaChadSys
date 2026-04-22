package pe.edu.pucp.gigachadsys.inter.membresias;

import pe.edu.pucp.gigachadsys.dao.base.BaseDAO;
import pe.edu.pucp.gigachadsys.model.Membresia;

import java.util.List;

public interface MembresiaDAO extends BaseDAO<Membresia, Integer> {
    List<Membresia> listAll();
}
