package pe.edu.pucp.gigachadsys.services;

import java.math.BigDecimal;

public class ReporteEntrenadorDTO {

    private String nombreCompleto;
    private String dni;
    private String correo;
    private String telefono;
    private String especialidad;
    private String estado;
    private BigDecimal sueldoMensual;
    private String estadoPago;
    private Integer sesionesAsignadas;

    public ReporteEntrenadorDTO() {
    }

    public ReporteEntrenadorDTO(String nombreCompleto,
                                String dni,
                                String correo,
                                String telefono,
                                String especialidad,
                                String estado,
                                BigDecimal sueldoMensual,
                                String estadoPago,
                                Integer sesionesAsignadas) {
        this.nombreCompleto = nombreCompleto;
        this.dni = dni;
        this.correo = correo;
        this.telefono = telefono;
        this.especialidad = especialidad;
        this.estado = estado;
        this.sueldoMensual = sueldoMensual;
        this.estadoPago = estadoPago;
        this.sesionesAsignadas = sesionesAsignadas;
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

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getSueldoMensual() {
        return sueldoMensual;
    }

    public void setSueldoMensual(BigDecimal sueldoMensual) {
        this.sueldoMensual = sueldoMensual;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public Integer getSesionesAsignadas() {
        return sesionesAsignadas;
    }

    public void setSesionesAsignadas(Integer sesionesAsignadas) {
        this.sesionesAsignadas = sesionesAsignadas;
    }
}