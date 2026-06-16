package pe.edu.pucp.gigachadsys.services;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.gigachadsys.bl.impl.pagos.MetodoPagoBLImpl;
import pe.edu.pucp.gigachadsys.bl.inter.pagos.MetodoPagoBL;
import pe.edu.pucp.gigachadsys.model.pagos.MetodoPago;

import java.util.List;
import java.util.Map;

@Path("MetodoPagoRS")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MetodoPagoRS {

    private final MetodoPagoBL metodoPagoBL;

    public MetodoPagoRS() {
        this.metodoPagoBL = new MetodoPagoBLImpl();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarMetodosDePago() {
        List<MetodoPago> metodos = metodoPagoBL.listarMetodosDePago();
        return Response.ok(metodos).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerMetodoPagoPorId(@PathParam("id") int idMetodoPago) {
        MetodoPago metodoPago = metodoPagoBL.obtenerPorId(idMetodoPago);

        if (metodoPago == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("mensaje", "Método de pago no encontrado."))
                    .build();
        }

        return Response.ok(metodoPago).build();
    }

    @POST
    public Response registrarMetodoPago(MetodoPago metodoPago) {
        String mensaje = metodoPagoBL.registrar(metodoPago);

        return Response.status(Response.Status.CREATED)
                .entity(Map.of("mensaje", mensaje))
                .build();
    }

    @PUT
    @Path("{id}")
    public Response actualizarMetodoPago(@PathParam("id") int idMetodoPago,
                                          MetodoPago metodoPago) {
        String mensaje = metodoPagoBL.actualizar(idMetodoPago, metodoPago);

        return Response.ok(Map.of("mensaje", mensaje)).build();
    }

    @DELETE
    @Path("{id}")
    public Response eliminarMetodoPago(@PathParam("id") int idMetodoPago) {
        String mensaje = metodoPagoBL.eliminar(idMetodoPago);

        return Response.ok(Map.of("mensaje", mensaje)).build();
    }
}
