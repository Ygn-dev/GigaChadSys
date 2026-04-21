public class MembresiaBlack extends Membresia{
    //Atributos
    private int cantidadInvitadosPorMes;
    private double costoMantenimientoAnual;

     //Constructores
    public MembresiaBlack() {
        super();
    }

    public MembresiaBlack(int idMembresia, String nombre, int duracion, boolean activa,
                          int cantidadInvitadosPorMes, double costoMantenimientoAnual) {
        super(idMembresia, nombre, duracion, activa);
        this.cantidadInvitadosPorMes = cantidadInvitadosPorMes;
        this.costoMantenimientoAnual = costoMantenimientoAnual;
    }
    
    //Setters y Getters
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


    //Metodos
    @Override
    public double calcularCostoTotal() {
        return 0;
    }
}
