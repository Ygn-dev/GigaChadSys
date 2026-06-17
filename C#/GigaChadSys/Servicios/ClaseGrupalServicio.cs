using System.Net.Http.Json;
using GigaChadSysModel;

namespace GigaChadSys.Servicios;

/// <summary>
/// Servicio que consume el endpoint REST ClaseGrupalRS del backend Java.
/// Base URL: http://localhost:8080/GigaChadSys-REST/webresources/ClaseGrupalRS
/// </summary>
public class ClaseGrupalServicio
{
    private readonly HttpClient _httpClient;
    private const string Endpoint = "ClaseGrupalRS";

    public ClaseGrupalServicio(HttpClient httpClient)
    {
        _httpClient = httpClient;
    }

    /// <summary>
    /// GET /ClaseGrupalRS — Lista todas las clases grupales.
    /// </summary>
    public async Task<List<ClaseGrupal>> ListarClasesGrupalesAsync()
    {
        var clases = await _httpClient.GetFromJsonAsync<List<ClaseGrupal>>(Endpoint);
        return clases ?? new List<ClaseGrupal>();
    }

    /// <summary>
    /// GET /ClaseGrupalRS/{id} — Obtiene una clase grupal por su ID.
    /// </summary>
    public async Task<ClaseGrupal?> ObtenerPorIdAsync(int idClase)
    {
        var response = await _httpClient.GetAsync($"{Endpoint}/{idClase}");
        if (response.IsSuccessStatusCode)
            return await response.Content.ReadFromJsonAsync<ClaseGrupal>();
        return null;
    }

    /// <summary>
    /// POST /ClaseGrupalRS — Registra una nueva clase grupal.
    /// </summary>
    public async Task<string> RegistrarAsync(ClaseGrupal claseGrupal)
    {
        var response = await _httpClient.PostAsJsonAsync(Endpoint, claseGrupal);
        var result = await response.Content.ReadFromJsonAsync<MensajeRespuesta>();
        return result?.Mensaje ?? "Error al registrar clase grupal.";
    }

    /// <summary>
    /// PUT /ClaseGrupalRS/{id} — Actualiza los datos de una clase grupal existente.
    /// </summary>
    public async Task<string> ActualizarAsync(int idClase, ClaseGrupal claseGrupal)
    {
        var response = await _httpClient.PutAsJsonAsync($"{Endpoint}/{idClase}", claseGrupal);
        var result = await response.Content.ReadFromJsonAsync<MensajeRespuesta>();
        return result?.Mensaje ?? "Error al actualizar clase grupal.";
    }

    /// <summary>
    /// DELETE /ClaseGrupalRS/{id} — Elimina (desactiva) una clase grupal por su ID.
    /// </summary>
    public async Task<string> EliminarAsync(int idClase)
    {
        var response = await _httpClient.DeleteAsync($"{Endpoint}/{idClase}");
        var result = await response.Content.ReadFromJsonAsync<MensajeRespuesta>();
        return result?.Mensaje ?? "Error al eliminar clase grupal.";
    }
}
