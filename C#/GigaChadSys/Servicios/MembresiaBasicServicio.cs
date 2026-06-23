using System.Net.Http.Json;
using GigaChadSys.Servicios.DTO;

namespace GigaChadSys.Servicios;

/// <summary>
/// Servicio que consume el endpoint REST MembresiaBasicRS del backend Java.
/// Base URL: http://localhost:8080/GigaChadSys-REST/webresources/MembresiaBasicRS
/// </summary>
public class MembresiaBasicServicio
{
    private readonly HttpClient _httpClient;
    private const string Endpoint = "MembresiaBasicRS";

    public MembresiaBasicServicio(HttpClient httpClient)
    {
        _httpClient = httpClient;
    }

    /// <summary>
    /// GET /MembresiaBasicRS — Lista todas las membresías Basic.
    /// </summary>
    public async Task<List<MembresiaBasicDTO>> ListarMembresiasBasicAsync()
    {
        var membresias = await _httpClient.GetFromJsonAsync<List<MembresiaBasicDTO>>(Endpoint);
        return membresias ?? new List<MembresiaBasicDTO>();
    }

    /// <summary>
    /// GET /MembresiaBasicRS/{id} — Obtiene una membresía Basic por su ID.
    /// </summary>
    public async Task<MembresiaBasicDTO?> ObtenerPorIdAsync(int idMembresia)
    {
        var response = await _httpClient.GetAsync($"{Endpoint}/{idMembresia}");
        if (response.IsSuccessStatusCode)
            return await response.Content.ReadFromJsonAsync<MembresiaBasicDTO>();
        return null;
    }

    /// <summary>
    /// POST /MembresiaBasicRS — Registra una nueva membresía Basic.
    /// </summary>
    public async Task<string> RegistrarAsync(MembresiaBasicDTO membresia)
    {
        var response = await _httpClient.PostAsJsonAsync(Endpoint, membresia);
        var result = await response.Content.ReadFromJsonAsync<MensajeRespuesta>();
        return result?.Mensaje ?? "Error al registrar membresía Basic.";
    }

    /// <summary>
    /// PUT /MembresiaBasicRS/{id} — Actualiza los datos de una membresía Basic existente.
    /// </summary>
    public async Task<string> ActualizarAsync(int idMembresia, MembresiaBasicDTO membresia)
    {
        var response = await _httpClient.PutAsJsonAsync($"{Endpoint}/{idMembresia}", membresia);
        var result = await response.Content.ReadFromJsonAsync<MensajeRespuesta>();
        return result?.Mensaje ?? "Error al actualizar membresía Basic.";
    }

    /// <summary>
    /// DELETE /MembresiaBasicRS/{id} — Elimina (desactiva) una membresía Basic por su ID.
    /// </summary>
    public async Task<string> EliminarAsync(int idMembresia)
    {
        var response = await _httpClient.DeleteAsync($"{Endpoint}/{idMembresia}");
        var result = await response.Content.ReadFromJsonAsync<MensajeRespuesta>();
        return result?.Mensaje ?? "Error al eliminar membresía Basic.";
    }
}
