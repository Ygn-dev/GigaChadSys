package pe.edu.pucp.gigachadsys.model;

import java.util.Date;

public  class Pago implements  IPrintable{
    //Atributos
    private int idPago;
    private Date fechaPago;
    private double monto;
    private String tipo;
    private MetodoPago metodoPago;

    //Constructores
    public Pago() {
    }

    public Pago(int idPago, Date fechaPago, double monto, String tipo, MetodoPago metodoPago) {
        this.idPago = idPago;
        this.fechaPago = fechaPago;
        this.monto = monto;
        this.tipo = tipo;
        this.metodoPago = metodoPago;
    }

    //Setters y Getters
    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }


    //Metodos
    @Override
    public void mostrarDatos() {

    }

    public void procesarPagos(){

    };

    public boolean validarPago(){
        return true;
    }

    public String generarComprobante(){

        return "Hola";
    }

    public void registrarCobro(){

    }

    public Pago consultarPago(){

        return null;
    }

}
