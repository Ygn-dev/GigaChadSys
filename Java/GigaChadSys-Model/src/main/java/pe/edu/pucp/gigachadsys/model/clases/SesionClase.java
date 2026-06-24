package pe.edu.pucp.gigachadsys.model.clases;

import pe.edu.pucp.gigachadsys.model.personas.Entrenador;

import java.sql.Timestamp;
import java.util.Date;

public class SesionClase {
    private int idSesion;
    private Date fechaSesion;
    private String fechaSesionTexto;

    private Timestamp horaInicio;
    private Timestamp horaFin;
    private int cuposDisponibles;
    private Boolean activo;

    private Salon salon;
    private Entrenador entrenador;
    private ClaseGrupal claseGrupal;

    public SesionClase() {
        this.activo = true;
    }

    public SesionClase(int idSesion, java.sql.Date fechaSesion, Timestamp horaInicio, Timestamp horaFin,
                       int cuposDisponibles, int idSalon, int idEntrenador, int idClase) {
        this.idSesion = idSesion;
        this.fechaSesion = fechaSesion;
        this.fechaSesionTexto = fechaSesion != null ? fechaSesion.toString() : null;

        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.cuposDisponibles = cuposDisponibles;
        this.activo = true;

        this.salon = new Salon();
        this.salon.setIdSalon(idSalon);

        this.entrenador = new Entrenador();
        this.entrenador.setIdUsuario(idEntrenador);

        this.claseGrupal = new ClaseGrupal();
        this.claseGrupal.setIdClase(idClase);
    }

    public SesionClase(int idSesion, Date fechaSesion, Timestamp horaInicio,
                       Timestamp horaFin, int cuposDisponibles,
                       Salon salon, Entrenador entrenador, ClaseGrupal claseGrupal) {
        this.idSesion = idSesion;
        this.fechaSesion = fechaSesion;
        this.fechaSesionTexto = fechaSesion != null
                ? new java.sql.Date(fechaSesion.getTime()).toString()
                : null;

        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.cuposDisponibles = cuposDisponibles;
        this.salon = salon;
        this.entrenador = entrenador;
        this.claseGrupal = claseGrupal;
        this.activo = true;
    }

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

        if (fechaSesion != null && (this.fechaSesionTexto == null || this.fechaSesionTexto.isBlank())) {
            this.fechaSesionTexto = new java.sql.Date(fechaSesion.getTime()).toString();
        }
    }

    public String getFechaSesionTexto() {
        if (fechaSesionTexto != null && !fechaSesionTexto.isBlank()) {
            return fechaSesionTexto;
        }

        if (fechaSesion != null) {
            return new java.sql.Date(fechaSesion.getTime()).toString();
        }

        return null;
    }

    public void setFechaSesionTexto(String fechaSesionTexto) {
        this.fechaSesionTexto = fechaSesionTexto;

        if (fechaSesionTexto != null && !fechaSesionTexto.isBlank()) {
            try {
                this.fechaSesion = java.sql.Date.valueOf(fechaSesionTexto);
            } catch (IllegalArgumentException ex) {
                // Si viene mal, se deja que fechaSesion normal intente resolver.
            }
        }
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

    public void aumentarCupoDisponible() {
        this.cuposDisponibles++;
    }

    public void reducirCupoDisponible() {
        if (this.cuposDisponibles > 0) {
            this.cuposDisponibles--;
        }
    }
}