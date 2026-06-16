package pe.edu.pucp.gigachadsys.services;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.gigachadsys.bl.impl.membresias.MembresiaBasicBLImpl;
import pe.edu.pucp.gigachadsys.bl.inter.membresias.MembresiaBasicBL;
import pe.edu.pucp.gigachadsys.model.membresias.MembresiaBasic;

import java.util.List;
import java.util.Map;

@Path("MembresiaBasicRS")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MembresiaBasicRS {

    private final MembresiaBasicBL membresiaBasicBL;

    public MembresiaBasicRS() {
        this.membresiaBasicBL = new MembresiaBasicBLImpl();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarMembresiasBasic() {
        List<MembresiaBasic> membresias = membresiaBasicBL.listarMembresiasBasic();
        return Response.ok(membresias).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerMembresiaBasicPorId(@PathParam("id") int idMembresia) {
        MembresiaBasic membresia = membresiaBasicBL.obtenerPorId(idMembresia);

        if (membresia == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("mensaje", "Membresía Basic no encontrada."))
                    .build();
        }

        return Response.ok(membresia).build();
    }

    @POST
    public Response registrarMembresiaBasic(MembresiaBasic membresia) {
        String mensaje = membresiaBasicBL.registrar(membresia);

        return Response.status(Response.Status.CREATED)
                .entity(Map.of("mensaje", mensaje))
                .build();
    }

    @PUT
    @Path("{id}")
    public Response actualizarMembresiaBasic(@PathParam("id") int idMembresia,
                                              MembresiaBasic membresia) {
        String mensaje = membresiaBasicBL.actualizar(idMembresia, membresia);

        return Response.ok(Map.of("mensaje", mensaje)).build();
    }

    @DELETE
    @Path("{id}")
    public Response eliminarMembresiaBasic(@PathParam("id") int idMembresia) {
        String mensaje = membresiaBasicBL.eliminar(idMembresia);

        return Response.ok(Map.of("mensaje", mensaje)).build();
    }
}
