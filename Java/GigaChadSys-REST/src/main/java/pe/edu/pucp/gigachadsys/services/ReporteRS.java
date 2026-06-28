package pe.edu.pucp.gigachadsys.services;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import pe.edu.pucp.gigachadsys.bl.impl.pagos.MetodoPagoBLImpl;
import pe.edu.pucp.gigachadsys.bl.impl.pagos.PagoBLImpl;
import pe.edu.pucp.gigachadsys.bl.inter.pagos.MetodoPagoBL;
import pe.edu.pucp.gigachadsys.bl.inter.pagos.PagoBL;
import pe.edu.pucp.gigachadsys.model.pagos.MetodoPago;
import pe.edu.pucp.gigachadsys.model.pagos.Pago;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("reportes")
public class ReporteRS {

    private final PagoBL pagoBL;
    private final MetodoPagoBL metodoPagoBL;

    public ReporteRS() {
        this.pagoBL = new PagoBLImpl();
        this.metodoPagoBL = new MetodoPagoBLImpl();
    }

    @GET
    @Path("pagos-administrador")
    @Produces("application/pdf")
    public Response generarReportePagosAdministrador() {
        try {
            InputStream jasperStream = getClass()
                    .getResourceAsStream("/reports/reporte_pagos_administrador.jasper");

            if (jasperStream == null) {
                return Response.serverError()
                        .entity("No se encontró el archivo /reports/reporte_pagos_administrador.jasper")
                        .type("text/plain")
                        .build();
            }

            InputStream logoStream = getClass()
                    .getResourceAsStream("/reports/logo-gigachad-sidebar.png");

            if (logoStream == null) {
                return Response.serverError()
                        .entity("No se encontró el archivo /reports/logo-gigachad-sidebar.png")
                        .type("text/plain")
                        .build();
            }

            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("titulo", "Reporte de Pagos - Administrador");
            parametros.put("fechaGeneracion", new Date());
            parametros.put("logo", logoStream);

            List<ReportePagoAdministradorDTO> pagosReporte = obtenerPagosReales();

            JRBeanCollectionDataSource dataSource =
                    new JRBeanCollectionDataSource(pagosReporte);

            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(jasperReport, parametros, dataSource);

            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

            return Response.ok(pdf, "application/pdf")
                    .header("Content-Disposition", "attachment; filename=\"reporte_pagos_administrador.pdf\"")
                    .build();

        } catch (Exception e) {
            e.printStackTrace();

            return Response.serverError()
                    .entity("Error al generar reporte PDF: " + e.getMessage())
                    .type("text/plain")
                    .build();
        }
    }

    private List<ReportePagoAdministradorDTO> obtenerPagosReales() {
        List<Pago> pagos = pagoBL.listarPagos();
        List<MetodoPago> metodos = metodoPagoBL.listarMetodosDePago();

        Map<Integer, String> metodoPorId = metodos.stream()
                .collect(Collectors.toMap(
                        MetodoPago::getIdMetodoPago,
                        MetodoPago::getTipo,
                        (a, b) -> a
                ));

        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

        return pagos.stream()
                .map(pago -> new ReportePagoAdministradorDTO(
                        obtenerConcepto(pago),
                        obtenerTipoPago(pago),
                        obtenerMetodoPago(pago, metodoPorId),
                        BigDecimal.valueOf(pago.getMonto()),
                        pago.getFechaPago() != null ? formatoFecha.format(pago.getFechaPago()) : "-",
                        pago.isActivo() ? "Pagado" : "Pendiente"
                ))
                .collect(Collectors.toList());
    }

    private String obtenerConcepto(Pago pago) {
        if (pago.getTipo() == null || pago.getTipo().isBlank()) {
            return "Pago";
        }

        return pago.getTipo();
    }

    private String obtenerTipoPago(Pago pago) {
        if (pago.getTipo() == null || pago.getTipo().isBlank()) {
            return "Pago";
        }

        String tipo = pago.getTipo().toLowerCase();

        if (tipo.contains("black")) {
            return "Black";
        }

        if (tipo.contains("basic")) {
            return "Basic";
        }

        if (tipo.contains("sueldo")) {
            return "Sueldo";
        }

        if (tipo.contains("bono")) {
            return "Bono";
        }

        if (tipo.contains("membres")) {
            return "Membresía";
        }

        return pago.getTipo();
    }

    private String obtenerMetodoPago(Pago pago, Map<Integer, String> metodoPorId) {
        String metodo = metodoPorId.get(pago.getMetodoPago());

        if (metodo == null || metodo.isBlank()) {
            return "No definido";
        }

        return metodo;
    }
}