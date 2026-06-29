package pe.edu.pucp.gigachadsys.bl.impl.suscripcion;

import pe.edu.pucp.gigachadsys.bl.inter.suscripcion.SuscripcionBL;
import pe.edu.pucp.gigachadsys.dao.impl.suscripcion.SuscripcionDAOImpl;
import pe.edu.pucp.gigachadsys.dao.inter.suscripcion.SuscripcionDAO;
import pe.edu.pucp.gigachadsys.dao.impl.usuarios.SocioDAOImpl;
import pe.edu.pucp.gigachadsys.dao.inter.usuarios.SocioDAO;
import pe.edu.pucp.gigachadsys.model.membresias.Suscripcion;
import pe.edu.pucp.gigachadsys.model.personas.Socio;
import java.util.List;

public class SuscripcionBLImpl implements SuscripcionBL {
    private SuscripcionDAO suscripcionDAO = new SuscripcionDAOImpl();
    private SocioDAO socioDAO = new SocioDAOImpl();

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

            // AGREGAR: sincronizar estadoMembresia del Socio
            Socio socio = socioDAO.load(suscripcion.getIdUsuario());
            if (socio != null) {
                socio.setEstadoMembresia(true);
                socioDAO.update(socio);
            }

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
            Suscripcion suscripcion = suscripcionDAO.load(idSuscripcion); // CAMBIAR (antes creaba uno vacío)
            if (suscripcion == null) return "Suscripción no encontrada.";

            suscripcionDAO.remove(suscripcion);

            // AGREGAR: quitar membresía al socio
            Socio socio = socioDAO.load(suscripcion.getIdUsuario());
            if (socio != null) {
                socio.setEstadoMembresia(false);
                socioDAO.update(socio);
            }

            return "Suscripción eliminada correctamente.";
        } catch (Exception e) {
            return "Error al eliminar suscripción: " + e.getMessage();
        }
    }
}
