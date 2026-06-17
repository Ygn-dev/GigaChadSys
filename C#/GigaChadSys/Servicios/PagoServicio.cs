using System.Net.Http.Json;
using GigaChadSysModel;

namespace GigaChadSys.Servicios;

/// <summary>
/// Servicio que consume el endpoint REST PagoRS del backend Java.
/// Base URL: http://localhost:8080/GigaChadSys-REST/webresources/PagoRS
/// </summary>
public class PagoServicio
{
    private readonly HttpClient _httpClient;
    private const string Endpoint = "PagoRS";

    public PagoServicio(HttpClient httpClient)
    {
        _httpClient = httpClient;
    }

    /// <summary>
    /// GET /PagoRS — Lista todos los pagos.
    /// </summary>
    public async Task<List<Pago>> ListarPagosAsync()
    {
        var pagos = await _httpClient.GetFromJsonAsync<List<Pago>>(Endpoint);
        return pagos ?? new List<Pago>();
    }

    /// <summary>
    /// GET /PagoRS/{id} — Obtiene un pago por su ID.
    /// </summary>
    public async Task<Pago?> ObtenerPorIdAsync(int idPago)
    {
        var response = await _httpClient.GetAsync($"{Endpoint}/{idPago}");
        if (response.IsSuccessStatusCode)
            return await response.Content.ReadFromJsonAsync<Pago>();
        return null;
    }

    /// <summary>
    /// POST /PagoRS — Registra un nuevo pago.
    /// </summary>
    public async Task<string> RegistrarAsync(Pago pago)
    {
        var response = await _httpClient.PostAsJsonAsync(Endpoint, pago);
        var result = await response.Content.ReadFromJsonAsync<MensajeRespuesta>();
        return result?.Mensaje ?? "Error al registrar pago.";
    }

    /// <summary>
    /// PUT /PagoRS/{id} — Actualiza los datos de un pago existente.
    /// </summary>
    public async Task<string> ActualizarAsync(int idPago, Pago pago)
    {
        var response = await _httpClient.PutAsJsonAsync($"{Endpoint}/{idPago}", pago);
        var result = await response.Content.ReadFromJsonAsync<MensajeRespuesta>();
        return result?.Mensaje ?? "Error al actualizar pago.";
    }

    /// <summary>
    /// DELETE /PagoRS/{id} — Elimina (desactiva) un pago por su ID.
    /// </summary>
    public async Task<string> EliminarAsync(int idPago)
    {
        var response = await _httpClient.DeleteAsync($"{Endpoint}/{idPago}");
        var result = await response.Content.ReadFromJsonAsync<MensajeRespuesta>();
        return result?.Mensaje ?? "Error al eliminar pago.";
    }
}
