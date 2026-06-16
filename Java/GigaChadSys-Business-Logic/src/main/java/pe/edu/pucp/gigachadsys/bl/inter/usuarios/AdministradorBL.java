package pe.edu.pucp.gigachadsys.bl.inter.usuarios;

import pe.edu.pucp.gigachadsys.model.personas.Administrador;

import java.util.List;

public interface AdministradorBL {
    List<Administrador> listarAdministradores();
    Administrador obtenerPorId(int idUsuario);
    String registrar(Administrador administrador);
    String actualizar(int idUsuario, Administrador administrador);
    String eliminar(int idUsuario);
}
