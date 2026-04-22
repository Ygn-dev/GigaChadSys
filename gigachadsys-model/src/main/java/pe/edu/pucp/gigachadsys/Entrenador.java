import java.sql.Time;

public class Entrenador extends Usuario{
    //Atributos
    private String especialidad;
    private double sueldo;
    private Time tiempoTrabajado;

    //Constructor con parámetros
    public Entrenador(int idUsuario, String nombres, String apellidoMaterno, String apellidoPaterno,
                      int edad, int dni, String email, int telefono, String contrasenia, String rol,
                      String especialidad, double sueldo, Time tiempoTrabajado) {

        super(idUsuario, nombres, apellidoMaterno, apellidoPaterno, edad, dni, email, telefono, contrasenia, rol);

        this.especialidad = especialidad;
        this.sueldo = sueldo;
        this.tiempoTrabajado = tiempoTrabajado;
    }


    //Setters y Getters
    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public Time getTiempoTrabajado() {
        return tiempoTrabajado;
    }

    public void setTiempoTrabajado(Time tiempoTrabajado) {
        this.tiempoTrabajado = tiempoTrabajado;
    }

    //Metodos
    @Override
    public void mostrarDatos() {

    }
}
