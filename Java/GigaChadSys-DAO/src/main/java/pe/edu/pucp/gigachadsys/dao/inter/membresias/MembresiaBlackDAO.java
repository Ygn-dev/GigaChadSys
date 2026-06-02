package pe.edu.pucp.gigachadsys.dao.inter.membresias;

import pe.edu.pucp.gigachadsys.dao.base.BaseDAO;
import pe.edu.pucp.gigachadsys.model.membresias.MembresiaBlack;

import java.util.List;

public interface MembresiaBlackDAO extends BaseDAO<MembresiaBlack, Integer> {
    List<MembresiaBlack> listAll();
}
