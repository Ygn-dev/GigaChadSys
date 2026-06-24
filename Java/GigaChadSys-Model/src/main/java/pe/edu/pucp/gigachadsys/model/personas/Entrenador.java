package pe.edu.pucp.gigachadsys.model.personas;

import java.sql.Time;
import jakarta.json.bind.annotation.JsonbTransient;

public class Entrenador extends Usuario {

    private String especialidad;
    private double sueldo;
    private Time tiempoTrabajado;

    public Entrenador(int idUsuario, String nombres, String apellidoMaterno, String apellidoPaterno,
                      int edad, int dni, String email, int telefono, String contrasenia, String rol,
                      String especialidad, double sueldo, Time tiempoTrabajado) {

        super(idUsuario, nombres, apellidoMaterno, apellidoPaterno, edad, dni, email, telefono, contrasenia, rol);

        this.especialidad = especialidad;
        this.sueldo = sueldo;
        this.tiempoTrabajado = tiempoTrabajado;
    }

    public Entrenador() {
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    @JsonbTransient
    public Time getTiempoTrabajado() {
        return tiempoTrabajado;
    }

    @JsonbTransient
    public void setTiempoTrabajado(Time tiempoTrabajado) {
        this.tiempoTrabajado = tiempoTrabajado;
    }

    /**
     * Campo visible para JSON.
     * C# recibirá y enviará este campo como tiempoTrabajadoTexto.
     */
    public String getTiempoTrabajadoTexto() {
        return tiempoTrabajado != null ? tiempoTrabajado.toString() : null;
    }

    /**
     * Permite que Java REST reciba desde C#:
     * "08:00:00", "04:00:00", "02:00:00"
     * y también textos antiguos por compatibilidad.
     */
    public void setTiempoTrabajadoTexto(String tiempoTrabajadoTexto) {
        if (tiempoTrabajadoTexto == null || tiempoTrabajadoTexto.isBlank()) {
            this.tiempoTrabajado = null;
            return;
        }

        String valor = tiempoTrabajadoTexto.trim();

        switch (valor) {
            case "Tiempo completo":
                valor = "08:00:00";
                break;
            case "Medio tiempo":
                valor = "04:00:00";
                break;
            case "Por horas":
                valor = "02:00:00";
                break;
        }

        try {
            this.tiempoTrabajado = Time.valueOf(valor);
        } catch (IllegalArgumentException ex) {
            this.tiempoTrabajado = null;
        }
    }

    @Override
    public void mostrarDatos() {
    }
}