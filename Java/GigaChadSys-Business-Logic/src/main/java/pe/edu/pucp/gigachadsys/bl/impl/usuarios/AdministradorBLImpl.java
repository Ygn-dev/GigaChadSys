package pe.edu.pucp.gigachadsys.bl.impl.usuarios;

import pe.edu.pucp.gigachadsys.bl.inter.usuarios.AdministradorBL;
import pe.edu.pucp.gigachadsys.dao.impl.usuarios.AdministradorDAOImpl;
import pe.edu.pucp.gigachadsys.dao.inter.usuarios.AdministradorDAO;
import pe.edu.pucp.gigachadsys.model.personas.Administrador;

import java.util.List;

public class AdministradorBLImpl implements AdministradorBL {
    private AdministradorDAO adminDAO = new AdministradorDAOImpl();

    @Override
    public List<Administrador> listarAdministradores() {
        return adminDAO.listAll();
    }

    @Override
    public Administrador obtenerPorId(int idUsuario) {
        return adminDAO.load(idUsuario);
    }

    @Override
    public String registrar(Administrador administrador) {
        try {
            adminDAO.save(administrador);
            return "Administrador registrado correctamente.";
        } catch (Exception e) {
            return "Error al registrar administrador: " + e.getMessage();
        }
    }

    @Override
    public String actualizar(int idUsuario, Administrador administrador) {
        try {
            administrador.setIdUsuario(idUsuario);
            adminDAO.update(administrador);
            return "Administrador actualizado correctamente.";
        } catch (Exception e) {
            return "Error al actualizar administrador: " + e.getMessage();
        }
    }

    @Override
    public String eliminar(int idUsuario) {
        try {
            Administrador administrador = new Administrador();
            administrador.setIdUsuario(idUsuario);
            administrador.setActivo(false);
            adminDAO.remove(administrador);
            return "Administrador eliminado correctamente.";
        } catch (Exception e) {
            return "Error al eliminar administrador: " + e.getMessage();
        }
    }
}
