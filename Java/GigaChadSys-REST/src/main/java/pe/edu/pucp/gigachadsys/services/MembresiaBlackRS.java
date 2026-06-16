package pe.edu.pucp.gigachadsys.services;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.gigachadsys.bl.impl.membresias.MembresiaBlackBLImpl;
import pe.edu.pucp.gigachadsys.bl.inter.membresias.MembresiaBlackBL;
import pe.edu.pucp.gigachadsys.model.membresias.MembresiaBlack;

import java.util.List;
import java.util.Map;

@Path("MembresiaBlackRS")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MembresiaBlackRS {

    private final MembresiaBlackBL membresiaBlackBL;

    public MembresiaBlackRS() {
        this.membresiaBlackBL = new MembresiaBlackBLImpl();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarMembresiasBlack() {
        List<MembresiaBlack> membresias = membresiaBlackBL.listarMembresiasBlack();
        return Response.ok(membresias).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerMembresiaBlackPorId(@PathParam("id") int idMembresia) {
        MembresiaBlack membresia = membresiaBlackBL.obtenerPorId(idMembresia);

        if (membresia == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("mensaje", "Membresía Black no encontrada."))
                    .build();
        }

        return Response.ok(membresia).build();
    }

    @POST
    public Response registrarMembresiaBlack(MembresiaBlack membresia) {
        String mensaje = membresiaBlackBL.registrar(membresia);

        return Response.status(Response.Status.CREATED)
                .entity(Map.of("mensaje", mensaje))
                .build();
    }

    @PUT
    @Path("{id}")
    public Response actualizarMembresiaBlack(@PathParam("id") int idMembresia,
                                              MembresiaBlack membresia) {
        String mensaje = membresiaBlackBL.actualizar(idMembresia, membresia);

        return Response.ok(Map.of("mensaje", mensaje)).build();
    }

    @DELETE
    @Path("{id}")
    public Response eliminarMembresiaBlack(@PathParam("id") int idMembresia) {
        String mensaje = membresiaBlackBL.eliminar(idMembresia);

        return Response.ok(Map.of("mensaje", mensaje)).build();
    }
}
