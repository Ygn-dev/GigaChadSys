package pe.edu.pucp.gigachadsys.model;

public class Salon {
    // Atributos
    private String nombreSalon;
    private int aforoMaximo;
    private int idSalon;
    private boolean estado;

    public int getIdSalon() {
        return idSalon;
    }

    public void setIdSalon(int idSalon) {
        this.idSalon = idSalon;
    }

    //Constructores
    public Salon() {}

    public Salon(String nombreSalon, int aforoMaximo) {
        this.nombreSalon = nombreSalon;
        this.aforoMaximo = aforoMaximo;
    }

    public Salon(int idSalon, String nombreSalon, int aforoMaximo) {
        this.idSalon = idSalon;
        this.nombreSalon = nombreSalon;
        this.aforoMaximo = aforoMaximo;
    }

    //Setters y Getters
    public String getNombreSalon() {
        return nombreSalon;
    }

    public void setNombreSalon(String nobreSalon) {
        this.nombreSalon = nobreSalon;
    }

    public int getAforoMaximo() {
        return aforoMaximo;
    }

    public void setAforoMaximo(int aforoMaximo) {
        this.aforoMaximo = aforoMaximo;
    }

    public void setActive(boolean estado) {
        this.estado = estado;
    }
    public boolean isActive() {
        return estado;
    }

    //Metodos
	public boolean verificarDisponibilidadAforo(int cantidadActual) {
        return true;
    }

}
