namespace GigaChadSys.Servicios;

public class ReporteServicio
{
    private readonly HttpClient _httpClient;

    public ReporteServicio(HttpClient httpClient)
    {
        _httpClient = httpClient;
    }

    public async Task<byte[]> DescargarReportePagosAdministradorAsync()
    {
        return await _httpClient.GetByteArrayAsync("reportes/pagos-administrador");
    }

    public async Task<byte[]> DescargarReporteEstadoSociosAsync()
    {
        return await _httpClient.GetByteArrayAsync("reportes/estado-socios");
    }

    public async Task<byte[]> DescargarReporteEntrenadoresAsync()
    {
        return await _httpClient.GetByteArrayAsync("reportes/entrenadores");
    }

    public async Task<byte[]> DescargarReporteRecaudacionAsync(DateTime fechaInicio, DateTime fechaFin)
    {
        string inicio = fechaInicio.ToString("yyyy-MM-dd");
        string fin = fechaFin.ToString("yyyy-MM-dd");

        return await _httpClient.GetByteArrayAsync(
            $"reportes/recaudacion?fechaInicio={inicio}&fechaFin={fin}"
        );
    }
}