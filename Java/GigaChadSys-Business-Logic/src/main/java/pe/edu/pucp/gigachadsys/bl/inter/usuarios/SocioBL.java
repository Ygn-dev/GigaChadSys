package pe.edu.pucp.gigachadsys.bl.inter.usuarios;

import pe.edu.pucp.gigachadsys.model.personas.Socio;

import java.util.List;

public interface SocioBL {
    List<Socio> listarSocios();
    Socio obtenerPorId(int idUsuario);
    String registrar(Socio socio);
    String actualizar(int idUsuario, Socio socio);
    String eliminar(int idUsuario);
}
