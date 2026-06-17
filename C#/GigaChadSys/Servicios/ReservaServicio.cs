using System.Net.Http.Json;
using GigaChadSysModel;

namespace GigaChadSys.Servicios;

/// <summary>
/// Servicio que consume el endpoint REST ReservaRS del backend Java.
/// Base URL: http://localhost:8080/GigaChadSys-REST/webresources/ReservaRS
/// </summary>
public class ReservaServicio
{
    private readonly HttpClient _httpClient;
    private const string Endpoint = "ReservaRS";

    public ReservaServicio(HttpClient httpClient)
    {
        _httpClient = httpClient;
    }

    /// <summary>
    /// GET /ReservaRS — Lista todas las reservas.
    /// </summary>
    public async Task<List<Reserva>> ListarReservasAsync()
    {
        var reservas = await _httpClient.GetFromJsonAsync<List<Reserva>>(Endpoint);
        return reservas ?? new List<Reserva>();
    }

    /// <summary>
    /// GET /ReservaRS/{id} — Obtiene una reserva por su ID.
    /// </summary>
    public async Task<Reserva?> ObtenerPorIdAsync(int idReserva)
    {
        var response = await _httpClient.GetAsync($"{Endpoint}/{idReserva}");
        if (response.IsSuccessStatusCode)
            return await response.Content.ReadFromJsonAsync<Reserva>();
        return null;
    }

    /// <summary>
    /// POST /ReservaRS — Registra una nueva reserva.
    /// </summary>
    public async Task<string> RegistrarAsync(Reserva reserva)
    {
        var response = await _httpClient.PostAsJsonAsync(Endpoint, reserva);
        var result = await response.Content.ReadFromJsonAsync<MensajeRespuesta>();
        return result?.Mensaje ?? "Error al registrar reserva.";
    }

    /// <summary>
    /// PUT /ReservaRS/{id} — Actualiza los datos de una reserva existente.
    /// </summary>
    public async Task<string> ActualizarAsync(int idReserva, Reserva reserva)
    {
        var response = await _httpClient.PutAsJsonAsync($"{Endpoint}/{idReserva}", reserva);
        var result = await response.Content.ReadFromJsonAsync<MensajeRespuesta>();
        return result?.Mensaje ?? "Error al actualizar reserva.";
    }

    /// <summary>
    /// DELETE /ReservaRS/{id} — Elimina (desactiva) una reserva por su ID.
    /// </summary>
    public async Task<string> EliminarAsync(int idReserva)
    {
        var response = await _httpClient.DeleteAsync($"{Endpoint}/{idReserva}");
        var result = await response.Content.ReadFromJsonAsync<MensajeRespuesta>();
        return result?.Mensaje ?? "Error al eliminar reserva.";
    }
}
