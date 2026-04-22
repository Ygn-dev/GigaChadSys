package pe.edu.pucp.gigachadsys.model;

public  abstract class Membresia {
    //Atributos
    private int idMembresia;
    private String nombre;
    private boolean activa;

    //Constructores
    public Membresia() {
    }

    public Membresia(int idMembresia, String nombre) {
        this.idMembresia = idMembresia;
        this.nombre = nombre;
        this.activa = true;
    }
    
    //Setters y Getters
    public int getIdMembresia() {
        return idMembresia;
    }

    public void setIdMembresia(int idMembresia) {
        this.idMembresia = idMembresia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    //Metodos
    public abstract double calcularCostoTotal();
}
