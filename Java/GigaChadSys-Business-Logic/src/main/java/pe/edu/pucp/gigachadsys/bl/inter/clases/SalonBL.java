package pe.edu.pucp.gigachadsys.bl.inter.clases;

import pe.edu.pucp.gigachadsys.model.clases.Salon;

import java.util.List;

public interface SalonBL {
    List<Salon> listAll();
    Salon obtenerPorId(int idSalon);
    String registrar(Salon salon);
    String actualizar(int idSalon, Salon salon);
    String eliminar(int idSalon);
}
