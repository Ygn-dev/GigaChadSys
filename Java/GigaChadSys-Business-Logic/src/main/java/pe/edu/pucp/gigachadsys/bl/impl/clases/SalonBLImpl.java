package pe.edu.pucp.gigachadsys.bl.impl.clases;

import pe.edu.pucp.gigachadsys.bl.inter.clases.SalonBL;
import pe.edu.pucp.gigachadsys.dao.impl.clases.SalonDAOImpl;
import pe.edu.pucp.gigachadsys.dao.inter.clases.SalonDAO;
import pe.edu.pucp.gigachadsys.model.clases.Salon;

import java.util.List;

public class SalonBLImpl implements SalonBL {

    SalonDAO salonDAO = new SalonDAOImpl();

    @Override
    public List<Salon> listAll() {
        return salonDAO.listAll();
    }

    @Override
    public Salon obtenerPorId(int idSalon) {
        return salonDAO.load(idSalon);
    }

    @Override
    public String registrar(Salon salon) {
        try {
            salonDAO.save(salon);
            return "Salón registrado correctamente.";
        } catch (Exception e) {
            return "Error al registrar salón: " + e.getMessage();
        }
    }

    @Override
    public String actualizar(int idSalon, Salon salon) {
        try {
            salon.setIdSalon(idSalon);
            salonDAO.update(salon);
            return "Salón actualizado correctamente.";
        } catch (Exception e) {
            return "Error al actualizar salón: " + e.getMessage();
        }
    }

    @Override
    public String eliminar(int idSalon) {
        try {
            Salon salon = new Salon();
            salon.setIdSalon(idSalon);
            salonDAO.remove(salon);
            return "Salón eliminado correctamente.";
        } catch (Exception e) {
            return "Error al eliminar salón: " + e.getMessage();
        }
    }
}
