package pe.edu.pucp.gigachadsys.model.membresias;

import pe.edu.pucp.gigachadsys.model.pagos.Pago;
import pe.edu.pucp.gigachadsys.model.personas.Socio;

import java.util.Date;

public class Suscripcion {
    // Atributos
    private int idSuscripcion;
    private String estadoMembresia;
    private Date fechaIngreso;
    private Date fechaFinMembresia;
    
    // Foreign keys
    private int idPago;
    private int idUsuario;
    private Integer idMembresiaBasic;
    private Integer idMembresiaBlack;

    private boolean activo;
    
    // Objetos relacionados (opcional para joins)
    private Socio socio;
    private Membresia membresia;
    private Pago pago;

    // Constructores
    public Suscripcion() {
    }

    public Suscripcion(int idSuscripcion, String estadoMembresia, java.sql.Date fechaIngreso, java.sql.Date fechaFinMembresia, int idPago, int idUsuario, Integer idMembresiaBasic, Integer idMembresiaBlack) {
        this.idSuscripcion = idSuscripcion;
        this.estadoMembresia = estadoMembresia;
        this.fechaIngreso = fechaIngreso;
        this.fechaFinMembresia = fechaFinMembresia;
        this.idPago = idPago;
        this.idUsuario = idUsuario;
        this.idMembresiaBasic = idMembresiaBasic;
        this.idMembresiaBlack = idMembresiaBlack;
        this.activo = true;
    }

    public Suscripcion(String estadoMembresia, Date fechaIngreso, Date fechaFinMembresia,
                       Socio socio, Membresia membresia, Pago pago) {
        this.estadoMembresia = estadoMembresia;
        this.fechaIngreso = fechaIngreso;
        this.fechaFinMembresia = fechaFinMembresia;
        this.socio = socio;
        this.membresia = membresia;
        this.pago = pago;
        this.activo = true;
    }

    // Setters y Getters
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

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaFinMembresia() {
        return fechaFinMembresia;
    }

    public void setFechaFinMembresia(Date fechaFinMembresia) {
        this.fechaFinMembresia = fechaFinMembresia;
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

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    public Membresia getMembresia() {
        return membresia;
    }

    public void setMembresia(Membresia membresia) {
        this.membresia = membresia;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActive(boolean activo) {
        this.activo = activo;
    }
}
