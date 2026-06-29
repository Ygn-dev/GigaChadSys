using System.Net.Http.Json;
using GigaChadSys.Servicios.DTO;

namespace GigaChadSys.Servicios;

/// <summary>
/// Servicio que consume el endpoint REST SocioRS del backend Java.
/// Base URL: http://localhost:8080/GigaChadSys-REST/webresources/SocioRS
///
/// Usa SocioDTO que mapea exactamente al JSON del backend Java.
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
    public async Task<List<SocioDTO>> ListarSociosAsync()
    {
        var socios = await _httpClient.GetFromJsonAsync<List<SocioDTO>>(Endpoint);
        return socios ?? new List<SocioDTO>();
    }

    /// <summary>
    /// GET /SocioRS/{id} — Obtiene un socio por su ID.
    /// </summary>
    public async Task<SocioDTO?> ObtenerPorIdAsync(int idUsuario)
    {
        var response = await _httpClient.GetAsync($"{Endpoint}/{idUsuario}");
        if (response.IsSuccessStatusCode)
            return await response.Content.ReadFromJsonAsync<SocioDTO>();
        return null;
    }

    /// <summary>
    /// POST /SocioRS — Registra un nuevo socio.
    /// </summary>
    public async Task<string> RegistrarAsync(SocioDTO socio)
    {
        var response = await _httpClient.PostAsJsonAsync(Endpoint, socio);
        return await HttpUtils.LeerMensajeRespuestaAsync(response, "Error al registrar socio.");
    }

    /// <summary>
    /// PUT /SocioRS/{id} — Actualiza los datos de un socio existente.
    /// </summary>
    public async Task<string> ActualizarAsync(int idUsuario, SocioDTO socio)
    {
        var response = await _httpClient.PutAsJsonAsync($"{Endpoint}/{idUsuario}", socio);
        return await HttpUtils.LeerMensajeRespuestaAsync(response, "Error al actualizar socio.");
    }

    /// <summary>
    /// DELETE /SocioRS/{id} — Elimina (desactiva) un socio por su ID.
    /// </summary>
    public async Task<string> EliminarAsync(int idUsuario)
    {
        var response = await _httpClient.DeleteAsync($"{Endpoint}/{idUsuario}");
        return await HttpUtils.LeerMensajeRespuestaAsync(response, "Error al eliminar socio.");
    }
}
