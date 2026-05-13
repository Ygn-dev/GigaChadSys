package pe.edu.pucp.gigachadsys.bl.impl.clases;

import pe.edu.pucp.gigachadsys.bl.inter.clases.SesionClaseBL;
import pe.edu.pucp.gigachadsys.dao.impl.clases.SesionClaseDAOImpl;
import pe.edu.pucp.gigachadsys.dao.inter.clases.SesionClaseDAO;
import pe.edu.pucp.gigachadsys.model.clases.SesionClase;

import java.util.List;

public class SesionClaseBLImpl implements SesionClaseBL {

    SesionClaseDAO sesionClase = new SesionClaseDAOImpl();
    @Override
    public List<SesionClase> listAll() {
        return sesionClase.listAll();
    }
}
