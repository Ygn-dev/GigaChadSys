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
import pe.edu.pucp.gigachadsys.bl.impl.suscripcion.SuscripcionBLImpl;
import pe.edu.pucp.gigachadsys.bl.impl.usuarios.SocioBLImpl;
import pe.edu.pucp.gigachadsys.bl.impl.usuarios.EntrenadorBLImpl;

import pe.edu.pucp.gigachadsys.bl.inter.pagos.MetodoPagoBL;
import pe.edu.pucp.gigachadsys.bl.inter.pagos.PagoBL;
import pe.edu.pucp.gigachadsys.bl.inter.suscripcion.SuscripcionBL;
import pe.edu.pucp.gigachadsys.bl.inter.usuarios.SocioBL;
import pe.edu.pucp.gigachadsys.bl.inter.usuarios.EntrenadorBL;

import pe.edu.pucp.gigachadsys.model.membresias.Suscripcion;
import pe.edu.pucp.gigachadsys.model.pagos.MetodoPago;
import pe.edu.pucp.gigachadsys.model.pagos.Pago;
import pe.edu.pucp.gigachadsys.model.personas.Socio;
import pe.edu.pucp.gigachadsys.model.personas.Entrenador;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("reportes")
public class ReporteRS {

    private final PagoBL pagoBL;
    private final MetodoPagoBL metodoPagoBL;
    private final SuscripcionBL suscripcionBL;
    private final SocioBL socioBL;
    private final EntrenadorBL entrenadorBL;

