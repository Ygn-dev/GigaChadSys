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

    @Override
    public MembresiaBlack obtenerPorId(int idMembresia) {
        return membresiaDAO.load(idMembresia);
    }

    @Override
    public String registrar(MembresiaBlack membresia) {
        try {
            membresiaDAO.save(membresia);
            return "Membresía Black registrada correctamente.";
        } catch (Exception e) {
            return "Error al registrar membresía Black: " + e.getMessage();
        }
    }

    @Override
    public String actualizar(int idMembresia, MembresiaBlack membresia) {
        try {
            membresia.setIdMembresia(idMembresia);
            membresiaDAO.update(membresia);
            return "Membresía Black actualizada correctamente.";
        } catch (Exception e) {
            return "Error al actualizar membresía Black: " + e.getMessage();
        }
    }

    @Override
    public String eliminar(int idMembresia) {
        try {
            MembresiaBlack membresia = new MembresiaBlack();
            membresia.setIdMembresia(idMembresia);
            membresia.setActiva(false);
            membresiaDAO.remove(membresia);
            return "Membresía Black eliminada correctamente.";
        } catch (Exception e) {
            return "Error al eliminar membresía Black: " + e.getMessage();
        }
    }
}
