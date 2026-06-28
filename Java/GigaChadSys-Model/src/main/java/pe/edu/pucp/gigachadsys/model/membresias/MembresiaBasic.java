package pe.edu.pucp.gigachadsys.model.membresias;

public class MembresiaBasic extends Membresia {

    private double costoMantenimientoMensual;

    public MembresiaBasic() {
        super();
    }

    public MembresiaBasic(int idMembresia, String nombre, double costoMantenimientoMensual) {
        super(idMembresia, nombre);
        this.costoMantenimientoMensual = costoMantenimientoMensual;
    }

    public double getCostoMantenimientoMensual() {
        return costoMantenimientoMensual;
    }

    public void setCostoMantenimientoMensual(double costoMantenimientoMensual) {
        this.costoMantenimientoMensual = costoMantenimientoMensual;
    }

    // Alias por si el frontend usa otro nombre
    public double getPrecioMensual() {
        return costoMantenimientoMensual;
    }

    public void setPrecioMensual(double precioMensual) {
        this.costoMantenimientoMensual = precioMensual;
    }

    public double getCostoMensual() {
        return costoMantenimientoMensual;
    }

    public void setCostoMensual(double costoMensual) {
        this.costoMantenimientoMensual = costoMensual;
    }

    @Override
    public double calcularCostoTotal() {
        return costoMantenimientoMensual;
    }
}