package pe.edu.pucp.gigachadsys.bl.impl.usuarios;

import pe.edu.pucp.gigachadsys.bl.inter.usuarios.EntrenadorBL;
import pe.edu.pucp.gigachadsys.dao.impl.usuarios.EntrenadorDAOImpl;
import pe.edu.pucp.gigachadsys.dao.inter.usuarios.EntrenadorDAO;
import pe.edu.pucp.gigachadsys.model.personas.Entrenador;

import java.util.List;

public class EntrenadorBLImpl implements EntrenadorBL {
    private EntrenadorDAO entrenadorDAO = new EntrenadorDAOImpl();

    @Override
    public List<Entrenador> listarEntrenadores() {
        return entrenadorDAO.listAll();
    }

    @Override
    public Entrenador obtenerPorId(int idUsuario) {
        return entrenadorDAO.load(idUsuario);
    }

    @Override
    public String registrar(Entrenador entrenador) {
        try {
            entrenadorDAO.save(entrenador);
            return "Entrenador registrado correctamente.";
        } catch (Exception e) {
            return "Error al registrar entrenador: " + e.getMessage();
        }
    }

    @Override
    public String actualizar(int idUsuario, Entrenador entrenador) {
        try {
            entrenador.setIdUsuario(idUsuario);
            entrenadorDAO.update(entrenador);
            return "Entrenador actualizado correctamente.";
        } catch (Exception e) {
            return "Error al actualizar entrenador: " + e.getMessage();
        }
    }

    @Override
    public String eliminar(int idUsuario) {
        try {
            Entrenador entrenador = new Entrenador();
            entrenador.setIdUsuario(idUsuario);
            entrenador.setActivo(false);
            entrenadorDAO.remove(entrenador);
            return "Entrenador eliminado correctamente.";
        } catch (Exception e) {
            return "Error al eliminar entrenador: " + e.getMessage();
        }
    }
}