    public ReporteRS() {
        this.pagoBL = new PagoBLImpl();
        this.metodoPagoBL = new MetodoPagoBLImpl();
        this.suscripcionBL = new SuscripcionBLImpl();
        this.socioBL = new SocioBLImpl();
        this.entrenadorBL = new EntrenadorBLImpl();
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

            List<Pago> pagos = pagoBL.listarPagos();

            BigDecimal totalIngresos = pagos.stream()
                    .filter(p -> p.isActivo() && !esPagoEgreso(p))
                    .map(p -> BigDecimal.valueOf(p.getMonto()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal totalEgresos = pagos.stream()
                    .filter(p -> p.isActivo() && esPagoEgreso(p))
                    .map(p -> BigDecimal.valueOf(p.getMonto()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal balanceNeto = totalIngresos.subtract(totalEgresos);

            BigDecimal porCobrarSocios = pagos.stream()
                    .filter(p -> !p.isActivo() && !esPagoEgreso(p))
                    .map(p -> BigDecimal.valueOf(p.getMonto()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal porPagarStaff = pagos.stream()
                    .filter(p -> !p.isActivo() && esPagoEgreso(p))
                    .map(p -> BigDecimal.valueOf(p.getMonto()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("titulo", "Reporte Financiero Administrativo");
            parametros.put("fechaGeneracion", new Date());
            parametros.put("logo", logoStream);

            parametros.put("totalIngresos", formatoSoles(totalIngresos));
            parametros.put("totalEgresos", formatoSolesNegativo(totalEgresos));
            parametros.put("balanceNeto", formatoBalance(balanceNeto));
            parametros.put("porCobrarSocios", formatoSoles(porCobrarSocios));
            parametros.put("porPagarStaff", formatoSolesNegativo(porPagarStaff));
            parametros.put("totalMovimientos", String.valueOf(pagos.size()));

            List<ReportePagoAdministradorDTO> pagosReporte = obtenerPagosParaReporte();

            JRBeanCollectionDataSource dataSource =
                    new JRBeanCollectionDataSource(pagosReporte);

            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(jasperReport, parametros, dataSource);

            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

            return Response.ok(pdf, "application/pdf")
                    .header("Content-Disposition", "attachment; filename=\"reporte_financiero_administrativo.pdf\"")
                    .build();

        } catch (Exception e) {
            e.printStackTrace();

            return Response.serverError()
                    .entity("Error al generar reporte PDF: " + e.getMessage())
                    .type("text/plain")
                    .build();
        }
    }

    private List<ReportePagoAdministradorDTO> obtenerPagosParaReporte() {
        List<Pago> pagos = pagoBL.listarPagos();
        List<MetodoPago> metodos = metodoPagoBL.listarMetodosDePago();
        List<Suscripcion> suscripciones = suscripcionBL.listarSuscripciones();
        List<Socio> socios = socioBL.listarSocios();
        List<Entrenador> entrenadores = entrenadorBL.listarEntrenadores();

        Map<Integer, String> metodoPorId = metodos.stream()
                .collect(Collectors.toMap(
                        MetodoPago::getIdMetodoPago,
                        MetodoPago::getTipo,
                        (a, b) -> a
                ));

        Map<Integer, Suscripcion> suscripcionPorPago = suscripciones.stream()
                .filter(s -> s.getIdPago() > 0)
                .collect(Collectors.toMap(
                        Suscripcion::getIdPago,
                        s -> s,
                        (a, b) -> a.isActivo() ? a : b
                ));

        Map<Integer, Socio> socioPorId = socios.stream()
                .collect(Collectors.toMap(
                        Socio::getIdUsuario,
                        s -> s,
                        (a, b) -> a
                ));

        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

        return pagos.stream()
                .sorted(Comparator.comparing(Pago::getFechaPago, Comparator.nullsLast(Date::compareTo)).reversed())
                .map(pago -> {
                    boolean egreso = esPagoEgreso(pago);

                    String usuario = "Usuario no asociado";
                    String rolUsuario = "Usuario";

                    if (egreso) {
                        usuario = obtenerNombreEntrenadorCompletoDesdePago(pago, entrenadores);
                        rolUsuario = "Entrenador";
                    } else {
                        Suscripcion suscripcion = suscripcionPorPago.get(pago.getIdPago());

                        if (suscripcion != null) {
                            Socio socio = socioPorId.get(suscripcion.getIdUsuario());

                            if (socio != null) {
                                usuario = obtenerNombreSocio(socio);
                                rolUsuario = "Socio";
                            } else {
                                usuario = "Socio asociado";
                                rolUsuario = "Socio";
                            }
                        }
                    }

                    String metodo = metodoPorId.getOrDefault(pago.getMetodoPago(), "No definido");

                    BigDecimal monto = BigDecimal.valueOf(pago.getMonto());

                    if (egreso) {
                        monto = monto.negate();
                    }

                    return new ReportePagoAdministradorDTO(
                            usuario,
                            rolUsuario,
                            obtenerConcepto(pago),
                            egreso ? "Egreso" : "Ingreso",
                            metodo,
                            pago.getFechaPago() != null ? formatoFecha.format(pago.getFechaPago()) : "-",
                            pago.isActivo() ? "Pagado" : "Pendiente",
                            monto
                    );
                })
                .collect(Collectors.toList());
    }

    private boolean esPagoEgreso(Pago pago) {
        if (pago.getTipo() == null || pago.getTipo().isBlank()) {
            return false;
        }

        String tipo = pago.getTipo().trim().toLowerCase();

        return tipo.contains("sueldo") ||
                tipo.contains("bono") ||
                tipo.contains("entrenador");
    }

    private String obtenerConcepto(Pago pago) {
        if (pago.getTipo() == null || pago.getTipo().isBlank()) {
            return "Pago";
        }

        return pago.getTipo();
    }

    private String obtenerNombreSocio(Socio socio) {
        String nombre = String.format(
                "%s %s %s",
                socio.getNombres() != null ? socio.getNombres() : "",
                socio.getApellidoPaterno() != null ? socio.getApellidoPaterno() : "",
                socio.getApellidoMaterno() != null ? socio.getApellidoMaterno() : ""
        ).trim();

        return nombre.isBlank() ? "Socio" : nombre;
    }

    private String obtenerNombreEntrenadorCompletoDesdePago(Pago pago, List<Entrenador> entrenadores) {
        String nombreExtraido = extraerNombreEntrenadorDesdePago(pago);

        if (nombreExtraido.isBlank()) {
            return "Entrenador";
        }

        String buscado = normalizar(nombreExtraido);

        for (Entrenador entrenador : entrenadores) {
            String nombres = entrenador.getNombres() != null ? entrenador.getNombres().trim() : "";
            String nombreCompleto = obtenerNombreEntrenador(entrenador);

            String nombresNormalizados = normalizar(nombres);
            String completoNormalizado = normalizar(nombreCompleto);

            if (nombresNormalizados.equals(buscado) ||
                    nombresNormalizados.startsWith(buscado) ||
                    completoNormalizado.contains(buscado)) {
                return nombreCompleto;
            }
        }

        return nombreExtraido;
    }

    private String extraerNombreEntrenadorDesdePago(Pago pago) {
        if (pago.getTipo() == null || pago.getTipo().isBlank()) {
            return "";
        }

        String texto = pago.getTipo().trim();

        texto = texto.replace("Sueldo Entrenador", "")
                .replace("sueldo entrenador", "")
                .replace("Bono Entrenador", "")
                .replace("bono entrenador", "")
                .trim();

        if (texto.contains(" - ")) {
            texto = texto.split(" - ")[0].trim();
        }

        return texto;
    }

    private String obtenerNombreEntrenador(Entrenador entrenador) {
        String nombre = String.format(
                "%s %s %s",
                entrenador.getNombres() != null ? entrenador.getNombres() : "",
                entrenador.getApellidoPaterno() != null ? entrenador.getApellidoPaterno() : "",
                entrenador.getApellidoMaterno() != null ? entrenador.getApellidoMaterno() : ""
        ).trim();

        return nombre.isBlank() ? "Entrenador" : nombre;
    }

    private String normalizar(String texto) {
        if (texto == null) {
            return "";
        }

        String limpio = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");

        return limpio.trim().toLowerCase();
    }

    private String formatoSoles(BigDecimal valor) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return "S/ " + df.format(valor);
    }

    private String formatoSolesNegativo(BigDecimal valor) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return "-S/ " + df.format(valor.abs());
    }

    private String formatoBalance(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) < 0) {
            return formatoSolesNegativo(valor.abs());
        }

        return formatoSoles(valor);
    }
}