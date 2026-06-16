package pe.edu.pucp.gigachadsys.bl.inter.suscripcion;

import pe.edu.pucp.gigachadsys.model.membresias.Suscripcion;

import java.util.List;

public interface SuscripcionBL {
    List<Suscripcion> listarSuscripciones();
    Suscripcion obtenerPorId(int idSuscripcion);
    String registrar(Suscripcion suscripcion);
    String actualizar(int idSuscripcion, Suscripcion suscripcion);
    String eliminar(int idSuscripcion);
}
