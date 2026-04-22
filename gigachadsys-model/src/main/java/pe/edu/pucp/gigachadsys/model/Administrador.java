package pe.edu.pucp.gigachadsys.model;

public class Administrador extends Usuario{
    //Atributos
    private String sede;
    private double sueldo;

     //Constructor vacío
     public Administrador() {
        super();
    }

    //Constructor con parámetros
    public Administrador(int idUsuario, String nombres, String apellidoMaterno, String apellidoPaterno,
                         int edad, int dni, String email, int telefono, String contrasenia, String rol,
                         String sede, double sueldo) {

        super(idUsuario, nombres, apellidoMaterno, apellidoPaterno, edad, dni, email, telefono, contrasenia, rol);
        this.sede = sede;
        this.sueldo = sueldo;
    }


    //Setters y Getters
    public String getSede() {
        return sede;
    }
    
    public void setSede(String sede) {
        this.sede = sede;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    //Metodos
    @Override
    public void mostrarDatos() {

    }
}
