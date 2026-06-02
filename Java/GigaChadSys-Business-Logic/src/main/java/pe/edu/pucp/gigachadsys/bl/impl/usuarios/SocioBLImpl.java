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
}
