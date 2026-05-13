package pe.edu.pucp.gigachadsys.bl.impl.suscripcion;

import pe.edu.pucp.gigachadsys.bl.inter.suscripcion.SuscripcionBL;
import pe.edu.pucp.gigachadsys.dao.impl.suscripcion.SuscripcionDAOImpl;
import pe.edu.pucp.gigachadsys.dao.inter.suscripcion.SuscripcionDAO;
import pe.edu.pucp.gigachadsys.model.membresias.Suscripcion;

import java.util.List;

public class SuscripcionBLImpl implements SuscripcionBL {
    private SuscripcionDAO suscripcionDAO = new SuscripcionDAOImpl();


    @Override
    public List<Suscripcion> listarSuscripciones() {
        return suscripcionDAO.listAll();
    }
}
