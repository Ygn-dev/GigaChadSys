package pe.edu.pucp.gigachadsys.model;
public class MetodoPago {
    //Atributos
    private int idMetodoPago;
    private String tipo;
    private String detalle;

    //Constructores
    public MetodoPago() {
    }

    public MetodoPago(int idMetodoPago, String tipo, String detalle) {
        this.idMetodoPago = idMetodoPago;
        this.tipo = tipo;
        this.detalle = detalle;
    }

    //Setters y Getters
    public int getIdMetodoPago() {
        return idMetodoPago;
    }

    public void setIdMetodoPago(int idMetodoPago) {
        this.idMetodoPago = idMetodoPago;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }


    //Metodos
    public boolean validarMetodo(){
        return true;
    }
}
