package pe.edu.pucp.gigachadsys.services;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.gigachadsys.bl.impl.suscripcion.SuscripcionBLImpl;
import pe.edu.pucp.gigachadsys.bl.inter.suscripcion.SuscripcionBL;
import pe.edu.pucp.gigachadsys.model.membresias.Suscripcion;

import java.util.List;
import java.util.Map;

@Path("SuscripcionRS")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SuscripcionRS {

    private final SuscripcionBL suscripcionBL;

    public SuscripcionRS() {
        this.suscripcionBL = new SuscripcionBLImpl();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarSuscripciones() {
        List<Suscripcion> suscripciones = suscripcionBL.listarSuscripciones();
        return Response.ok(suscripciones).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerSuscripcionPorId(@PathParam("id") int idSuscripcion) {
        Suscripcion suscripcion = suscripcionBL.obtenerPorId(idSuscripcion);

        if (suscripcion == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("mensaje", "Suscripción no encontrada."))
                    .build();
        }

        return Response.ok(suscripcion).build();
    }

    @POST
    public Response registrarSuscripcion(Suscripcion suscripcion) {
        String mensaje = suscripcionBL.registrar(suscripcion);

        return Response.status(Response.Status.CREATED)
                .entity(Map.of("mensaje", mensaje))
                .build();
    }

    @PUT
    @Path("{id}")
    public Response actualizarSuscripcion(@PathParam("id") int idSuscripcion,
                                          Suscripcion suscripcion) {
        String mensaje = suscripcionBL.actualizar(idSuscripcion, suscripcion);

        return Response.ok(Map.of("mensaje", mensaje)).build();
    }

    @DELETE
    @Path("{id}")
    public Response eliminarSuscripcion(@PathParam("id") int idSuscripcion) {
        String mensaje = suscripcionBL.eliminar(idSuscripcion);

        return Response.ok(Map.of("mensaje", mensaje)).build();
    }
}
