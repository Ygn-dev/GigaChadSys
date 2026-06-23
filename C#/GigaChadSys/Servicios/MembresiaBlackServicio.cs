using System.Net.Http.Json;
using GigaChadSys.Servicios.DTO;

namespace GigaChadSys.Servicios;

/// <summary>
/// Servicio que consume el endpoint REST MembresiaBlackRS del backend Java.
/// Base URL: http://localhost:8080/GigaChadSys-REST/webresources/MembresiaBlackRS
/// Java model: MembresiaBlack extends Membresia — idMembresia, nombre, activa, costoMantenimientoAnual, cantidadInvitadosPorMes
/// </summary>
public class MembresiaBlackServicio
{
    private readonly HttpClient _httpClient;
    private const string Endpoint = "MembresiaBlackRS";

    public MembresiaBlackServicio(HttpClient httpClient)
    {
        _httpClient = httpClient;
    }

    /// <summary>
    /// GET /MembresiaBlackRS — Lista todas las membresías Black.
    /// </summary>
    public async Task<List<MembresiaBlackDTO>> ListarMembresiasBlackAsync()
    {
        var membresias = await _httpClient.GetFromJsonAsync<List<MembresiaBlackDTO>>(Endpoint);
        return membresias ?? new List<MembresiaBlackDTO>();
    }

    /// <summary>
    /// GET /MembresiaBlackRS/{id} — Obtiene una membresía Black por su ID.
    /// </summary>
    public async Task<MembresiaBlackDTO?> ObtenerPorIdAsync(int idMembresia)
    {
        var response = await _httpClient.GetAsync($"{Endpoint}/{idMembresia}");
        if (response.IsSuccessStatusCode)
            return await response.Content.ReadFromJsonAsync<MembresiaBlackDTO>();
        return null;
    }

    /// <summary>
    /// POST /MembresiaBlackRS — Registra una nueva membresía Black.
    /// </summary>
    public async Task<string> RegistrarAsync(MembresiaBlackDTO membresia)
    {
        var response = await _httpClient.PostAsJsonAsync(Endpoint, membresia);
        var result = await response.Content.ReadFromJsonAsync<MensajeRespuesta>();
        return result?.Mensaje ?? "Error al registrar membresía Black.";
    }

    /// <summary>
    /// PUT /MembresiaBlackRS/{id} — Actualiza los datos de una membresía Black existente.
    /// </summary>
    public async Task<string> ActualizarAsync(int idMembresia, MembresiaBlackDTO membresia)
    {
        var response = await _httpClient.PutAsJsonAsync($"{Endpoint}/{idMembresia}", membresia);
        var result = await response.Content.ReadFromJsonAsync<MensajeRespuesta>();
        return result?.Mensaje ?? "Error al actualizar membresía Black.";
    }

    /// <summary>
    /// DELETE /MembresiaBlackRS/{id} — Elimina (desactiva) una membresía Black por su ID.
    /// </summary>
    public async Task<string> EliminarAsync(int idMembresia)
    {
        var response = await _httpClient.DeleteAsync($"{Endpoint}/{idMembresia}");
        var result = await response.Content.ReadFromJsonAsync<MensajeRespuesta>();
        return result?.Mensaje ?? "Error al eliminar membresía Black.";
    }
}
