package pe.edu.pucp.gigachadsys.model.membresias;

import jakarta.json.bind.annotation.JsonbTransient;
import pe.edu.pucp.gigachadsys.model.pagos.Pago;
import pe.edu.pucp.gigachadsys.model.personas.Socio;

import java.util.Date;

public class Suscripcion {
    private int idSuscripcion;
    private String estadoMembresia;

    private Date fechaIngreso;
    private Date fechaFinMembresia;

    private String fechaIngresoTexto;
    private String fechaFinMembresiaTexto;

    private int idPago;
    private int idUsuario;
    private Integer idMembresiaBasic;
    private Integer idMembresiaBlack;

    private boolean activo;

    private Socio socio;
    private Membresia membresia;
    private Pago pago;

    public Suscripcion() {
        this.activo = true;
    }

    public Suscripcion(
            int idSuscripcion,
            String estadoMembresia,
            java.sql.Date fechaIngreso,
            java.sql.Date fechaFinMembresia,
            int idPago,
            int idUsuario,
            Integer idMembresiaBasic,
            Integer idMembresiaBlack
    ) {
        this.idSuscripcion = idSuscripcion;
        this.estadoMembresia = estadoMembresia;
        this.fechaIngreso = fechaIngreso;
        this.fechaFinMembresia = fechaFinMembresia;
        this.fechaIngresoTexto = fechaIngreso != null ? fechaIngreso.toString() : null;
        this.fechaFinMembresiaTexto = fechaFinMembresia != null ? fechaFinMembresia.toString() : null;
        this.idPago = idPago;
        this.idUsuario = idUsuario;
        this.idMembresiaBasic = idMembresiaBasic;
        this.idMembresiaBlack = idMembresiaBlack;
        this.activo = true;
    }

    public Suscripcion(
            String estadoMembresia,
            Date fechaIngreso,
            Date fechaFinMembresia,
            Socio socio,
            Membresia membresia,
            Pago pago
    ) {
        this.estadoMembresia = estadoMembresia;
        this.fechaIngreso = fechaIngreso;
        this.fechaFinMembresia = fechaFinMembresia;
        this.fechaIngresoTexto = fechaIngreso != null ? new java.sql.Date(fechaIngreso.getTime()).toString() : null;
        this.fechaFinMembresiaTexto = fechaFinMembresia != null ? new java.sql.Date(fechaFinMembresia.getTime()).toString() : null;
        this.socio = socio;
        this.membresia = membresia;
        this.pago = pago;
        this.activo = true;
    }

    public int getIdSuscripcion() {
        return idSuscripcion;
    }

    public void setIdSuscripcion(int idSuscripcion) {
        this.idSuscripcion = idSuscripcion;
    }

    public String getEstadoMembresia() {
        return estadoMembresia;
    }

    public void setEstadoMembresia(String estadoMembresia) {
        this.estadoMembresia = estadoMembresia;
    }

    @JsonbTransient
    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    @JsonbTransient
    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;

        if (fechaIngreso != null && (fechaIngresoTexto == null || fechaIngresoTexto.isBlank())) {
            fechaIngresoTexto = new java.sql.Date(fechaIngreso.getTime()).toString();
        }
    }

    @JsonbTransient
    public Date getFechaFinMembresia() {
        return fechaFinMembresia;
    }

    @JsonbTransient
    public void setFechaFinMembresia(Date fechaFinMembresia) {
        this.fechaFinMembresia = fechaFinMembresia;

        if (fechaFinMembresia != null && (fechaFinMembresiaTexto == null || fechaFinMembresiaTexto.isBlank())) {
            fechaFinMembresiaTexto = new java.sql.Date(fechaFinMembresia.getTime()).toString();
        }
    }

    public String getFechaIngresoTexto() {
        if (fechaIngresoTexto != null && !fechaIngresoTexto.isBlank()) {
            return fechaIngresoTexto;
        }

        if (fechaIngreso != null) {
            return new java.sql.Date(fechaIngreso.getTime()).toString();
        }

        return "";
    }

    public void setFechaIngresoTexto(String fechaIngresoTexto) {
        this.fechaIngresoTexto = fechaIngresoTexto;

        if (fechaIngresoTexto != null && !fechaIngresoTexto.isBlank()) {
            try {
                this.fechaIngreso = java.sql.Date.valueOf(fechaIngresoTexto);
            } catch (IllegalArgumentException ex) {
                this.fechaIngreso = null;
            }
        }
    }

    public String getFechaFinMembresiaTexto() {
        if (fechaFinMembresiaTexto != null && !fechaFinMembresiaTexto.isBlank()) {
            return fechaFinMembresiaTexto;
        }

        if (fechaFinMembresia != null) {
            return new java.sql.Date(fechaFinMembresia.getTime()).toString();
        }

        return "";
    }

    public void setFechaFinMembresiaTexto(String fechaFinMembresiaTexto) {
        this.fechaFinMembresiaTexto = fechaFinMembresiaTexto;

        if (fechaFinMembresiaTexto != null && !fechaFinMembresiaTexto.isBlank()) {
            try {
                this.fechaFinMembresia = java.sql.Date.valueOf(fechaFinMembresiaTexto);
            } catch (IllegalArgumentException ex) {
                this.fechaFinMembresia = null;
            }
        }
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdMembresiaBasic() {
        return idMembresiaBasic;
    }

    public void setIdMembresiaBasic(Integer idMembresiaBasic) {
        this.idMembresiaBasic = idMembresiaBasic;
    }

    public Integer getIdMembresiaBlack() {
        return idMembresiaBlack;
    }

    public void setIdMembresiaBlack(Integer idMembresiaBlack) {
        this.idMembresiaBlack = idMembresiaBlack;
    }

    public boolean isActivo() {
        return activo;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void setActive(boolean activo) {
        this.activo = activo;
    }

    @JsonbTransient
    public Socio getSocio() {
        return socio;
    }

    @JsonbTransient
    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    @JsonbTransient
    public Membresia getMembresia() {
        return membresia;
    }

    @JsonbTransient
    public void setMembresia(Membresia membresia) {
        this.membresia = membresia;
    }

    @JsonbTransient
    public Pago getPago() {
        return pago;
    }

    @JsonbTransient
    public void setPago(Pago pago) {
        this.pago = pago;
    }
}