package pe.edu.pucp.gigachadsys.model.clases;

public class ClaseGrupal {
	private int idClase;
	private String nombreDisciplina;
	private String descripcion;
	private int duracionMinutos;
	private String nivelClase;
	private Boolean activo;

	public ClaseGrupal() {
		this.activo = true;
	}

	public ClaseGrupal(int idClase, String nombreDisciplina, String descripcion, int duracionMinutos, String nivelClase) {
		this.idClase = idClase;
		this.nombreDisciplina = nombreDisciplina;
		this.descripcion = descripcion;
		this.duracionMinutos = duracionMinutos;
		this.nivelClase = nivelClase;
		this.activo = true;
	}

	public int getIdClase() {
		return idClase;
	}

	public void setIdClase(int idClase) {
		this.idClase = idClase;
	}

	public String getNombreDisciplina() {
		return nombreDisciplina;
	}

	public void setNombreDisciplina(String nombreDisciplina) {
		this.nombreDisciplina = nombreDisciplina;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getDuracionMinutos() {
		return duracionMinutos;
	}

	public void setDuracionMinutos(int duracionMinutos) {
		this.duracionMinutos = duracionMinutos;
	}

	public String getNivel() {
		return nivelClase;
	}

	public void setNivel(String nivelClase) {
		this.nivelClase = nivelClase;
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
}