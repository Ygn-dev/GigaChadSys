import java.util.Date;

public class Suscripcion {
    // Atributos
    private boolean estadoMembresia;
    private Date fechaIngreso;
    private Date fechaFinMembresia;
    private Socio socio;
    private Membresia membresia;
    private Pago pago;

     //Constructores
    public Suscripcion() {
    }

    public Suscripcion(boolean estadoMembresia, Date fechaIngreso, Date fechaFinMembresia,
                       Socio socio, Membresia membresia, Pago pago) {
        this.estadoMembresia = estadoMembresia;
        this.fechaIngreso = fechaIngreso;
        this.fechaFinMembresia = fechaFinMembresia;
        this.socio = socio;
        this.membresia = membresia;
        this.pago = pago;
    }
    
    //Setters y Getters
    public boolean isEstadoMembresia() {
        return estadoMembresia;
    }

    public void setEstadoMembresia(boolean estadoMembresia) {
        this.estadoMembresia = estadoMembresia;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaFinMembresia() {
        return fechaFinMembresia;
    }

    public void setFechaFinMembresia(Date fechaFinMembresia) {
        this.fechaFinMembresia = fechaFinMembresia;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public boolean estaActivo(){
     return true;
    };


    //Metodos
    public boolean verificarVigenciaSuscripcion(){
        return true;
    }

    public void actualizarEstadoVencido(){

    }

}
