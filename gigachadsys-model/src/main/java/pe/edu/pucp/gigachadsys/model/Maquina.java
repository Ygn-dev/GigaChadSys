package pe.edu.pucp.gigachadsys.model;

import java.util.Date;

public class Maquina {
    private int idMaquina;
    private String nombre;
    private String marca;
    private String estado;
    private Date fechaUltimoMantenimiento;

    // Constructor vacío
    public Maquina() {}

    // Constructor completo (útil para el DAO)
    public Maquina(int idMaquina, String nombre, String marca, String estado, Date fechaUltimoMantenimiento) {
        this.idMaquina = idMaquina;
        this.nombre = nombre;
        this.marca = marca;
        this.estado = estado;
        this.fechaUltimoMantenimiento = fechaUltimoMantenimiento;
    }

    // Getters y Setters
    public int getIdMaquina() { return idMaquina; }
    public void setIdMaquina(int idMaquina) { this.idMaquina = idMaquina; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Date getFechaUltimoMantenimiento() { return fechaUltimoMantenimiento; }
    public void setFechaUltimoMantenimiento(Date fechaUltimoMantenimiento) { this.fechaUltimoMantenimiento = fechaUltimoMantenimiento; }
}
