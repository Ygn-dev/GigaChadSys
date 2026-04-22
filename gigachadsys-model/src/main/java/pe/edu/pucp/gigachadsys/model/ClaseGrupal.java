package pe.edu.pucp.gigachadsys.model;

public class ClaseGrupal {
	//Atributos
    private int idClase;
    private String nombreDisciplina;
    private String descripcion;
    private  int duracionMinutos;
    private String nivelClase;

	//Constructor vacío
    public ClaseGrupal() {
    }

    //Constructor con parámetros
    public ClaseGrupal(int idClase, String nombreDisciplina, String descripcion, int duracionMinutos, String nivelClase) {
        this.idClase = idClase;
        this.nombreDisciplina = nombreDisciplina;
        this.descripcion = descripcion;
        this.duracionMinutos = duracionMinutos;
        this.nivelClase = nivelClase;
    }

	//Setters y Getters
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

	public String getNivelClase() {
		return nivelClase;
	}

	public void setNivelClase(String nivelClase) {
		this.nivelClase = nivelClase;
	}
	

	//Metodos
}
