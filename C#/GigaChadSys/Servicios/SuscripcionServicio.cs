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
        return await LeerMensajeRespuesta(response, "Error al registrar suscripción.");
    }

    public async Task<string> ActualizarAsync(int idSuscripcion, SuscripcionDTO suscripcion)
    {
        var response = await _httpClient.PutAsJsonAsync($"{Endpoint}/{idSuscripcion}", suscripcion);
        return await LeerMensajeRespuesta(response, "Error al actualizar suscripción.");
    }

    public async Task<string> EliminarAsync(int idSuscripcion)
    {
        var response = await _httpClient.DeleteAsync($"{Endpoint}/{idSuscripcion}");
        return await LeerMensajeRespuesta(response, "Error al eliminar suscripción.");
    }

    private static async Task<string> LeerMensajeRespuesta(HttpResponseMessage response, string mensajeDefault)
    {
        string contenido = await response.Content.ReadAsStringAsync();

        if (string.IsNullOrWhiteSpace(contenido))
        {
            return response.IsSuccessStatusCode
                ? "Operación realizada correctamente."
                : $"{mensajeDefault} Código HTTP: {(int)response.StatusCode}";
        }

        try
        {
            var result = JsonSerializer.Deserialize<MensajeRespuesta>(
                contenido,
                new JsonSerializerOptions
                {
                    PropertyNameCaseInsensitive = true
                }
            );

            if (!string.IsNullOrWhiteSpace(result?.Mensaje))
                return result.Mensaje;
        }
        catch
        {
            string resumen = contenido.Length > 500
                ? contenido[..500]
                : contenido;

            return $"{mensajeDefault} El servidor devolvió una respuesta no JSON. HTTP {(int)response.StatusCode}: {resumen}";
        }

        return response.IsSuccessStatusCode
            ? "Operación realizada correctamente."
            : $"{mensajeDefault} Código HTTP: {(int)response.StatusCode}";
    }
}