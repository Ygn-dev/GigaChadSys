import java.time.LocalDateTime;
import java.util.Date;

public class SesionClase {
    //Atributos
    private int idSesion;
    private Date fechaSesion;
    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;
    private int cuposDisponibles;
    private Salon salon;
    private Entrenador entrenador;
    private ClaseGrupal claseGrupal;

    //Constructores
    public SesionClase() {
    }

    public SesionClase(int idSesion, Date fechaSesion, LocalDateTime horaInicio,
                       LocalDateTime horaFin, int cuposDisponibles,
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

    public LocalDateTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalDateTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalDateTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalDateTime horaFin) {
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
