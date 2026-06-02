package pe.edu.pucp.gigachadsys.bl.impl.membresias;

import pe.edu.pucp.gigachadsys.bl.inter.membresias.MembresiaBasicBL;
import pe.edu.pucp.gigachadsys.dao.impl.membresias.MembresiaBasicDAOImpl;
import pe.edu.pucp.gigachadsys.dao.inter.membresias.MembresiaBasicDAO;
import pe.edu.pucp.gigachadsys.model.membresias.MembresiaBasic;

import java.util.List;

public class MembresiaBasicBLImpl implements MembresiaBasicBL {
    private MembresiaBasicDAO membresiaDAO = new MembresiaBasicDAOImpl();

    @Override
    public List<MembresiaBasic> listarMembresiasBlack() {
        return membresiaDAO.listAll();
    }
}
