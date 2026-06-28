package pe.edu.pucp.gigachadsys.services;

import java.math.BigDecimal;

public class ReportePagoAdministradorDTO {

    private String usuario;
    private String rolUsuario;
    private String concepto;
    private String movimiento;
    private String metodo;
    private String fecha;
    private String estado;
    private BigDecimal monto;

    public ReportePagoAdministradorDTO() {
    }

    public ReportePagoAdministradorDTO(String usuario,
                                       String rolUsuario,
                                       String concepto,
                                       String movimiento,
                                       String metodo,
                                       String fecha,
                                       String estado,
                                       BigDecimal monto) {
        this.usuario = usuario;
        this.rolUsuario = rolUsuario;
        this.concepto = concepto;
        this.movimiento = movimiento;
        this.metodo = metodo;
        this.fecha = fecha;
        this.estado = estado;
        this.monto = monto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(String rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(String movimiento) {
        this.movimiento = movimiento;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
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

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
}