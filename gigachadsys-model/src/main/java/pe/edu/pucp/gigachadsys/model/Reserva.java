package pe.edu.pucp.gigachadsys.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Reserva {
    //Atributos
    private int idReserva;
    private LocalDateTime  fechaHoraReserva;
    private boolean asistio;
    private SesionClase sesionClase;

    //Constructores
    public Reserva(int idReserva, Timestamp fechaHoraReserva, boolean asistio, int idSesion, int idUsuario) {
    }

    public Reserva(int idReserva, LocalDateTime fechaHoraReserva, boolean asistio, SesionClase sesionClase) {
        this.idReserva = idReserva;
        this.fechaHoraReserva = fechaHoraReserva;
        this.asistio = asistio;
        this.sesionClase = sesionClase;
    }

    //Setters y Getters
    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public Timestamp getFechaHoraReserva() {
        return fechaHoraReserva;
    }

    public void setFechaHoraReserva(LocalDateTime fechaHoraReserva) {
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


    //Metodos
    public  void registrarAsistencia() {

    }
}
