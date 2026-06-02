package pe.edu.pucp.gigachadsys.bl.impl.clases;

import pe.edu.pucp.gigachadsys.bl.inter.clases.SalonBL;
import pe.edu.pucp.gigachadsys.dao.impl.clases.SalonDAOImpl;
import pe.edu.pucp.gigachadsys.dao.inter.clases.SalonDAO;
import pe.edu.pucp.gigachadsys.model.clases.Salon;

import java.util.List;

public class SalonBLImpl implements SalonBL {
    SalonDAO salon = new SalonDAOImpl();
    @Override
    public List<Salon> listAll() {
        return salon.listAll();
    }
}
