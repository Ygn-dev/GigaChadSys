package pe.edu.pucp.gigachadsys.services;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.gigachadsys.bl.impl.usuarios.EntrenadorBLImpl;
import pe.edu.pucp.gigachadsys.bl.inter.usuarios.EntrenadorBL;
import pe.edu.pucp.gigachadsys.model.personas.Entrenador;

import java.util.List;
import java.util.Map;

@Path("EntrenadorRS")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EntrenadorRS {

    private final EntrenadorBL entrenadorBL;

    public EntrenadorRS() {
        this.entrenadorBL = new EntrenadorBLImpl();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarEntrenadores() {
        List<Entrenador> entrenadores = entrenadorBL.listarEntrenadores();
        return Response.ok(entrenadores).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerEntrenadorPorId(@PathParam("id") int idUsuario) {
        Entrenador entrenador = entrenadorBL.obtenerPorId(idUsuario);

        if (entrenador == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("mensaje", "Entrenador no encontrado."))
                    .build();
        }

        return Response.ok(entrenador).build();
    }

    @POST
    public Response registrarEntrenador(Entrenador entrenador) {
        String mensaje = entrenadorBL.registrar(entrenador);

        return Response.status(Response.Status.CREATED)
                .entity(Map.of("mensaje", mensaje))
                .build();
    }

    @PUT
    @Path("{id}")
    public Response actualizarEntrenador(@PathParam("id") int idUsuario,
                                         Entrenador entrenador) {
        String mensaje = entrenadorBL.actualizar(idUsuario, entrenador);

        return Response.ok(Map.of("mensaje", mensaje)).build();
    }

    @DELETE
    @Path("{id}")
    public Response eliminarEntrenador(@PathParam("id") int idUsuario) {
        String mensaje = entrenadorBL.eliminar(idUsuario);

        return Response.ok(Map.of("mensaje", mensaje)).build();
    }
}
