package pe.edu.pucp.gigachadsys.model.membresias;

public class MembresiaBlack extends Membresia {

    private int cantidadInvitadosPorMes;
    private double costoMantenimientoAnual;

    public MembresiaBlack() {
        super();
    }

    public MembresiaBlack(
            int idMembresia,
            String nombre,
            double costoMantenimientoAnual,
            int cantidadInvitadosPorMes
    ) {
        super(idMembresia, nombre);
        this.cantidadInvitadosPorMes = cantidadInvitadosPorMes;
        this.costoMantenimientoAnual = costoMantenimientoAnual;
    }

    public int getCantidadInvitadosPorMes() {
        return cantidadInvitadosPorMes;
    }

    public void setCantidadInvitadosPorMes(int cantidadInvitadosPorMes) {
        this.cantidadInvitadosPorMes = cantidadInvitadosPorMes;
    }

    public double getCostoMantenimientoAnual() {
        return costoMantenimientoAnual;
    }

    public void setCostoMantenimientoAnual(double costoMantenimientoAnual) {
        this.costoMantenimientoAnual = costoMantenimientoAnual;
    }

    // Alias clave:
    // Si el frontend manda precio mensual para Black,
    // Java lo convierte a anual porque la BD guarda costoMantenimientoAnual.
    public double getCostoMantenimientoMensual() {
        return costoMantenimientoAnual / 12.0;
    }

    public void setCostoMantenimientoMensual(double costoMantenimientoMensual) {
        this.costoMantenimientoAnual = costoMantenimientoMensual * 12.0;
    }

    public double getPrecioMensual() {
        return costoMantenimientoAnual / 12.0;
    }

    public void setPrecioMensual(double precioMensual) {
        this.costoMantenimientoAnual = precioMensual * 12.0;
    }

    public double getCostoMensual() {
        return costoMantenimientoAnual / 12.0;
    }

    public void setCostoMensual(double costoMensual) {
        this.costoMantenimientoAnual = costoMensual * 12.0;
    }

    @Override
    public double calcularCostoTotal() {
        return costoMantenimientoAnual;
    }
}