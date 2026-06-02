package pe.edu.pucp.gigachadsys.bl.impl.maquinas;

import pe.edu.pucp.gigachadsys.bl.inter.maquinas.MaquinasBL;
import pe.edu.pucp.gigachadsys.dao.impl.maquinas.MaquinaDAOImpl;
import pe.edu.pucp.gigachadsys.dao.inter.maquinas.MaquinaDAO;
import pe.edu.pucp.gigachadsys.model.clases.Maquina;

import java.util.List;

public class MaquinasBLImpl implements MaquinasBL {
    MaquinaDAO maquina = new MaquinaDAOImpl();
    @Override
    public List<Maquina> ListAll() {
        return maquina.listAll();
    }
}
