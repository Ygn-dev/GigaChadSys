package pe.edu.pucp.gigachadsys.bl.inter.maquinas;

import pe.edu.pucp.gigachadsys.model.clases.Maquina;

import java.util.List;

public interface MaquinasBL {
    List<Maquina> ListAll();
    Maquina obtenerPorId(int idMaquina);
    String registrar(Maquina maquina);
    String actualizar(int idMaquina, Maquina maquina);
    String eliminar(int idMaquina);
}
