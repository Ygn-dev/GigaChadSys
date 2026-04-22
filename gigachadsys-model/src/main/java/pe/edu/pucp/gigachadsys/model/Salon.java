package pe.edu.pucp.gigachadsys.model;

public class Salon {
    // Atributos
    private String nobreSalon;
    private int aforoMaximo;

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
    public String getNobreSalon() {
        return nobreSalon;
    }

    public void setNobreSalon(String nobreSalon) {
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

    public void setActive(boolean activo) {

    }
}
