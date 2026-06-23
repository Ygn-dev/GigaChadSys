using System.Net.Http.Json;
using GigaChadSys.Servicios.DTO;

namespace GigaChadSys.Servicios;

/// <summary>
/// Servicio que consume el endpoint REST SesionClaseRS del backend Java.
/// Base URL: http://localhost:8080/GigaChadSys-REST/webresources/SesionClaseRS
/// </summary>
public class SesionClaseServicio
{
    private readonly HttpClient _httpClient;
    private const string Endpoint = "SesionClaseRS";

    public SesionClaseServicio(HttpClient httpClient)
    {
        _httpClient = httpClient;
    }

    /// <summary>
    /// GET /SesionClaseRS — Lista todas las sesiones de clase.
    /// </summary>
    public async Task<List<SesionClaseDTO>> ListarSesionesAsync()
    {
        var sesiones = await _httpClient.GetFromJsonAsync<List<SesionClaseDTO>>(Endpoint);
        return sesiones ?? new List<SesionClaseDTO>();
    }

    /// <summary>
    /// GET /SesionClaseRS/{id} — Obtiene una sesión de clase por su ID.
    /// </summary>
    public async Task<SesionClaseDTO?> ObtenerPorIdAsync(int idSesion)
    {
        var response = await _httpClient.GetAsync($"{Endpoint}/{idSesion}");
        if (response.IsSuccessStatusCode)
            return await response.Content.ReadFromJsonAsync<SesionClaseDTO>();
        return null;
    }

    /// <summary>
    /// POST /SesionClaseRS — Registra una nueva sesión de clase.
    /// </summary>
    public async Task<string> RegistrarAsync(SesionClaseDTO sesionClase)
    {
        var response = await _httpClient.PostAsJsonAsync(Endpoint, sesionClase);
        var result = await response.Content.ReadFromJsonAsync<MensajeRespuesta>();
        return result?.Mensaje ?? "Error al registrar sesión de clase.";
    }

    /// <summary>
    /// PUT /SesionClaseRS/{id} — Actualiza los datos de una sesión de clase existente.
    /// </summary>
    public async Task<string> ActualizarAsync(int idSesion, SesionClaseDTO sesionClase)
    {
        var response = await _httpClient.PutAsJsonAsync($"{Endpoint}/{idSesion}", sesionClase);
        var result = await response.Content.ReadFromJsonAsync<MensajeRespuesta>();
        return result?.Mensaje ?? "Error al actualizar sesión de clase.";
    }

    /// <summary>
    /// DELETE /SesionClaseRS/{id} — Elimina (desactiva) una sesión de clase por su ID.
    /// </summary>
    public async Task<string> EliminarAsync(int idSesion)
    {
        var response = await _httpClient.DeleteAsync($"{Endpoint}/{idSesion}");
        var result = await response.Content.ReadFromJsonAsync<MensajeRespuesta>();
        return result?.Mensaje ?? "Error al eliminar sesión de clase.";
    }
}
