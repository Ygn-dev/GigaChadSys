using System.Text.Json;
using GigaChadSys.Servicios.DTO;

namespace GigaChadSys.Servicios;

public static class HttpUtils
{
    public static async Task<string> LeerMensajeRespuestaAsync(HttpResponseMessage response, string mensajeDefault)
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
