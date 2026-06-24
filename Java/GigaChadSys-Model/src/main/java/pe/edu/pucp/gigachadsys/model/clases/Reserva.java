package pe.edu.pucp.gigachadsys.model.clases;

import java.sql.Timestamp;

public class Reserva {
    private int idReserva;
    private Timestamp fechaHoraReserva;
    private boolean asistio;
    private SesionClase sesionClase;
    private int idUsuario;
    private Boolean activo;

    public Reserva() {
        this.activo = true;
        this.asistio = false;
    }

    public Reserva(int idReserva, Timestamp fechaHoraReserva, boolean asistio, int idSesion, int idUsuario) {
        this.idReserva = idReserva;
        this.fechaHoraReserva = fechaHoraReserva;
        this.asistio = asistio;
        this.sesionClase = new SesionClase();
        this.sesionClase.setIdSesion(idSesion);
        this.idUsuario = idUsuario;
        this.activo = true;
    }

    public Reserva(int idReserva, Timestamp fechaHoraReserva, boolean asistio, SesionClase sesionClase) {
        this.idReserva = idReserva;
        this.fechaHoraReserva = fechaHoraReserva;
        this.asistio = asistio;
        this.sesionClase = sesionClase;
        this.activo = true;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public Timestamp getFechaHoraReserva() {
        return fechaHoraReserva;
    }

    public void setFechaHoraReserva(Timestamp fechaHoraReserva) {
        this.fechaHoraReserva = fechaHoraReserva;
    }

    public boolean isAsistio() {
        return asistio;
    }

    public void setAsistio(boolean asistio) {
        this.asistio = asistio;
    }

    public SesionClase getSesionClase() {
        return sesionClase;
    }

    public void setSesionClase(SesionClase sesionClase) {
        this.sesionClase = sesionClase;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Boolean getActivo() {
        return activo != null ? activo : false;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public void setActive(boolean activo) {
        this.activo = activo;
    }

    public boolean isActive() {
        return activo != null ? activo : false;
    }

    public void registrarAsistencia() {
        this.asistio = true;
    }
}