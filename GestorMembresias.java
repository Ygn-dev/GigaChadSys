import java.util.List;
import java.util.ArrayList;

public class GestorMembresias {
    //Atributos
    private List<Suscripcion> suscripciones;
    private List<MetodoPago> pagos;
    private List<Membresia> membresias;

    //Constructores
    public GestorMembresias() {
        this.suscripciones = new ArrayList<>();
        this.pagos = new ArrayList<>();
        this.membresias = new ArrayList<>();
    }

    public GestorMembresias(List<Suscripcion> suscripciones, List<MetodoPago> pagos, List<Membresia> membresias) {
        this.suscripciones = suscripciones;
        this.pagos = pagos;
        this.membresias = membresias;
    }
    
    //Metodos
    public Membresia consultarMembresia( int idMembresia){
        return null;
    }

    public void asignarSuscripcion(Socio socio, Membresia membresia){
        
    }

    public void procesarCobroMensual(Suscripcion suscripcion , MetodoPago metodo ) {

    }

    public boolean validarMembresiaActiva(Socio socio){
        return true;
    }

}
