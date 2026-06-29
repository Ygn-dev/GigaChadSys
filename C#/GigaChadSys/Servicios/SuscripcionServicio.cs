using System.Net.Http.Json;
using System.Text.Json;
using GigaChadSys.Servicios.DTO;

namespace GigaChadSys.Servicios;

public class SuscripcionServicio
{
    private readonly HttpClient _httpClient;
    private const string Endpoint = "SuscripcionRS";

    public SuscripcionServicio(HttpClient httpClient)
    {
        _httpClient = httpClient;
    }

    public async Task<List<SuscripcionDTO>> ListarSuscripcionesAsync()
    {
        var suscripciones = await _httpClient.GetFromJsonAsync<List<SuscripcionDTO>>(Endpoint);
        return suscripciones ?? new List<SuscripcionDTO>();
    }

    public async Task<SuscripcionDTO?> ObtenerPorIdAsync(int idSuscripcion)
    {
        var response = await _httpClient.GetAsync($"{Endpoint}/{idSuscripcion}");

        if (response.IsSuccessStatusCode)
            return await response.Content.ReadFromJsonAsync<SuscripcionDTO>();

        return null;
    }

    public async Task<string> RegistrarAsync(SuscripcionDTO suscripcion)
    {
        var response = await _httpClient.PostAsJsonAsync(Endpoint, suscripcion);
        return await HttpUtils.LeerMensajeRespuestaAsync(response, "Error al registrar suscripción.");
    }

    public async Task<string> ActualizarAsync(int idSuscripcion, SuscripcionDTO suscripcion)
    {
        var response = await _httpClient.PutAsJsonAsync($"{Endpoint}/{idSuscripcion}", suscripcion);
        return await HttpUtils.LeerMensajeRespuestaAsync(response, "Error al actualizar suscripción.");
    }

    public async Task<string> EliminarAsync(int idSuscripcion)
    {
        var response = await _httpClient.DeleteAsync($"{Endpoint}/{idSuscripcion}");
        return await HttpUtils.LeerMensajeRespuestaAsync(response, "Error al eliminar suscripción.");
    }


}