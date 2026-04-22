package pe.edu.pucp.gigachadsys.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

public class SesionClase {
    //Atributos
    private int idSesion;
    private Date fechaSesion;
    private Timestamp horaInicio;
    private Timestamp horaFin;
    private int cuposDisponibles;
    private Salon salon;
    private Entrenador entrenador;
    private ClaseGrupal claseGrupal;

    //Constructores
    public SesionClase(int idSesion, java.sql.Date fechaSesion, Timestamp horaInicio, Timestamp horaFin, int cuposDisponibles, int idSalon, int idEntrenador, int idClase) {
    }

    public SesionClase(int idSesion, Date fechaSesion, Timestamp horaInicio,
                       Timestamp horaFin, int cuposDisponibles,
                       Salon salon, Entrenador entrenador, ClaseGrupal claseGrupal) {
        this.idSesion = idSesion;
        this.fechaSesion = fechaSesion;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.cuposDisponibles = cuposDisponibles;
        this.salon = salon;
        this.entrenador = entrenador;
        this.claseGrupal = claseGrupal;
    }
    
    //Setters y Getters
    public int getIdSesion() {
    return idSesion;
    }

    public void setIdSesion(int idSesion) {
        this.idSesion = idSesion;
    }

    public Date getFechaSesion() {
        return fechaSesion;
    }

    public void setFechaSesion(Date fechaSesion) {
        this.fechaSesion = fechaSesion;
    }

    public Timestamp getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Timestamp horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Timestamp getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Timestamp horaFin) {
        this.horaFin = horaFin;
    }

    public int getCuposDisponibles() {
        return cuposDisponibles;
    }

    public void setCuposDisponibles(int cuposDisponibles) {
        this.cuposDisponibles = cuposDisponibles;
    }

    public Salon getSalon() {
        return salon;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }

    public ClaseGrupal getClaseGrupal() {
        return claseGrupal;
    }

    public void setClaseGrupal(ClaseGrupal claseGrupal) {
        this.claseGrupal = claseGrupal;
    }


    //Metodos
	public void aumentarCupoDisponible(){

    }
	public void reducirCupoDisponible(){

    }
}
