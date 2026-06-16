package pe.edu.pucp.gigachadsys.bl.inter.membresias;

import pe.edu.pucp.gigachadsys.model.membresias.MembresiaBasic;

import java.util.List;

public interface MembresiaBasicBL {
    List<MembresiaBasic> listarMembresiasBasic();
    MembresiaBasic obtenerPorId(int idMembresia);
    String registrar(MembresiaBasic membresia);
    String actualizar(int idMembresia, MembresiaBasic membresia);
    String eliminar(int idMembresia);
}
