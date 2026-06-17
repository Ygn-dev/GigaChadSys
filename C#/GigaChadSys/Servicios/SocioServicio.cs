using System.Net.Http.Json;
using GigaChadSysModel;

namespace GigaChadSys.Servicios;

/// <summary>
/// Servicio que consume el endpoint REST SocioRS del backend Java.
/// Base URL: http://localhost:8080/GigaChadSys-REST/webresources/SocioRS
/// </summary>
public class SocioServicio
{
    private readonly HttpClient _httpClient;
    private const string Endpoint = "SocioRS";

    public SocioServicio(HttpClient httpClient)
    {
        _httpClient = httpClient;
    }

    /// <summary>
    /// GET /SocioRS — Lista todos los socios.
    /// </summary>
    public async Task<List<Socio>> ListarSociosAsync()
    {
        var socios = await _httpClient.GetFromJsonAsync<List<Socio>>(Endpoint);
        return socios ?? new List<Socio>();
    }

    /// <summary>
    /// GET /SocioRS/{id} — Obtiene un socio por su ID.
    /// </summary>
    public async Task<Socio?> ObtenerPorIdAsync(int idUsuario)
    {
        var response = await _httpClient.GetAsync($"{Endpoint}/{idUsuario}");
        if (response.IsSuccessStatusCode)
            return await response.Content.ReadFromJsonAsync<Socio>();
        return null;
    }

    /// <summary>
    /// POST /SocioRS — Registra un nuevo socio.
    /// </summary>
    public async Task<string> RegistrarAsync(Socio socio)
    {
        var response = await _httpClient.PostAsJsonAsync(Endpoint, socio);
        var result = await response.Content.ReadFromJsonAsync<MensajeRespuesta>();
        return result?.Mensaje ?? "Error al registrar socio.";
    }

    /// <summary>
    /// PUT /SocioRS/{id} — Actualiza los datos de un socio existente.
    /// </summary>
    public async Task<string> ActualizarAsync(int idUsuario, Socio socio)
    {
        var response = await _httpClient.PutAsJsonAsync($"{Endpoint}/{idUsuario}", socio);
        var result = await response.Content.ReadFromJsonAsync<MensajeRespuesta>();
        return result?.Mensaje ?? "Error al actualizar socio.";
    }

    /// <summary>
    /// DELETE /SocioRS/{id} — Elimina (desactiva) un socio por su ID.
    /// </summary>
    public async Task<string> EliminarAsync(int idUsuario)
    {
        var response = await _httpClient.DeleteAsync($"{Endpoint}/{idUsuario}");
        var result = await response.Content.ReadFromJsonAsync<MensajeRespuesta>();
        return result?.Mensaje ?? "Error al eliminar socio.";
    }
}
