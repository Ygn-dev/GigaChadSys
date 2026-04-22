package pe.edu.pucp.gigachadsys.inter.membresias;


import pe.edu.pucp.gigachadsys.dao.base.BaseDAO;
import pe.edu.pucp.gigachadsys.model.MembresiaBasic;

import java.util.List;

public interface MembresiaBasicDAO extends BaseDAO<MembresiaBasic, Integer> {
    List<MembresiaBasic> listAll();
}

