using System.Net.Http.Json;
using GigaChadSys.Servicios.DTO;

namespace GigaChadSys.Servicios;

/// <summary>
/// Servicio que consume el endpoint REST PagoRS del backend Java.
/// Base URL: http://localhost:8080/GigaChadSys-REST/webresources/PagoRS
/// Java model: Pago.java — idPago, fechaPago, monto, tipo, metodoPago, activo
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
    /// GET /PagoRS — Lista todos los pagos activos.
    /// </summary>
    public async Task<List<PagoDTO>> ListarPagosAsync()
    {
        var pagos = await _httpClient.GetFromJsonAsync<List<PagoDTO>>(Endpoint);
        return pagos ?? new List<PagoDTO>();
    }

    /// <summary>
    /// GET /PagoRS/{id} — Obtiene un pago por su ID.
    /// </summary>
    public async Task<PagoDTO?> ObtenerPorIdAsync(int idPago)
    {
        var response = await _httpClient.GetAsync($"{Endpoint}/{idPago}");
        if (response.IsSuccessStatusCode)
            return await response.Content.ReadFromJsonAsync<PagoDTO>();
        return null;
    }

    /// <summary>
    /// POST /PagoRS — Registra un nuevo pago.
    /// </summary>
    public async Task<MensajeRespuesta> RegistrarAsync(PagoDTO pago)
    {
        var response = await _httpClient.PostAsJsonAsync(Endpoint, pago);
        if (response.IsSuccessStatusCode)
        {
            var result = await response.Content.ReadFromJsonAsync<MensajeRespuesta>();
            return result ?? new MensajeRespuesta { Mensaje = "Error al registrar pago." };
        }
        else
        {
            return new MensajeRespuesta { Mensaje = $"Error del servidor ({response.StatusCode}). Verifique el formato de los datos." };
        }
    }

    /// <summary>
    /// PUT /PagoRS/{id} — Actualiza los datos de un pago existente.
    /// </summary>
    public async Task<string> ActualizarAsync(int idPago, PagoDTO pago)
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
