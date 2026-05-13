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
}
