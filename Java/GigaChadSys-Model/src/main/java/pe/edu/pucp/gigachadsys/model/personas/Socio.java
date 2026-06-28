package pe.edu.pucp.gigachadsys.model.personas;

public class Socio extends Usuario {

    private boolean estadoMembresia;

    public Socio() {
        super();
    }

    /*
     * Constructor con orden normal:
     * apellidoPaterno primero, apellidoMaterno después.
     *
     * OJO:
     * Si Usuario tiene el constructor antiguo con orden:
     * (apellidoMaterno, apellidoPaterno),
     * aquí lo acomodamos al llamar al super.
     */
    public Socio(
            int idUsuario,
            String nombres,
            String apellidoPaterno,
            String apellidoMaterno,
            int edad,
            int dni,
            String email,
            int telefono,
            String contrasenia,
            String rol,
            boolean estadoMembresia
    ) {
        super(
                idUsuario,
                nombres,
                apellidoMaterno,
                apellidoPaterno,
                edad,
                dni,
                email,
                telefono,
                contrasenia,
                rol
        );

        this.estadoMembresia = estadoMembresia;
    }

    public boolean getEstadoMembresia() {
        return estadoMembresia;
    }

    public void setEstadoMembresia(boolean estadoMembresia) {
        this.estadoMembresia = estadoMembresia;
    }

    @Override
    public void mostrarDatos() {
    }

    public boolean verificarEstadoActivo() {
        return true;
    }
}