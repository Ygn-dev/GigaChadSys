package pe.edu.pucp.gigachadsys.services;

import java.math.BigDecimal;

public class ReportePagoAdministradorDTO {

    private String concepto;
    private String tipo;
    private String metodo;
    private BigDecimal monto;
    private String fecha;
    private String estado;

    public ReportePagoAdministradorDTO() {
    }

    public ReportePagoAdministradorDTO(String concepto, String tipo, String metodo,
                                       BigDecimal monto, String fecha, String estado) {
        this.concepto = concepto;
        this.tipo = tipo;
        this.metodo = metodo;
        this.monto = monto;
        this.fecha = fecha;
        this.estado = estado;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}