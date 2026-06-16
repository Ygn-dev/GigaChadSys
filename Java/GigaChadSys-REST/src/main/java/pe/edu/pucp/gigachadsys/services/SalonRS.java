package pe.edu.pucp.gigachadsys.services;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.gigachadsys.bl.impl.clases.SalonBLImpl;
import pe.edu.pucp.gigachadsys.bl.inter.clases.SalonBL;
import pe.edu.pucp.gigachadsys.model.clases.Salon;

import java.util.List;
import java.util.Map;

@Path("SalonRS")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SalonRS {

    private final SalonBL salonBL;

    public SalonRS() {
        this.salonBL = new SalonBLImpl();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarSalones() {
        List<Salon> salones = salonBL.listAll();
        return Response.ok(salones).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerSalonPorId(@PathParam("id") int idSalon) {
        Salon salon = salonBL.obtenerPorId(idSalon);

        if (salon == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("mensaje", "Salón no encontrado."))
                    .build();
        }

        return Response.ok(salon).build();
    }

    @POST
    public Response registrarSalon(Salon salon) {
        String mensaje = salonBL.registrar(salon);

        return Response.status(Response.Status.CREATED)
                .entity(Map.of("mensaje", mensaje))
                .build();
    }

    @PUT
    @Path("{id}")
    public Response actualizarSalon(@PathParam("id") int idSalon,
                                     Salon salon) {
        String mensaje = salonBL.actualizar(idSalon, salon);

        return Response.ok(Map.of("mensaje", mensaje)).build();
    }

    @DELETE
    @Path("{id}")
    public Response eliminarSalon(@PathParam("id") int idSalon) {
        String mensaje = salonBL.eliminar(idSalon);

        return Response.ok(Map.of("mensaje", mensaje)).build();
    }
}
