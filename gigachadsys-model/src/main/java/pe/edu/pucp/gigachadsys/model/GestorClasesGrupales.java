package pe.edu.pucp.gigachadsys.model;

import java.util.List;
import java.util.ArrayList;

public class GestorClasesGrupales {
    //Atributos
    private List<ClaseGrupal> clasesGrupales;
    private List<Reserva> reservas;
    private List<SesionClase> sesionesClase;

    //Constructor vacío
    public GestorClasesGrupales() {
		clasesGrupales = new ArrayList<>();
		reservas = new ArrayList<>();
		sesionesClase = new ArrayList<>();
    }

    //Constructor con parámetros
    public GestorClasesGrupales(List<ClaseGrupal> clasesGrupales, List<Reserva> reservas, List<SesionClase> sesionesClase) {
        this.clasesGrupales = clasesGrupales;
        this.reservas = reservas;
        this.sesionesClase = sesionesClase;
    }

    //Metodos
    public void programarHorarioSesion(SesionClase sesion ) {

    }

    public void registrarReserva(Socio socio , SesionClase sesion ){

    }

    public void  cancelarReserva(Reserva reserva ){

    }

    public boolean restringirReservaSinMembresia(Socio socio){
        return true;
    }

}
