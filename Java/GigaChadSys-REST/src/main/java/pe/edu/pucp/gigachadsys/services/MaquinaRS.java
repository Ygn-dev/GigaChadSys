package pe.edu.pucp.gigachadsys.services;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.gigachadsys.bl.impl.maquinas.MaquinasBLImpl;
import pe.edu.pucp.gigachadsys.bl.inter.maquinas.MaquinasBL;
import pe.edu.pucp.gigachadsys.model.clases.Maquina;

import java.util.List;
import java.util.Map;

@Path("MaquinaRS")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MaquinaRS {

    private final MaquinasBL maquinasBL;

    public MaquinaRS() {
        this.maquinasBL = new MaquinasBLImpl();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarMaquinas() {
        List<Maquina> maquinas = maquinasBL.ListAll();
        return Response.ok(maquinas).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerMaquinaPorId(@PathParam("id") int idMaquina) {
        Maquina maquina = maquinasBL.obtenerPorId(idMaquina);

        if (maquina == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("mensaje", "Máquina no encontrada."))
                    .build();
        }

        return Response.ok(maquina).build();
    }

    @POST
    public Response registrarMaquina(Maquina maquina) {
        String mensaje = maquinasBL.registrar(maquina);

        return Response.status(Response.Status.CREATED)
                .entity(Map.of("mensaje", mensaje))
                .build();
    }

    @PUT
    @Path("{id}")
    public Response actualizarMaquina(@PathParam("id") int idMaquina,
                                      Maquina maquina) {
        String mensaje = maquinasBL.actualizar(idMaquina, maquina);

        return Response.ok(Map.of("mensaje", mensaje)).build();
    }

    @DELETE
    @Path("{id}")
    public Response eliminarMaquina(@PathParam("id") int idMaquina) {
        String mensaje = maquinasBL.eliminar(idMaquina);

        return Response.ok(Map.of("mensaje", mensaje)).build();
    }
}
