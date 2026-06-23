using System.Net.Http.Json;
using GigaChadSys.Servicios.DTO;

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
    public async Task<List<SalonDTO>> ListarSalonesAsync()
    {
        var salones = await _httpClient.GetFromJsonAsync<List<SalonDTO>>(Endpoint);
        return salones ?? new List<SalonDTO>();
    }

    /// <summary>
    /// GET /SalonRS/{id} — Obtiene un salón por su ID.
    /// </summary>
    public async Task<SalonDTO?> ObtenerPorIdAsync(int idSalon)
    {
        var response = await _httpClient.GetAsync($"{Endpoint}/{idSalon}");
        if (response.IsSuccessStatusCode)
            return await response.Content.ReadFromJsonAsync<SalonDTO>();
        return null;
    }

    /// <summary>
    /// POST /SalonRS — Registra un nuevo salón.
    /// </summary>
    public async Task<string> RegistrarAsync(SalonDTO salon)
    {
        var response = await _httpClient.PostAsJsonAsync(Endpoint, salon);
        var result = await response.Content.ReadFromJsonAsync<MensajeRespuesta>();
        return result?.Mensaje ?? "Error al registrar salón.";
    }

    /// <summary>
    /// PUT /SalonRS/{id} — Actualiza los datos de un salón existente.
    /// </summary>
    public async Task<string> ActualizarAsync(int idSalon, SalonDTO salon)
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
