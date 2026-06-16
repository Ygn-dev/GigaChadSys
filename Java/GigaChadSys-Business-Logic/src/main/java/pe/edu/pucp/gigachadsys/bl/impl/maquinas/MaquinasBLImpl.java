package pe.edu.pucp.gigachadsys.bl.impl.maquinas;

import pe.edu.pucp.gigachadsys.bl.inter.maquinas.MaquinasBL;
import pe.edu.pucp.gigachadsys.dao.impl.maquinas.MaquinaDAOImpl;
import pe.edu.pucp.gigachadsys.dao.inter.maquinas.MaquinaDAO;
import pe.edu.pucp.gigachadsys.model.clases.Maquina;

import java.util.List;

public class MaquinasBLImpl implements MaquinasBL {
    MaquinaDAO maquinaDAO = new MaquinaDAOImpl();

    @Override
    public List<Maquina> ListAll() {
        return maquinaDAO.listAll();
    }

    @Override
    public Maquina obtenerPorId(int idMaquina) {
        return maquinaDAO.load(idMaquina);
    }

    @Override
    public String registrar(Maquina maquina) {
        try {
            maquinaDAO.save(maquina);
            return "Máquina registrada correctamente.";
        } catch (Exception e) {
            return "Error al registrar máquina: " + e.getMessage();
        }
    }

    @Override
    public String actualizar(int idMaquina, Maquina maquina) {
        try {
            maquina.setIdMaquina(idMaquina);
            maquinaDAO.update(maquina);
            return "Máquina actualizada correctamente.";
        } catch (Exception e) {
            return "Error al actualizar máquina: " + e.getMessage();
        }
    }

    @Override
    public String eliminar(int idMaquina) {
        try {
            Maquina maquina = new Maquina();
            maquina.setIdMaquina(idMaquina);
            maquina.setActive(false);
            maquinaDAO.remove(maquina);
            return "Máquina eliminada correctamente.";
        } catch (Exception e) {
            return "Error al eliminar máquina: " + e.getMessage();
        }
    }
}
