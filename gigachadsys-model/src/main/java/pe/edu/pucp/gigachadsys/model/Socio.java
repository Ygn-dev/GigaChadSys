package pe.edu.pucp.gigachadsys.model;

public class Socio extends  Usuario{
    //Atributos
    private boolean estadoMembresia;

    //Constructores
    public Socio() {
        super();
    }

    public Socio(int idUsuario, String nombres, String apellidoMaterno, String apellidoPaterno,
                 int edad, int dni, String email, int telefono, String contrasenia, String rol,
                 boolean estadoMembresia) {

        super(idUsuario, nombres, apellidoMaterno, apellidoPaterno, edad, dni, email, telefono, contrasenia, rol);
        this.estadoMembresia = estadoMembresia;
    }

    //Setters y Getters
    public boolean isEstadoMembresia() {
        return estadoMembresia;
    }

    public void setEstadoMembresia(boolean estadoMembresia) {
        this.estadoMembresia = estadoMembresia;
    }
	

    //Metodos
    @Override
    public void mostrarDatos() {
		
    }
	
	public boolean verificarEstadoActivo(){
		return true;
	}
	
	
}
