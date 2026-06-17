using System.Net.Http.Json;
using GigaChadSysModel;

namespace GigaChadSys.Servicios;

/// <summary>
/// Servicio que consume el endpoint REST SalonRS del backend Java.
/// Base URL: http://localhost:8080/GigaChadSys-REST/webresources/SalonRS
/// </summary>
public class SalonServicio
{
    private readonly HttpClient _httpClient;
    private const string Endpoint = "SalonRS";

    public SalonServicio(HttpClient httpClient)
    {
        _httpClient = httpClient;
    }

    /// <summary>
    /// GET /SalonRS — Lista todos los salones.
    /// </summary>
    public async Task<List<Salon>> ListarSalonesAsync()
    {
        var salones = await _httpClient.GetFromJsonAsync<List<Salon>>(Endpoint);
        return salones ?? new List<Salon>();
    }

    /// <summary>
    /// GET /SalonRS/{id} — Obtiene un salón por su ID.
    /// </summary>
    public async Task<Salon?> ObtenerPorIdAsync(int idSalon)
    {
        var response = await _httpClient.GetAsync($"{Endpoint}/{idSalon}");
        if (response.IsSuccessStatusCode)
            return await response.Content.ReadFromJsonAsync<Salon>();
        return null;
    }

    /// <summary>
    /// POST /SalonRS — Registra un nuevo salón.
    /// </summary>
    public async Task<string> RegistrarAsync(Salon salon)
    {
        var response = await _httpClient.PostAsJsonAsync(Endpoint, salon);
        var result = await response.Content.ReadFromJsonAsync<MensajeRespuesta>();
        return result?.Mensaje ?? "Error al registrar salón.";
    }

    /// <summary>
    /// PUT /SalonRS/{id} — Actualiza los datos de un salón existente.
    /// </summary>
    public async Task<string> ActualizarAsync(int idSalon, Salon salon)
    {
        var response = await _httpClient.PutAsJsonAsync($"{Endpoint}/{idSalon}", salon);
        var result = await response.Content.ReadFromJsonAsync<MensajeRespuesta>();
        return result?.Mensaje ?? "Error al actualizar salón.";
    }

    /// <summary>
    /// DELETE /SalonRS/{id} — Elimina (desactiva) un salón por su ID.
    /// </summary>
    public async Task<string> EliminarAsync(int idSalon)
    {
        var response = await _httpClient.DeleteAsync($"{Endpoint}/{idSalon}");
        var result = await response.Content.ReadFromJsonAsync<MensajeRespuesta>();
        return result?.Mensaje ?? "Error al eliminar salón.";
    }
}
