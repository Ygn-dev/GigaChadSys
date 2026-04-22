package pe.edu.pucp.gigachadsys.model;

import java.util.Date;

public  class Pago /*implements  IPrintable*/{
    //Atributos
    private int idPago;
    private Date fechaPago;
    private double monto;
    private String tipo;
    private int metodoPago;
    private boolean estado;

    //Constructores
    public Pago(int idPago, Date fechaPago, double monto, String tipo, int metodoPago) {
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

    public int getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(int metodoPago) {
        this.metodoPago = metodoPago;
    }

    public void setActive(boolean estado) {
        this.estado = estado;
    }


    //Metodos

    public void mostrarDatos() {

    }

    public void procesarPagos(){

    };

    public boolean validarPago(){
        return true;
    }

    public String generarComprobante(){
        return "";
    }

    public void registrarCobro(){

    }

    public Pago consultarPago(){

        return null;
    }

}
