package pe.edu.pucp.gigachadsys.services;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import pe.edu.pucp.gigachadsys.bl.impl.clases.SesionClaseBLImpl;
import pe.edu.pucp.gigachadsys.bl.impl.pagos.MetodoPagoBLImpl;
import pe.edu.pucp.gigachadsys.bl.impl.pagos.PagoBLImpl;
import pe.edu.pucp.gigachadsys.bl.impl.suscripcion.SuscripcionBLImpl;
import pe.edu.pucp.gigachadsys.bl.impl.usuarios.EntrenadorBLImpl;
import pe.edu.pucp.gigachadsys.bl.impl.usuarios.SocioBLImpl;

import pe.edu.pucp.gigachadsys.bl.inter.clases.SesionClaseBL;
import pe.edu.pucp.gigachadsys.bl.inter.pagos.MetodoPagoBL;
import pe.edu.pucp.gigachadsys.bl.inter.pagos.PagoBL;
import pe.edu.pucp.gigachadsys.bl.inter.suscripcion.SuscripcionBL;
import pe.edu.pucp.gigachadsys.bl.inter.usuarios.EntrenadorBL;
import pe.edu.pucp.gigachadsys.bl.inter.usuarios.SocioBL;

import pe.edu.pucp.gigachadsys.model.clases.SesionClase;
import pe.edu.pucp.gigachadsys.model.membresias.Suscripcion;
import pe.edu.pucp.gigachadsys.model.pagos.MetodoPago;
import pe.edu.pucp.gigachadsys.model.pagos.Pago;
import pe.edu.pucp.gigachadsys.model.personas.Entrenador;
import pe.edu.pucp.gigachadsys.model.personas.Socio;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    private final SesionClaseBL sesionClaseBL;

    public ReporteRS() {
        this.pagoBL = new PagoBLImpl();
        this.metodoPagoBL = new MetodoPagoBLImpl();
        this.suscripcionBL = new SuscripcionBLImpl();
        this.socioBL = new SocioBLImpl();
        this.entrenadorBL = new EntrenadorBLImpl();
        this.sesionClaseBL = new SesionClaseBLImpl();
    }

    @GET
    @Path("estado-socios")
    @Produces("application/pdf")
    public Response generarReporteEstadoSocios() {
        try {
            List<ReporteSocioDTO> sociosReporte = obtenerSociosParaReporte();

            long totalSocios = sociosReporte.size();
            long sociosActivos = sociosReporte.stream().filter(s -> "Activo".equalsIgnoreCase(s.getEstado())).count();
            long sociosInactivos = sociosReporte.stream().filter(s -> "Inactivo".equalsIgnoreCase(s.getEstado())).count();
            long sociosPendientes = sociosReporte.stream().filter(s -> "Pendiente".equalsIgnoreCase(s.getEstado())).count();

            BigDecimal pendientePorCobrar = sociosReporte.stream()
                    .map(ReporteSocioDTO::getMontoPendiente)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            long basicCantidad = sociosReporte.stream()
                    .filter(s -> s.getMembresia() != null && s.getMembresia().toLowerCase().contains("basic"))
                    .count();

            long blackCantidad = sociosReporte.stream()
                    .filter(s -> s.getMembresia() != null && s.getMembresia().toLowerCase().contains("black"))
                    .count();

            long sinPlanCantidad = sociosReporte.stream()
                    .filter(s -> s.getMembresia() == null || s.getMembresia().toLowerCase().contains("sin plan"))
                    .count();

            Map<String, Object> parametros = parametrosBase("Reporte de Estado de Socios");
            parametros.put("totalSocios", String.valueOf(totalSocios));
            parametros.put("sociosActivos", String.valueOf(sociosActivos));
            parametros.put("sociosInactivos", String.valueOf(sociosInactivos));
            parametros.put("sociosPendientes", String.valueOf(sociosPendientes));
            parametros.put("pendientePorCobrar", formatoSoles(pendientePorCobrar));
            parametros.put("basicCantidad", String.valueOf(basicCantidad));
            parametros.put("blackCantidad", String.valueOf(blackCantidad));
            parametros.put("sinPlanCantidad", String.valueOf(sinPlanCantidad));

            return generarPdf(
                    "/reports/reporte_estado_socios.jasper",
                    "reporte_estado_socios.pdf",
                    parametros,
                    sociosReporte
            );

        } catch (Exception e) {
            return respuestaError("Error al generar reporte de socios: " + e.getMessage(), e);
        }
    }

    @GET
    @Path("entrenadores")
    @Produces("application/pdf")
    public Response generarReporteEntrenadores() {
        try {
            Date mesReferencia = obtenerMesReferenciaEntrenadores();

            List<ReporteEntrenadorDTO> entrenadoresReporte =
                    obtenerEntrenadoresParaReporte(mesReferencia);

            long totalEntrenadores = entrenadoresReporte.size();
            long entrenadoresActivos = entrenadoresReporte.stream()
                    .filter(e -> "Activo".equalsIgnoreCase(e.getEstado()))
                    .count();

            BigDecimal sueldosPagados = entrenadoresReporte.stream()
                    .filter(e -> "Pagado".equalsIgnoreCase(e.getEstadoPago()))
                    .map(ReporteEntrenadorDTO::getSueldoMensual)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal sueldosPendientes = entrenadoresReporte.stream()
                    .filter(e -> "Pendiente".equalsIgnoreCase(e.getEstadoPago()))
                    .map(ReporteEntrenadorDTO::getSueldoMensual)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            int sesionesAsignadasMes = entrenadoresReporte.stream()
                    .mapToInt(e -> e.getSesionesAsignadas() != null ? e.getSesionesAsignadas() : 0)
                    .sum();

            Map<String, Object> parametros = parametrosBase("Reporte de Entrenadores");
            parametros.put("totalEntrenadores", String.valueOf(totalEntrenadores));
            parametros.put("entrenadoresActivos", String.valueOf(entrenadoresActivos));
            parametros.put("sueldosPagados", formatoSoles(sueldosPagados));
            parametros.put("sueldosPendientes", formatoSoles(sueldosPendientes));
            parametros.put("sesionesAsignadasMes", String.valueOf(sesionesAsignadasMes));
            parametros.put("mesReferencia", new SimpleDateFormat("MMMM yyyy").format(mesReferencia));

            return generarPdf(
                    "/reports/reporte_entrenadores.jasper",
                    "reporte_entrenadores.pdf",
                    parametros,
                    entrenadoresReporte
            );

        } catch (Exception e) {
            return respuestaError("Error al generar reporte de entrenadores: " + e.getMessage(), e);
        }
    }

    @GET
    @Path("recaudacion")
    @Produces("application/pdf")
    public Response generarReporteRecaudacion(@QueryParam("fechaInicio") String fechaInicioTexto,
                                              @QueryParam("fechaFin") String fechaFinTexto) {
        try {
            Date fechaInicio = parsearFecha(fechaInicioTexto);
            Date fechaFin = parsearFecha(fechaFinTexto);

            List<Pago> pagosRango = filtrarPagosPorRango(pagoBL.listarPagos(), fechaInicio, fechaFin);

            BigDecimal totalIngresos = pagosRango.stream()
                    .filter(p -> p.isActivo() && !esPagoEgreso(p))
                    .map(p -> BigDecimal.valueOf(p.getMonto()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal totalEgresos = pagosRango.stream()
                    .filter(p -> p.isActivo() && esPagoEgreso(p))
                    .map(p -> BigDecimal.valueOf(p.getMonto()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal balanceNeto = totalIngresos.subtract(totalEgresos);

            BigDecimal porCobrarSocios = pagosRango.stream()
                    .filter(p -> !p.isActivo() && !esPagoEgreso(p))
                    .map(p -> BigDecimal.valueOf(p.getMonto()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal porPagarStaff = pagosRango.stream()
                    .filter(p -> !p.isActivo() && esPagoEgreso(p))
                    .map(p -> BigDecimal.valueOf(p.getMonto()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            RecaudacionMembresiaResumen resumenMembresias =
                    calcularRecaudacionPorMembresia(pagosRango);

            Map<String, Object> parametros = parametrosBase("Reporte de Recaudación");
            parametros.put("fechaInicio", fechaInicio != null ? formatoFecha(fechaInicio) : "Inicio");
            parametros.put("fechaFin", fechaFin != null ? formatoFecha(fechaFin) : "Actualidad");

            parametros.put("totalIngresos", formatoSoles(totalIngresos));
            parametros.put("totalEgresos", formatoSolesNegativo(totalEgresos));
            parametros.put("balanceNeto", formatoBalance(balanceNeto));
            parametros.put("porCobrarSocios", formatoSoles(porCobrarSocios));
            parametros.put("porPagarStaff", formatoSolesNegativo(porPagarStaff));
            parametros.put("totalMovimientos", String.valueOf(pagosRango.size()));

            parametros.put("totalBasic", formatoSoles(resumenMembresias.totalBasic));
            parametros.put("totalBlack", formatoSoles(resumenMembresias.totalBlack));
            parametros.put("totalSinPlan", formatoSoles(resumenMembresias.totalSinPlan));

            parametros.put("cantidadBasic", String.valueOf(resumenMembresias.cantidadBasic));
            parametros.put("cantidadBlack", String.valueOf(resumenMembresias.cantidadBlack));
            parametros.put("cantidadSinPlan", String.valueOf(resumenMembresias.cantidadSinPlan));

            List<ReportePagoAdministradorDTO> movimientos =
                    obtenerPagosParaReporte(fechaInicio, fechaFin);

            return generarPdf(
                    "/reports/reporte_recaudacion.jasper",
                    "reporte_recaudacion.pdf",
                    parametros,
                    movimientos
            );

        } catch (Exception e) {
            return respuestaError("Error al generar reporte de recaudación: " + e.getMessage(), e);
        }
    }

    @GET
    @Path("pagos-administrador")
    @Produces("application/pdf")
    public Response generarReportePagosAdministrador() {
        try {
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

            Map<String, Object> parametros = parametrosBase("Reporte Financiero Administrativo");
            parametros.put("totalIngresos", formatoSoles(totalIngresos));
            parametros.put("totalEgresos", formatoSolesNegativo(totalEgresos));
            parametros.put("balanceNeto", formatoBalance(balanceNeto));
            parametros.put("porCobrarSocios", formatoSoles(porCobrarSocios));
            parametros.put("porPagarStaff", formatoSolesNegativo(porPagarStaff));
            parametros.put("totalMovimientos", String.valueOf(pagos.size()));

            List<ReportePagoAdministradorDTO> pagosReporte = obtenerPagosParaReporte(null, null);

            return generarPdf(
                    "/reports/reporte_pagos_administrador.jasper",
                    "reporte_financiero_administrativo.pdf",
                    parametros,
                    pagosReporte
            );

        } catch (Exception e) {
            return respuestaError("Error al generar reporte PDF: " + e.getMessage(), e);
        }
    }

    private Response generarPdf(String rutaJasper,
                                String nombreArchivo,
                                Map<String, Object> parametros,
                                List<?> data) throws Exception {

        InputStream jasperStream = getClass().getResourceAsStream(rutaJasper);

        if (jasperStream == null) {
            return Response.serverError()
                    .entity("No se encontró el archivo " + rutaJasper)
                    .type("text/plain")
                    .build();
        }

        InputStream logoStream = getClass().getResourceAsStream("/reports/logo-gigachad-sidebar.png");

        if (logoStream == null) {
            return Response.serverError()
                    .entity("No se encontró el archivo /reports/logo-gigachad-sidebar.png")
                    .type("text/plain")
                    .build();
        }

        parametros.put("logo", logoStream);

        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, parametros, dataSource);

        byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

        return Response.ok(pdf, "application/pdf")
                .header("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\"")
                .build();
    }

    private Map<String, Object> parametrosBase(String titulo) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("titulo", titulo);
        parametros.put("fechaGeneracion", new Date());
        return parametros;
    }

    private List<ReporteSocioDTO> obtenerSociosParaReporte() {
        List<Socio> socios = socioBL.listarSocios();
        List<Suscripcion> suscripciones = suscripcionBL.listarSuscripciones();
        List<Pago> pagos = pagoBL.listarPagos();

        Map<Integer, Pago> pagoPorId = pagos.stream()
                .collect(Collectors.toMap(
                        Pago::getIdPago,
                        p -> p,
                        (a, b) -> a
                ));

        return socios.stream()
                .map(socio -> {
                    Suscripcion suscripcion = obtenerSuscripcionPrincipal(socio, suscripciones);
                    Pago pago = suscripcion != null ? pagoPorId.get(suscripcion.getIdPago()) : null;

                    boolean pagoPendiente = pago != null && !pago.isActivo() && !esPagoEgreso(pago);
                    BigDecimal montoPendiente = pagoPendiente
                            ? BigDecimal.valueOf(pago.getMonto())
                            : BigDecimal.ZERO;

                    String membresia = obtenerNombreMembresia(suscripcion);
                    String estado = obtenerEstadoSocio(socio, pagoPendiente);

                    return new ReporteSocioDTO(
                            obtenerNombreSocio(socio),
                            socio.getDni() > 0 ? String.valueOf(socio.getDni()) : "-",
                            socio.getEmail() != null ? socio.getEmail() : "-",
                            socio.getTelefono() > 0 ? String.valueOf(socio.getTelefono()) : "-",
                            membresia,
                            estado,
                            montoPendiente
                    );
                })
                .sorted(Comparator.comparing(ReporteSocioDTO::getNombreCompleto))
                .collect(Collectors.toList());
    }

    private List<ReporteEntrenadorDTO> obtenerEntrenadoresParaReporte(Date mesReferencia) {
        List<Entrenador> entrenadores = entrenadorBL.listarEntrenadores();
        List<Pago> pagosStaffMes = pagoBL.listarPagos().stream()
                .filter(this::esPagoEgreso)
                .filter(p -> esPagoDelMes(p, mesReferencia))
                .collect(Collectors.toList());

        List<SesionClase> sesionesMes = sesionClaseBL.listAll().stream()
                .filter(this::esSesionActiva)
                .filter(s -> s.getFechaSesion() != null && esMismoMes(s.getFechaSesion(), mesReferencia))
                .collect(Collectors.toList());

        return entrenadores.stream()
                .map(entrenador -> {
                    Pago pagoMes = obtenerPagoEntrenadorMes(entrenador, pagosStaffMes);

                    int sesionesAsignadas = (int) sesionesMes.stream()
                            .filter(s -> s.getEntrenador() != null)
                            .filter(s -> s.getEntrenador().getIdUsuario() == entrenador.getIdUsuario())
                            .count();

                    BigDecimal sueldo = pagoMes != null
                            ? BigDecimal.valueOf(pagoMes.getMonto())
                            : BigDecimal.valueOf(entrenador.getSueldo());

                    String estadoPago = obtenerEstadoPagoEntrenador(pagoMes);

                    return new ReporteEntrenadorDTO(
                            obtenerNombreEntrenador(entrenador),
                            entrenador.getDni() > 0 ? String.valueOf(entrenador.getDni()) : "-",
                            entrenador.getEmail() != null ? entrenador.getEmail() : "-",
                            entrenador.getTelefono() > 0 ? String.valueOf(entrenador.getTelefono()) : "-",
                            entrenador.getEspecialidad() != null ? entrenador.getEspecialidad() : "Sin especialidad",
                            estaActivo(entrenador.getActivo()) ? "Activo" : "Inactivo",
                            sueldo,
                            estadoPago,
                            sesionesAsignadas
                    );
                })
                .sorted(Comparator.comparing(ReporteEntrenadorDTO::getNombreCompleto))
                .collect(Collectors.toList());
    }

    private List<ReportePagoAdministradorDTO> obtenerPagosParaReporte(Date fechaInicio, Date fechaFin) {
        List<Pago> pagos = filtrarPagosPorRango(pagoBL.listarPagos(), fechaInicio, fechaFin);
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

    private RecaudacionMembresiaResumen calcularRecaudacionPorMembresia(List<Pago> pagosRango) {
        List<Suscripcion> suscripciones = suscripcionBL.listarSuscripciones();

        Map<Integer, Suscripcion> suscripcionPorPago = suscripciones.stream()
                .filter(s -> s.getIdPago() > 0)
                .collect(Collectors.toMap(
                        Suscripcion::getIdPago,
                        s -> s,
                        (a, b) -> a.isActivo() ? a : b
                ));

        RecaudacionMembresiaResumen resumen = new RecaudacionMembresiaResumen();

        pagosRango.stream()
                .filter(p -> p.isActivo() && !esPagoEgreso(p))
                .forEach(pago -> {
                    Suscripcion suscripcion = suscripcionPorPago.get(pago.getIdPago());
                    BigDecimal monto = BigDecimal.valueOf(pago.getMonto());

                    if (suscripcion != null && suscripcion.getIdMembresiaBlack() != null) {
                        resumen.totalBlack = resumen.totalBlack.add(monto);
                        resumen.cantidadBlack++;
                    } else if (suscripcion != null && suscripcion.getIdMembresiaBasic() != null) {
                        resumen.totalBasic = resumen.totalBasic.add(monto);
                        resumen.cantidadBasic++;
                    } else {
                        resumen.totalSinPlan = resumen.totalSinPlan.add(monto);
                        resumen.cantidadSinPlan++;
                    }
                });

        return resumen;
    }

    private Suscripcion obtenerSuscripcionPrincipal(Socio socio, List<Suscripcion> suscripciones) {
        return suscripciones.stream()
                .filter(s -> s.getIdUsuario() == socio.getIdUsuario())
                .sorted(Comparator.comparing(Suscripcion::isActivo).reversed())
                .findFirst()
                .orElse(null);
    }

    private String obtenerNombreMembresia(Suscripcion suscripcion) {
        if (suscripcion == null) {
            return "Sin plan";
        }

        if (suscripcion.getIdMembresiaBlack() != null) {
            return "Membresía Black";
        }

        if (suscripcion.getIdMembresiaBasic() != null) {
            return "Membresía Basic";
        }

        return "Sin plan";
    }

    private String obtenerEstadoSocio(Socio socio, boolean pagoPendiente) {
        if (!estaActivo(socio.getActivo())) {
            return "Inactivo";
        }

        if (pagoPendiente) {
            return "Pendiente";
        }

        return "Activo";
    }

    private Date obtenerMesReferenciaEntrenadores() {
        List<Pago> pagosStaff = pagoBL.listarPagos().stream()
                .filter(this::esPagoEgreso)
                .filter(p -> p.getFechaPago() != null)
                .collect(Collectors.toList());

        Date hoy = new Date();

        boolean hayPagoMesActual = pagosStaff.stream()
                .anyMatch(p -> esMismoMes(p.getFechaPago(), hoy));

        if (hayPagoMesActual) {
            return hoy;
        }

        return pagosStaff.stream()
                .map(Pago::getFechaPago)
                .max(Date::compareTo)
                .orElse(hoy);
    }

    private Pago obtenerPagoEntrenadorMes(Entrenador entrenador, List<Pago> pagosStaffMes) {
        return pagosStaffMes.stream()
                .filter(p -> pagoPerteneceAEntrenador(p, entrenador))
                .sorted(Comparator.comparing(Pago::isActivo).reversed())
                .findFirst()
                .orElse(null);
    }

    private boolean pagoPerteneceAEntrenador(Pago pago, Entrenador entrenador) {
        String tipo = normalizar(pago.getTipo());
        String nombres = normalizar(entrenador.getNombres());
        String apellidoPaterno = normalizar(entrenador.getApellidoPaterno());
        String nombreCompleto = normalizar(obtenerNombreEntrenador(entrenador));

        if (tipo.isBlank()) {
            return false;
        }

        if (!tipo.contains("entrenador") && !tipo.contains("sueldo") && !tipo.contains("bono")) {
            return false;
        }

        if (!nombreCompleto.isBlank() && tipo.contains(nombreCompleto)) {
            return true;
        }

        String primerNombre = "";
        if (!nombres.isBlank()) {
            primerNombre = nombres.split(" ")[0];
        }

        if (!primerNombre.isBlank() && tipo.contains(primerNombre)) {
            return true;
        }

        return !apellidoPaterno.isBlank() && tipo.contains(apellidoPaterno);
    }

    private String obtenerEstadoPagoEntrenador(Pago pago) {
        if (pago == null) {
            return "Sin registro";
        }

        return pago.isActivo() ? "Pagado" : "Pendiente";
    }

    private boolean esSesionActiva(SesionClase sesion) {
        return sesion.getActivo() != null && sesion.getActivo();
    }

    private boolean esPagoDelMes(Pago pago, Date mesReferencia) {
        if (pago.getFechaPago() != null) {
            return esMismoMes(pago.getFechaPago(), mesReferencia);
        }

        return false;
    }

    private boolean esMismoMes(Date fecha, Date referencia) {
        Calendar calFecha = Calendar.getInstance();
        calFecha.setTime(fecha);

        Calendar calReferencia = Calendar.getInstance();
        calReferencia.setTime(referencia);

        return calFecha.get(Calendar.YEAR) == calReferencia.get(Calendar.YEAR)
                && calFecha.get(Calendar.MONTH) == calReferencia.get(Calendar.MONTH);
    }

    private List<Pago> filtrarPagosPorRango(List<Pago> pagos, Date fechaInicio, Date fechaFin) {
        return pagos.stream()
                .filter(p -> {
                    if (p.getFechaPago() == null) {
                        return fechaInicio == null && fechaFin == null;
                    }

                    Date fecha = limpiarHora(p.getFechaPago());

                    if (fechaInicio != null && fecha.before(limpiarHora(fechaInicio))) {
                        return false;
                    }

                    if (fechaFin != null && fecha.after(limpiarHora(fechaFin))) {
                        return false;
                    }

                    return true;
                })
                .collect(Collectors.toList());
    }

    private Date parsearFecha(String texto) throws Exception {
        if (texto == null || texto.isBlank()) {
            return null;
        }

        return new SimpleDateFormat("yyyy-MM-dd").parse(texto);
    }

    private Date limpiarHora(Date fecha) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private String formatoFecha(Date fecha) {
        return new SimpleDateFormat("yyyy-MM-dd").format(fecha);
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

    private boolean estaActivo(Boolean activo) {
        return activo != null && activo;
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

    private Response respuestaError(String mensaje, Exception e) {
        e.printStackTrace();

        return Response.serverError()
                .entity(mensaje)
                .type("text/plain")
                .build();
    }

    private static class RecaudacionMembresiaResumen {
        BigDecimal totalBasic = BigDecimal.ZERO;
        BigDecimal totalBlack = BigDecimal.ZERO;
        BigDecimal totalSinPlan = BigDecimal.ZERO;

        int cantidadBasic = 0;
        int cantidadBlack = 0;
        int cantidadSinPlan = 0;
    }
}