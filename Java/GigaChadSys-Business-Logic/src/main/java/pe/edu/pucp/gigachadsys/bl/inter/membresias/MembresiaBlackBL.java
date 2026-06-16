package pe.edu.pucp.gigachadsys.bl.inter.membresias;

import pe.edu.pucp.gigachadsys.model.membresias.MembresiaBlack;

import java.util.List;

public interface MembresiaBlackBL {
    List<MembresiaBlack> listarMembresiasBlack();
    MembresiaBlack obtenerPorId(int idMembresia);
    String registrar(MembresiaBlack membresia);
    String actualizar(int idMembresia, MembresiaBlack membresia);
    String eliminar(int idMembresia);
}
