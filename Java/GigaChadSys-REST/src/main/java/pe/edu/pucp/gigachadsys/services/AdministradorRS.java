package pe.edu.pucp.gigachadsys.services;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.gigachadsys.bl.impl.usuarios.AdministradorBLImpl;
import pe.edu.pucp.gigachadsys.bl.inter.usuarios.AdministradorBL;
import pe.edu.pucp.gigachadsys.model.personas.Administrador;

import java.util.List;
import java.util.Map;

@Path("AdministradorRS")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdministradorRS {

    private final AdministradorBL administradorBL;

    public AdministradorRS() {
        this.administradorBL = new AdministradorBLImpl();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarAdministradores() {
        List<Administrador> administradores = administradorBL.listarAdministradores();
        return Response.ok(administradores).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerAdministradorPorId(@PathParam("id") int idUsuario) {
        Administrador administrador = administradorBL.obtenerPorId(idUsuario);

        if (administrador == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("mensaje", "Administrador no encontrado."))
                    .build();
        }

        return Response.ok(administrador).build();
    }

    @POST
    public Response registrarAdministrador(Administrador administrador) {
        String mensaje = administradorBL.registrar(administrador);

        return Response.status(Response.Status.CREATED)
                .entity(Map.of("mensaje", mensaje))
                .build();
    }

    @PUT
    @Path("{id}")
    public Response actualizarAdministrador(@PathParam("id") int idUsuario,
                                            Administrador administrador) {
        String mensaje = administradorBL.actualizar(idUsuario, administrador);

        return Response.ok(Map.of("mensaje", mensaje)).build();
    }

    @DELETE
    @Path("{id}")
    public Response eliminarAdministrador(@PathParam("id") int idUsuario) {
        String mensaje = administradorBL.eliminar(idUsuario);

        return Response.ok(Map.of("mensaje", mensaje)).build();
    }
}
