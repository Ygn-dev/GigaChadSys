import java.util.List;
import java.util.ArrayList;

public class GestorUsuario {
    //Atributos
    private List<Socio> socios;
    private List<Entrenador> entrenadores;
    private List<Administrador> administradores;

     //Constructores
    public GestorUsuario() {
		socios = new ArrayList<>();
		entrenadores = new ArrayList<>();
		administradores = new ArrayList<>();
    }

    public GestorUsuario(List<Socio> socios, List<Entrenador> entrenadores, List<Administrador> administradores) {
        this.socios = socios;
        this.entrenadores = entrenadores;
        this.administradores = administradores;
    }
    
    //Metodos
    public void registrarEntrenador(Entrenador entrenador) {
        
    }

    public void actualizarEntrenador(Entrenador entrenador) {
        
    }

    public void eliminarSocio(int idUsuario) {
        
    }

    public Entrenador consultarEntrenador(int idUsuario) {
        return null;
    }

    public void registrarAdministrativo(Administrador admi) {
        
    }

    public void actualizarAdministrativo(Administrador admi) {
        
    }

    public void eliminarAdministrativo(int idUsuario) {
        
    }

    public Administrador consultarAdministrativo(int idUsuario) {
        return null;
    }

    public List<Socio> buscarSocioAvanzado(String criterio) {
        return null;
    }

}
