package pe.edu.pucp.gigachadsys.services;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.gigachadsys.bl.impl.usuarios.SocioBLImpl;
import pe.edu.pucp.gigachadsys.bl.inter.usuarios.SocioBL;
import pe.edu.pucp.gigachadsys.model.personas.Socio;

import java.util.List;
import java.util.Map;

@Path("SocioRS")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SocioRS {

    private final SocioBL socioBL;

    public SocioRS() {
        this.socioBL = new SocioBLImpl();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarSocios() {
        List<Socio> socios = socioBL.listarSocios();
        return Response.ok(socios).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerSocioPorId(@PathParam("id") int idUsuario) {
        Socio socio = socioBL.obtenerPorId(idUsuario);

        if (socio == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("mensaje", "Socio no encontrado."))
                    .build();
        }

        return Response.ok(socio).build();
    }

    @POST
    public Response registrarSocio(Socio socio) {
        String mensaje = socioBL.registrar(socio);

        return Response.status(Response.Status.CREATED)
                .entity(Map.of("mensaje", mensaje))
                .build();
    }

    @PUT
    @Path("{id}")
    public Response actualizarSocio(@PathParam("id") int idUsuario,
                                    Socio socio) {
        String mensaje = socioBL.actualizar(idUsuario, socio);

        return Response.ok(Map.of("mensaje", mensaje)).build();
    }

    @DELETE
    @Path("{id}")
    public Response eliminarSocio(@PathParam("id") int idUsuario) {
        String mensaje = socioBL.eliminar(idUsuario);

        return Response.ok(Map.of("mensaje", mensaje)).build();
    }
}
