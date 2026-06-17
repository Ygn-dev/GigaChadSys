using System.Net.Http.Json;
using GigaChadSysModel;

namespace GigaChadSys.Servicios;

/// <summary>
/// Servicio que consume el endpoint REST SuscripcionRS del backend Java.
/// Base URL: http://localhost:8080/GigaChadSys-REST/webresources/SuscripcionRS
/// </summary>
public class SuscripcionServicio
{
    private readonly HttpClient _httpClient;
    private const string Endpoint = "SuscripcionRS";

    public SuscripcionServicio(HttpClient httpClient)
    {
        _httpClient = httpClient;
    }

    /// <summary>
    /// GET /SuscripcionRS — Lista todas las suscripciones.
    /// </summary>
    public async Task<List<Suscripcion>> ListarSuscripcionesAsync()
    {
        var suscripciones = await _httpClient.GetFromJsonAsync<List<Suscripcion>>(Endpoint);
        return suscripciones ?? new List<Suscripcion>();
    }

    /// <summary>
    /// GET /SuscripcionRS/{id} — Obtiene una suscripción por su ID.
    /// </summary>
    public async Task<Suscripcion?> ObtenerPorIdAsync(int idSuscripcion)
    {
        var response = await _httpClient.GetAsync($"{Endpoint}/{idSuscripcion}");
        if (response.IsSuccessStatusCode)
            return await response.Content.ReadFromJsonAsync<Suscripcion>();
        return null;
    }

    /// <summary>
    /// POST /SuscripcionRS — Registra una nueva suscripción (asocia socio con membresía).
    /// </summary>
    public async Task<string> RegistrarAsync(Suscripcion suscripcion)
    {
        var response = await _httpClient.PostAsJsonAsync(Endpoint, suscripcion);
        var result = await response.Content.ReadFromJsonAsync<MensajeRespuesta>();
        return result?.Mensaje ?? "Error al registrar suscripción.";
    }

    /// <summary>
    /// PUT /SuscripcionRS/{id} — Actualiza los datos de una suscripción existente.
    /// </summary>
    public async Task<string> ActualizarAsync(int idSuscripcion, Suscripcion suscripcion)
    {
        var response = await _httpClient.PutAsJsonAsync($"{Endpoint}/{idSuscripcion}", suscripcion);
        var result = await response.Content.ReadFromJsonAsync<MensajeRespuesta>();
        return result?.Mensaje ?? "Error al actualizar suscripción.";
    }

    /// <summary>
    /// DELETE /SuscripcionRS/{id} — Elimina (desactiva) una suscripción por su ID.
    /// </summary>
    public async Task<string> EliminarAsync(int idSuscripcion)
    {
        var response = await _httpClient.DeleteAsync($"{Endpoint}/{idSuscripcion}");
        var result = await response.Content.ReadFromJsonAsync<MensajeRespuesta>();
        return result?.Mensaje ?? "Error al eliminar suscripción.";
    }
}
