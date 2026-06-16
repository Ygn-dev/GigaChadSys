package pe.edu.pucp.gigachadsys.bl.inter.clases;

import pe.edu.pucp.gigachadsys.model.clases.SesionClase;

import java.util.List;

public interface SesionClaseBL {
    List<SesionClase> listAll();
    SesionClase obtenerPorId(int idSesion);
    String registrar(SesionClase sesionClase);
    String actualizar(int idSesion, SesionClase sesionClase);
    String eliminar(int idSesion);
}
