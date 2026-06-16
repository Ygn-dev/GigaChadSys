package pe.edu.pucp.gigachadsys.services;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.gigachadsys.bl.impl.clases.ClaseGrupalBLImpl;
import pe.edu.pucp.gigachadsys.bl.inter.clases.ClaseGrupalBL;
import pe.edu.pucp.gigachadsys.model.clases.ClaseGrupal;

import java.util.List;
import java.util.Map;

@Path("ClaseGrupalRS")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClaseGrupalRS {

    private final ClaseGrupalBL claseGrupalBL;

    public ClaseGrupalRS() {
        this.claseGrupalBL = new ClaseGrupalBLImpl();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarClasesGrupales() {
        List<ClaseGrupal> clases = claseGrupalBL.listAll();
        return Response.ok(clases).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerClaseGrupalPorId(@PathParam("id") int idClase) {
        ClaseGrupal claseGrupal = claseGrupalBL.obtenerPorId(idClase);

        if (claseGrupal == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("mensaje", "Clase grupal no encontrada."))
                    .build();
        }

        return Response.ok(claseGrupal).build();
    }

    @POST
    public Response registrarClaseGrupal(ClaseGrupal claseGrupal) {
        String mensaje = claseGrupalBL.registrar(claseGrupal);

        return Response.status(Response.Status.CREATED)
                .entity(Map.of("mensaje", mensaje))
                .build();
    }

    @PUT
    @Path("{id}")
    public Response actualizarClaseGrupal(@PathParam("id") int idClase,
                                          ClaseGrupal claseGrupal) {
        String mensaje = claseGrupalBL.actualizar(idClase, claseGrupal);

        return Response.ok(Map.of("mensaje", mensaje)).build();
    }

    @DELETE
    @Path("{id}")
    public Response eliminarClaseGrupal(@PathParam("id") int idClase) {
        String mensaje = claseGrupalBL.eliminar(idClase);

        return Response.ok(Map.of("mensaje", mensaje)).build();
    }
}
