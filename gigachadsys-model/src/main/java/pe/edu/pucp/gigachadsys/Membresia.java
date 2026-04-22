public  abstract class Membresia {
    //Atributos
    private int idMembresia;
    private String nombre;
    private int duracion;
    private boolean activa;

    //Constructores
    public Membresia() {
    }

    public Membresia(int idMembresia, String nombre, int duracion, boolean activa) {
        this.idMembresia = idMembresia;
        this.nombre = nombre;
        this.duracion = duracion;
        this.activa = activa;
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

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
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
