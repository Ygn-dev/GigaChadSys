package pe.edu.pucp.gigachadsys.bl.impl.membresias;

import pe.edu.pucp.gigachadsys.bl.inter.membresias.MembresiaBasicBL;
import pe.edu.pucp.gigachadsys.dao.impl.membresias.MembresiaBasicDAOImpl;
import pe.edu.pucp.gigachadsys.dao.inter.membresias.MembresiaBasicDAO;
import pe.edu.pucp.gigachadsys.model.membresias.MembresiaBasic;

import java.util.List;

public class MembresiaBasicBLImpl implements MembresiaBasicBL {
    private MembresiaBasicDAO membresiaDAO = new MembresiaBasicDAOImpl();

    @Override
    public List<MembresiaBasic> listarMembresiasBasic() {
        return membresiaDAO.listAll();
    }

    @Override
    public MembresiaBasic obtenerPorId(int idMembresia) {
        return membresiaDAO.load(idMembresia);
    }

    @Override
    public String registrar(MembresiaBasic membresia) {
        try {
            membresiaDAO.save(membresia);
            return "Membresía Basic registrada correctamente.";
        } catch (Exception e) {
            return "Error al registrar membresía Basic: " + e.getMessage();
        }
    }

    @Override
    public String actualizar(int idMembresia, MembresiaBasic membresia) {
        try {
            membresia.setIdMembresia(idMembresia);
            membresiaDAO.update(membresia);
            return "Membresía Basic actualizada correctamente.";
        } catch (Exception e) {
            return "Error al actualizar membresía Basic: " + e.getMessage();
        }
    }

    @Override
    public String eliminar(int idMembresia) {
        try {
            MembresiaBasic membresia = new MembresiaBasic();
            membresia.setIdMembresia(idMembresia);
            membresia.setActiva(false);
            membresiaDAO.remove(membresia);
            return "Membresía Basic eliminada correctamente.";
        } catch (Exception e) {
            return "Error al eliminar membresía Basic: " + e.getMessage();
        }
    }
}
