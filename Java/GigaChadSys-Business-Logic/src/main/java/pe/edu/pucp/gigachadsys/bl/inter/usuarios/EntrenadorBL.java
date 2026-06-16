package pe.edu.pucp.gigachadsys.bl.inter.usuarios;

import pe.edu.pucp.gigachadsys.model.personas.Entrenador;

import java.util.List;

public interface EntrenadorBL {
    List<Entrenador> listarEntrenadores();
    Entrenador obtenerPorId(int idUsuario);
    String registrar(Entrenador entrenador);
    String actualizar(int idUsuario, Entrenador entrenador);
    String eliminar(int idUsuario);
}
