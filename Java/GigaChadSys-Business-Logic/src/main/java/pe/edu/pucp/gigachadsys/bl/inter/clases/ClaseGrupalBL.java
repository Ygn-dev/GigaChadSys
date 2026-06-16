package pe.edu.pucp.gigachadsys.bl.inter.clases;

import pe.edu.pucp.gigachadsys.model.clases.ClaseGrupal;

import java.util.List;

public interface ClaseGrupalBL {
    List<ClaseGrupal> listAll();
    ClaseGrupal obtenerPorId(int idClase);
    String registrar(ClaseGrupal claseGrupal);
    String actualizar(int idClase, ClaseGrupal claseGrupal);
    String eliminar(int idClase);
}
