package pe.edu.pucp.gigachadsys.services;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.gigachadsys.bl.impl.clases.ReservaBLImpl;
import pe.edu.pucp.gigachadsys.bl.inter.clases.ReservaBL;
import pe.edu.pucp.gigachadsys.model.clases.Reserva;

import java.util.List;
import java.util.Map;

@Path("ReservaRS")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservaRS {

    private final ReservaBL reservaBL;

    public ReservaRS() {
        this.reservaBL = new ReservaBLImpl();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarReservas() {
        List<Reserva> reservas = reservaBL.listAll();
        return Response.ok(reservas).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerReservaPorId(@PathParam("id") int idReserva) {
        Reserva reserva = reservaBL.obtenerPorId(idReserva);

        if (reserva == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("mensaje", "Reserva no encontrada."))
                    .build();
        }

        return Response.ok(reserva).build();
    }

    @POST
    public Response registrarReserva(Reserva reserva) {
        String mensaje = reservaBL.registrar(reserva);

        return Response.status(Response.Status.CREATED)
                .entity(Map.of("mensaje", mensaje))
                .build();
    }

    @PUT
    @Path("{id}")
    public Response actualizarReserva(@PathParam("id") int idReserva,
                                       Reserva reserva) {
        String mensaje = reservaBL.actualizar(idReserva, reserva);

        return Response.ok(Map.of("mensaje", mensaje)).build();
    }

    @DELETE
    @Path("{id}")
    public Response eliminarReserva(@PathParam("id") int idReserva) {
        String mensaje = reservaBL.eliminar(idReserva);

        return Response.ok(Map.of("mensaje", mensaje)).build();
    }
}
