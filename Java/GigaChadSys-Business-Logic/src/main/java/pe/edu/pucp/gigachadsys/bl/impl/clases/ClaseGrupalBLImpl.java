package pe.edu.pucp.gigachadsys.bl.impl.clases;

import pe.edu.pucp.gigachadsys.bl.inter.clases.ClaseGrupalBL;
import pe.edu.pucp.gigachadsys.dao.impl.clases.ClaseGrupalDAOImpl;
import pe.edu.pucp.gigachadsys.dao.inter.clases.ClaseGrupalDAO;
import pe.edu.pucp.gigachadsys.model.clases.ClaseGrupal;

import java.util.List;

public class ClaseGrupalBLImpl implements ClaseGrupalBL {

    ClaseGrupalDAO claseDAO = new ClaseGrupalDAOImpl();

    @Override
    public List<ClaseGrupal> listAll() {
        return claseDAO.listAll();
    }

    @Override
    public ClaseGrupal obtenerPorId(int idClase) {
        return claseDAO.load(idClase);
    }

    @Override
    public String registrar(ClaseGrupal claseGrupal) {
        try {
            claseDAO.save(claseGrupal);
            return "Clase grupal registrada correctamente.";
        } catch (Exception e) {
            return "Error al registrar clase grupal: " + e.getMessage();
        }
    }

    @Override
    public String actualizar(int idClase, ClaseGrupal claseGrupal) {
        try {
            claseGrupal.setIdClase(idClase);
            claseDAO.update(claseGrupal);
            return "Clase grupal actualizada correctamente.";
        } catch (Exception e) {
            return "Error al actualizar clase grupal: " + e.getMessage();
        }
    }

    @Override
    public String eliminar(int idClase) {
        try {
            ClaseGrupal claseGrupal = new ClaseGrupal();
            claseGrupal.setIdClase(idClase);
            claseDAO.remove(claseGrupal);
            return "Clase grupal eliminada correctamente.";
        } catch (Exception e) {
            return "Error al eliminar clase grupal: " + e.getMessage();
        }
    }
}