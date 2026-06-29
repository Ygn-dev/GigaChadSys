using System.Net.Http.Json;
using GigaChadSys.Servicios.DTO;

namespace GigaChadSys.Servicios;

/// <summary>
/// Servicio que consume el endpoint REST MetodoPagoRS del backend Java.
/// Base URL: http://localhost:8080/GigaChadSys-REST/webresources/MetodoPagoRS
/// </summary>
public class MetodoPagoServicio
{
    private readonly HttpClient _httpClient;
    private const string Endpoint = "MetodoPagoRS";

    public MetodoPagoServicio(HttpClient httpClient)
    {
        _httpClient = httpClient;
    }

    /// <summary>
    /// GET /MetodoPagoRS — Lista todos los métodos de pago.
    /// </summary>
    public async Task<List<MetodoPagoDTO>> ListarMetodosDePagoAsync()
    {
        var metodos = await _httpClient.GetFromJsonAsync<List<MetodoPagoDTO>>(Endpoint);
        return metodos ?? new List<MetodoPagoDTO>();
    }

    /// <summary>
    /// GET /MetodoPagoRS/{id} — Obtiene un método de pago por su ID.
    /// </summary>
    public async Task<MetodoPagoDTO?> ObtenerPorIdAsync(int idMetodoPago)
    {
        var response = await _httpClient.GetAsync($"{Endpoint}/{idMetodoPago}");
        if (response.IsSuccessStatusCode)
            return await response.Content.ReadFromJsonAsync<MetodoPagoDTO>();
        return null;
    }

    /// <summary>
    /// POST /MetodoPagoRS — Registra un nuevo método de pago.
    /// </summary>
    public async Task<string> RegistrarAsync(MetodoPagoDTO metodoPago)
    {
        var response = await _httpClient.PostAsJsonAsync(Endpoint, metodoPago);
        return await HttpUtils.LeerMensajeRespuestaAsync(response, "Error al registrar método de pago.");
    }

    /// <summary>
    /// PUT /MetodoPagoRS/{id} — Actualiza los datos de un método de pago existente.
    /// </summary>
    public async Task<string> ActualizarAsync(int idMetodoPago, MetodoPagoDTO metodoPago)
    {
        var response = await _httpClient.PutAsJsonAsync($"{Endpoint}/{idMetodoPago}", metodoPago);
        return await HttpUtils.LeerMensajeRespuestaAsync(response, "Error al actualizar método de pago.");
    }

    /// <summary>
    /// DELETE /MetodoPagoRS/{id} — Elimina (desactiva) un método de pago por su ID.
    /// </summary>
    public async Task<string> EliminarAsync(int idMetodoPago)
    {
        var response = await _httpClient.DeleteAsync($"{Endpoint}/{idMetodoPago}");
        return await HttpUtils.LeerMensajeRespuestaAsync(response, "Error al eliminar método de pago.");
    }
}
