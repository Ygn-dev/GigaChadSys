package pe.edu.pucp.gigachadsys.services;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.gigachadsys.bl.impl.suscripcion.SuscripcionBLImpl;
import pe.edu.pucp.gigachadsys.bl.inter.suscripcion.SuscripcionBL;
import pe.edu.pucp.gigachadsys.model.membresias.Suscripcion;

import java.util.List;
import java.util.Map;

@Path("SuscripcionRS")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SuscripcionRS {

    private final SuscripcionBL suscripcionBL;

    public SuscripcionRS() {
        this.suscripcionBL = new SuscripcionBLImpl();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarSuscripciones() {
        List<Suscripcion> suscripciones = suscripcionBL.listarSuscripciones();
        return Response.ok(suscripciones).build();
    }
}
