using System.Net.Http.Json;
using GigaChadSys.Servicios.DTO;

namespace GigaChadSys.Servicios;

/// <summary>
/// Servicio que consume el endpoint REST AdministradorRS del backend Java.
/// Base URL: http://localhost:8080/GigaChadSys-REST/webresources/AdministradorRS
/// </summary>
public class AdministradorServicio
{
    private readonly HttpClient _httpClient;
    private const string Endpoint = "AdministradorRS";

    public AdministradorServicio(HttpClient httpClient)
    {
        _httpClient = httpClient;
    }

    /// <summary>
    /// GET /AdministradorRS — Lista todos los administradores.
    /// </summary>
    public async Task<List<AdministradorDTO>> ListarAdministradoresAsync()
    {
        var admins = await _httpClient.GetFromJsonAsync<List<AdministradorDTO>>(Endpoint);
        return admins ?? new List<AdministradorDTO>();
    }

    /// <summary>
    /// GET /AdministradorRS/{id} — Obtiene un administrador por su ID.
    /// </summary>
    public async Task<AdministradorDTO?> ObtenerPorIdAsync(int idUsuario)
    {
        var response = await _httpClient.GetAsync($"{Endpoint}/{idUsuario}");
        if (response.IsSuccessStatusCode)
            return await response.Content.ReadFromJsonAsync<AdministradorDTO>();
        return null;
    }

    /// <summary>
    /// POST /AdministradorRS — Registra un nuevo administrador.
    /// </summary>
    public async Task<string> RegistrarAsync(AdministradorDTO administrador)
    {
        var response = await _httpClient.PostAsJsonAsync(Endpoint, administrador);
        return await HttpUtils.LeerMensajeRespuestaAsync(response, "Error al registrar administrador.");
    }

    /// <summary>
    /// PUT /AdministradorRS/{id} — Actualiza los datos de un administrador existente.
    /// </summary>
    public async Task<string> ActualizarAsync(int idUsuario, AdministradorDTO administrador)
    {
        var response = await _httpClient.PutAsJsonAsync($"{Endpoint}/{idUsuario}", administrador);
        return await HttpUtils.LeerMensajeRespuestaAsync(response, "Error al actualizar administrador.");
    }

    /// <summary>
    /// DELETE /AdministradorRS/{id} — Elimina (desactiva) un administrador por su ID.
    /// </summary>
    public async Task<string> EliminarAsync(int idUsuario)
    {
        var response = await _httpClient.DeleteAsync($"{Endpoint}/{idUsuario}");
        return await HttpUtils.LeerMensajeRespuestaAsync(response, "Error al eliminar administrador.");
    }
}
