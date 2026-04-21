public  abstract class Usuario implements  IPrintable{
	//Atributos
    private int idUsuario;
    private String nombres;
    private String apellidoMaterno;
    private String apellidoPaterno;
    private int edad;
    private int dni;
    private String email;
    private int telefono;
    private String contrasenia;
    private String rol;

    //Constructores
    public Usuario() {
    }

    public Usuario(int idUsuario, String nombres, String apellidoMaterno, String apellidoPaterno,
                   int edad, int dni, String email, int telefono, String contrasenia, String rol) {
        this.idUsuario = idUsuario;
        this.nombres = nombres;
        this.apellidoMaterno = apellidoMaterno;
        this.apellidoPaterno = apellidoPaterno;
        this.edad = edad;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
        this.contrasenia = contrasenia;
        this.rol = rol;
    }

	//Setters y Getters
	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	//Metodos
    public abstract void mostrarDatos();
	public boolean iniciarSesion(String correo, String password){
		return true;
	}
	public String obtenerRolUsuario(){
		return "";
	}
}
