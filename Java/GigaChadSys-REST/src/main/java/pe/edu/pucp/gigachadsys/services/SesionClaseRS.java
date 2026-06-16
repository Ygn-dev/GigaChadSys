package pe.edu.pucp.gigachadsys.services;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.gigachadsys.bl.impl.clases.SesionClaseBLImpl;
import pe.edu.pucp.gigachadsys.bl.inter.clases.SesionClaseBL;
import pe.edu.pucp.gigachadsys.model.clases.SesionClase;

import java.util.List;
import java.util.Map;

@Path("SesionClaseRS")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SesionClaseRS {

    private final SesionClaseBL sesionClaseBL;

    public SesionClaseRS() {
        this.sesionClaseBL = new SesionClaseBLImpl();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarSesionesClase() {
        List<SesionClase> sesiones = sesionClaseBL.listAll();
        return Response.ok(sesiones).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerSesionClasePorId(@PathParam("id") int idSesion) {
        SesionClase sesionClase = sesionClaseBL.obtenerPorId(idSesion);

        if (sesionClase == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("mensaje", "Sesión de clase no encontrada."))
                    .build();
        }

        return Response.ok(sesionClase).build();
    }

    @POST
    public Response registrarSesionClase(SesionClase sesionClase) {
        String mensaje = sesionClaseBL.registrar(sesionClase);

        return Response.status(Response.Status.CREATED)
                .entity(Map.of("mensaje", mensaje))
                .build();
    }

    @PUT
    @Path("{id}")
    public Response actualizarSesionClase(@PathParam("id") int idSesion,
                                           SesionClase sesionClase) {
        String mensaje = sesionClaseBL.actualizar(idSesion, sesionClase);

        return Response.ok(Map.of("mensaje", mensaje)).build();
    }

    @DELETE
    @Path("{id}")
    public Response eliminarSesionClase(@PathParam("id") int idSesion) {
        String mensaje = sesionClaseBL.eliminar(idSesion);

        return Response.ok(Map.of("mensaje", mensaje)).build();
    }
}
