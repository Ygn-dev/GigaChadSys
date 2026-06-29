using System.Net.Http.Json;
using GigaChadSys.Servicios.DTO;

namespace GigaChadSys.Servicios;

/// <summary>
/// Servicio que consume el endpoint REST EntrenadorRS del backend Java.
/// Base URL: http://localhost:8080/GigaChadSys-REST/webresources/EntrenadorRS
/// </summary>
public class EntrenadorServicio
{
    private readonly HttpClient _httpClient;
    private const string Endpoint = "EntrenadorRS";

    public EntrenadorServicio(HttpClient httpClient)
    {
        _httpClient = httpClient;
    }

    /// <summary>
    /// GET /EntrenadorRS — Lista todos los entrenadores.
    /// </summary>
    public async Task<List<EntrenadorDTO>> ListarEntrenadoresAsync()
    {
        var entrenadores = await _httpClient.GetFromJsonAsync<List<EntrenadorDTO>>(Endpoint);
        return entrenadores ?? new List<EntrenadorDTO>();
    }

    /// <summary>
    /// GET /EntrenadorRS/{id} — Obtiene un entrenador por su ID.
    /// </summary>
    public async Task<EntrenadorDTO?> ObtenerPorIdAsync(int idUsuario)
    {
        var response = await _httpClient.GetAsync($"{Endpoint}/{idUsuario}");
        if (response.IsSuccessStatusCode)
            return await response.Content.ReadFromJsonAsync<EntrenadorDTO>();
        return null;
    }

    /// <summary>
    /// POST /EntrenadorRS — Registra un nuevo entrenador.  
    /// </summary>
    public async Task<string> RegistrarAsync(EntrenadorDTO entrenador)
    {
        EncriptarPassword(entrenador);
        var response = await _httpClient.PostAsJsonAsync(Endpoint, entrenador);
        return await HttpUtils.LeerMensajeRespuestaAsync(response, "Error al registrar entrenador.");
    }

    /// <summary>
    /// PUT /EntrenadorRS/{id} — Actualiza los datos de un entrenador existente.
    /// </summary>
    public async Task<string> ActualizarAsync(int idUsuario, EntrenadorDTO entrenador)
    {
        EncriptarPassword(entrenador);
        var response = await _httpClient.PutAsJsonAsync($"{Endpoint}/{idUsuario}", entrenador);
        return await HttpUtils.LeerMensajeRespuestaAsync(response, "Error al actualizar entrenador.");
    }

    /// <summary>
    /// DELETE /EntrenadorRS/{id} — Elimina (desactiva) un entrenador por su ID.
    /// </summary>
    public async Task<string> EliminarAsync(int idUsuario)
    {
        var response = await _httpClient.DeleteAsync($"{Endpoint}/{idUsuario}");
        return await HttpUtils.LeerMensajeRespuestaAsync(response, "Error al eliminar entrenador.");
    }

    private static void EncriptarPassword(UsuarioDTO usuario)
    {
        if (!string.IsNullOrWhiteSpace(usuario.Contrasenia) &&
            !usuario.Contrasenia.StartsWith("$2a$") &&
            !usuario.Contrasenia.StartsWith("$2b$") &&
            !usuario.Contrasenia.StartsWith("$2x$") &&
            !usuario.Contrasenia.StartsWith("$2y$"))
        {
            usuario.Contrasenia = BCrypt.Net.BCrypt.HashPassword(usuario.Contrasenia);
        }
    }
}
