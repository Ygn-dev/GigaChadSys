package pe.edu.pucp.gigachadsys.model;

public class MembresiaBasic extends Membresia{
    //Atributos
    private double costoMantenimientoMensual;

     //Constructores
    public MembresiaBasic() {
        super();
    }

    public MembresiaBasic(int idMembresia, String nombre, double costoMantenimientoMensual) {
        super(idMembresia, nombre);
        this.costoMantenimientoMensual = costoMantenimientoMensual;
    }
    
    //Setters y Getters
    public double getCostoMantenimientoMensual() {
        return costoMantenimientoMensual;
    }

    public void setCostoMantenimientoMensual(double costoMantenimientoMensual) {
        this.costoMantenimientoMensual = costoMantenimientoMensual;
    }

    //Metodos
    @Override
    public double calcularCostoTotal() {
        return 0;
    }
}
