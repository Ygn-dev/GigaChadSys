namespace GigaChadSys.Servicios;

public class ReporteServicio
{
    private readonly HttpClient _httpClient;
    private const string Endpoint = "reportes/pagos-administrador";

    public ReporteServicio(HttpClient httpClient)
    {
        _httpClient = httpClient;
    }

    public async Task<byte[]> DescargarReportePagosAdministradorAsync()
    {
        return await _httpClient.GetByteArrayAsync(Endpoint);
    }
}