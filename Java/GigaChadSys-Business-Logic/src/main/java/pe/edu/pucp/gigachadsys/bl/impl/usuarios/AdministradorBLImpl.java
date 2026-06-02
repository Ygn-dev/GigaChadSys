package pe.edu.pucp.gigachadsys.bl.impl.usuarios;

import pe.edu.pucp.gigachadsys.bl.inter.usuarios.AdministradorBL;
import pe.edu.pucp.gigachadsys.dao.impl.usuarios.AdministradorDAOImpl;
import pe.edu.pucp.gigachadsys.dao.inter.usuarios.AdministradorDAO;
import pe.edu.pucp.gigachadsys.model.personas.Administrador;

import java.util.List;

public class AdministradorBLImpl implements AdministradorBL {

    private AdministradorDAO administradorDAO = new AdministradorDAOImpl();

    @Override
    public List<Administrador> listarAdministradores() {
        return administradorDAO.listAll();
    }
}
