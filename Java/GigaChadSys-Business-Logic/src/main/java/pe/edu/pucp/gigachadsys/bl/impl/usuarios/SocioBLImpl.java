package pe.edu.pucp.gigachadsys.bl.impl.usuarios;

import pe.edu.pucp.gigachadsys.bl.inter.usuarios.SocioBL;
import pe.edu.pucp.gigachadsys.dao.impl.usuarios.SocioDAOImpl;
import pe.edu.pucp.gigachadsys.dao.inter.usuarios.SocioDAO;
import pe.edu.pucp.gigachadsys.model.personas.Socio;

import java.util.List;

public class SocioBLImpl implements SocioBL {
    private SocioDAO socioDAO = new SocioDAOImpl();

    @Override
    public List<Socio> listarSocios() {
        return socioDAO.listAll();
    }

    @Override
    public Socio obtenerPorId(int idUsuario) {
        return socioDAO.load(idUsuario);
    }

    @Override
    public String registrar(Socio socio) {
        try {
            socioDAO.save(socio);
            return "Socio registrado correctamente.";
        } catch (Exception e) {
            return "Error al registrar socio: " + e.getMessage();
        }
    }

    @Override
    public String actualizar(int idUsuario, Socio socio) {
        try {
            socio.setIdUsuario(idUsuario);
            socioDAO.update(socio);
            return "Socio actualizado correctamente.";
        } catch (Exception e) {
            return "Error al actualizar socio: " + e.getMessage();
        }
    }

    @Override
    public String eliminar(int idUsuario) {
        try {
            Socio socio = new Socio();
            socio.setIdUsuario(idUsuario);
            socio.setActivo(false);
            socioDAO.remove(socio);
            return "Socio eliminado correctamente.";
        } catch (Exception e) {
            return "Error al eliminar socio: " + e.getMessage();
        }
    }
}
