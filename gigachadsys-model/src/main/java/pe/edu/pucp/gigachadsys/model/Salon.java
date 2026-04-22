package pe.edu.pucp.gigachadsys.model;

public class Salon {
    // Atributos
    private String nobreSalon;
    private int aforoMaximo;

    public int getIdSalon() {
        return idSalon;
    }

    public void setIdSalon(int idSalon) {
        this.idSalon = idSalon;
    }

    private int idSalon;

    //Constructores
    public Salon() {
    }

    public Salon(String nobreSalon, int aforoMaximo) {
        this.nobreSalon = nobreSalon;
        this.aforoMaximo = aforoMaximo;
    }

    public Salon(int idSalon, String nombreSalon, int aforoMaximo) {
    }

    //Setters y Getters
    public String getNombreSalon() {
        return nobreSalon;
    }

    public void setNombreSalon(String nobreSalon) {
        this.nobreSalon = nobreSalon;
    }

    public int getAforoMaximo() {
        return aforoMaximo;
    }

    public void setAforoMaximo(int aforoMaximo) {
        this.aforoMaximo = aforoMaximo;
    }


    //Metodos
	public boolean verificarDisponibilidadAforo(int cantidadActual) {
        return true;
    }

    public void setActivo(boolean activo) {

    }
}
