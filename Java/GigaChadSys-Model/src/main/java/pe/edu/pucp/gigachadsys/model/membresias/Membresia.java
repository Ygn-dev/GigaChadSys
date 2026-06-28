package pe.edu.pucp.gigachadsys.model.membresias;

public abstract class Membresia {

    private int idMembresia;
    private String nombre;
    private boolean activa = true;

    public Membresia() {
        this.activa = true;
    }

    public Membresia(int idMembresia, String nombre) {
        this.idMembresia = idMembresia;
        this.nombre = nombre;
        this.activa = true;
    }

    public int getIdMembresia() {
        return idMembresia;
    }

    public void setIdMembresia(int idMembresia) {
        this.idMembresia = idMembresia;
    }

    // Alias por si algún JSON usa membresia_ID
    public int getMembresia_ID() {
        return idMembresia;
    }

    public void setMembresia_ID(int membresia_ID) {
        this.idMembresia = membresia_ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Alias IMPORTANTÍSIMO para frontend / BD:
    // C# probablemente manda nombrePlan.
    public String getNombrePlan() {
        return nombre;
    }

    public void setNombrePlan(String nombrePlan) {
        this.nombre = nombrePlan;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    // Alias IMPORTANTÍSIMO:
    // C# probablemente manda activo.
    public boolean getActivo() {
        return activa;
    }

    public void setActivo(boolean activo) {
        this.activa = activo;
    }

    public abstract double calcularCostoTotal();
}