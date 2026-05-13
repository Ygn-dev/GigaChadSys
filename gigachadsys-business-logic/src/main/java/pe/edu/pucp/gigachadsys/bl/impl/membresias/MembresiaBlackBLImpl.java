package pe.edu.pucp.gigachadsys.bl.impl.membresias;

import pe.edu.pucp.gigachadsys.bl.inter.membresias.MembresiaBlackBL;
import pe.edu.pucp.gigachadsys.dao.impl.membresias.MembresiaBlackDAOImpl;
import pe.edu.pucp.gigachadsys.dao.inter.membresias.MembresiaBlackDAO;
import pe.edu.pucp.gigachadsys.model.membresias.MembresiaBlack;

import java.util.List;

public class MembresiaBlackBLImpl implements MembresiaBlackBL {
    private MembresiaBlackDAO membresiaDAO = new MembresiaBlackDAOImpl();

    @Override
    public List<MembresiaBlack> listarMembresiasBlack() {
        return membresiaDAO.listAll();
    }
}
