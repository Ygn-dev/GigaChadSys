package pe.edu.pucp.gigachadsys.services;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.gigachadsys.bl.impl.pagos.PagoBLImpl;
import pe.edu.pucp.gigachadsys.bl.inter.pagos.PagoBL;
import pe.edu.pucp.gigachadsys.model.pagos.Pago;

import java.util.List;
import java.util.Map;

@Path("PagoRS")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PagoRS {

    private final PagoBL pagoBL;

    public PagoRS() {
        this.pagoBL = new PagoBLImpl();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarPagos() {
        List<Pago> pagos = pagoBL.listarPagos();
        return Response.ok(pagos).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPagoPorId(@PathParam("id") int idPago) {
        Pago pago = pagoBL.obtenerPorId(idPago);

        if (pago == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("mensaje", "Pago no encontrado."))
                    .build();
        }

        return Response.ok(pago).build();
    }

    @POST
    public Response registrarPago(Pago pago) {
        String mensaje = pagoBL.registrar(pago);

        return Response.status(Response.Status.CREATED)
                .entity(Map.of("mensaje", mensaje, "idPago", pago.getIdPago()))
                .build();
    }

    @PUT
    @Path("{id}")
    public Response actualizarPago(@PathParam("id") int idPago,
                                    Pago pago) {
        String mensaje = pagoBL.actualizar(idPago, pago);

        return Response.ok(Map.of("mensaje", mensaje)).build();
    }

    @DELETE
    @Path("{id}")
    public Response eliminarPago(@PathParam("id") int idPago) {
        String mensaje = pagoBL.eliminar(idPago);

        return Response.ok(Map.of("mensaje", mensaje)).build();
    }
}
