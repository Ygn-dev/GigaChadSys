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

    @Override
    public Suscripcion obtenerPorId(int idSuscripcion) {
        return suscripcionDAO.load(idSuscripcion);
    }

    @Override
    public String registrar(Suscripcion suscripcion) {
        try {
            suscripcionDAO.save(suscripcion);
            return "Suscripción registrada correctamente.";
        } catch (Exception e) {
            return "Error al registrar suscripción: " + e.getMessage();
        }
    }

    @Override
    public String actualizar(int idSuscripcion, Suscripcion suscripcion) {
        try {
            suscripcion.setIdSuscripcion(idSuscripcion);
            suscripcionDAO.update(suscripcion);
            return "Suscripción actualizada correctamente.";
        } catch (Exception e) {
            return "Error al actualizar suscripción: " + e.getMessage();
        }
    }

    @Override
    public String eliminar(int idSuscripcion) {
        try {
            Suscripcion suscripcion = new Suscripcion();
            suscripcion.setIdSuscripcion(idSuscripcion);
            suscripcion.setActive(false);
            suscripcionDAO.remove(suscripcion);
            return "Suscripción eliminada correctamente.";
        } catch (Exception e) {
            return "Error al eliminar suscripción: " + e.getMessage();
        }
    }
}
