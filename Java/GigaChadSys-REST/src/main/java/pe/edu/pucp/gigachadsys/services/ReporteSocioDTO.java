package pe.edu.pucp.gigachadsys.services;

import java.math.BigDecimal;

public class ReporteSocioDTO {

    private String nombreCompleto;
    private String dni;
    private String correo;
    private String telefono;
    private String membresia;
    private String estado;
    private BigDecimal montoPendiente;

    public ReporteSocioDTO() {
    }

    public ReporteSocioDTO(String nombreCompleto,
                           String dni,
                           String correo,
                           String telefono,
                           String membresia,
                           String estado,
                           BigDecimal montoPendiente) {
        this.nombreCompleto = nombreCompleto;
        this.dni = dni;
        this.correo = correo;
        this.telefono = telefono;
        this.membresia = membresia;
        this.estado = estado;
        this.montoPendiente = montoPendiente;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMembresia() {
        return membresia;
    }

    public void setMembresia(String membresia) {
        this.membresia = membresia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getMontoPendiente() {
        return montoPendiente;
    }

    public void setMontoPendiente(BigDecimal montoPendiente) {
        this.montoPendiente = montoPendiente;
    }
}
