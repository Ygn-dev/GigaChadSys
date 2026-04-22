package pe.edu.pucp.gigachadsys.dao.impl;

import pe.edu.pucp.gigachadsys.dao.base.BaseDAO;
import pe.edu.pucp.gigachadsys.model.MembresiaBasic;
import pe.edu.pucp.gigachadsys.model.MembresiaBlack;

import java.util.List;

public interface MembresiaBlackDAO extends BaseDAO<MembresiaBlack, Integer> {
    List<MembresiaBlack> listAll();
}
