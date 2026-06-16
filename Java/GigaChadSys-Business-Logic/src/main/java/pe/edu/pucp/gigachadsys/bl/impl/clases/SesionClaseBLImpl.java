package pe.edu.pucp.gigachadsys.bl.impl.clases;

import pe.edu.pucp.gigachadsys.bl.inter.clases.SesionClaseBL;
import pe.edu.pucp.gigachadsys.dao.impl.clases.SesionClaseDAOImpl;
import pe.edu.pucp.gigachadsys.dao.inter.clases.SesionClaseDAO;
import pe.edu.pucp.gigachadsys.model.clases.SesionClase;

import java.util.List;

public class SesionClaseBLImpl implements SesionClaseBL {

    SesionClaseDAO sesionClaseDAO = new SesionClaseDAOImpl();

    @Override
    public List<SesionClase> listAll() {
        return sesionClaseDAO.listAll();
    }

    @Override
    public SesionClase obtenerPorId(int idSesion) {
        return sesionClaseDAO.load(idSesion);
    }

    @Override
    public String registrar(SesionClase sesionClase) {
        try {
            sesionClaseDAO.save(sesionClase);
            return "Sesión de clase registrada correctamente.";
        } catch (Exception e) {
            return "Error al registrar sesión de clase: " + e.getMessage();
        }
    }

    @Override
    public String actualizar(int idSesion, SesionClase sesionClase) {
        try {
            sesionClase.setIdSesion(idSesion);
            sesionClaseDAO.update(sesionClase);
            return "Sesión de clase actualizada correctamente.";
        } catch (Exception e) {
            return "Error al actualizar sesión de clase: " + e.getMessage();
        }
    }

    @Override
    public String eliminar(int idSesion) {
        try {
            SesionClase sesionClase = new SesionClase(idSesion, null, null, null, 0, null, null, null);
            sesionClaseDAO.remove(sesionClase);
            return "Sesión de clase eliminada correctamente.";
        } catch (Exception e) {
            return "Error al eliminar sesión de clase: " + e.getMessage();
        }
    }
}
