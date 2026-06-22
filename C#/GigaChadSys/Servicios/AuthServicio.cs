using System.Net.Http.Json;
using GigaChadSys.Servicios.DTO;

namespace GigaChadSys.Servicios;

/// <summary>
/// Servicio de autenticación que busca usuarios en los endpoints REST existentes
/// (SocioRS, EntrenadorRS, AdministradorRS) para validar credenciales.
///
/// Flujo: se listan los usuarios de cada tipo y se busca coincidencia de email + contraseña.
/// </summary>
public class AuthServicio
{
    private readonly HttpClient _httpClient;

    public AuthServicio(HttpClient httpClient)
    {
        _httpClient = httpClient;
    }

    /// <summary>
    /// Intenta autenticar un usuario buscando en los 3 endpoints de usuarios.
    /// Retorna un LoginResponseDTO con el resultado.
    /// </summary>
    public async Task<LoginResponseDTO> LoginAsync(string email, string contrasenia)
    {
        if (string.IsNullOrWhiteSpace(email))
            return Fallo("El correo es obligatorio.");

        if (string.IsNullOrWhiteSpace(contrasenia))
            return Fallo("La contraseña es obligatoria.");

        try
        {
            // 1. Buscar en Administradores
            var admins = await _httpClient.GetFromJsonAsync<List<AdministradorDTO>>("AdministradorRS");
            if (admins != null)
            {
                var admin = admins.FirstOrDefault(a =>
                    a.Email.Equals(email, StringComparison.OrdinalIgnoreCase) &&
                    a.Contrasenia == contrasenia &&
                    a.Activo);

                if (admin != null)
                    return Exitoso(admin, "Administrador");
            }

            // 2. Buscar en Entrenadores
            var entrenadores = await _httpClient.GetFromJsonAsync<List<EntrenadorDTO>>("EntrenadorRS");
            if (entrenadores != null)
            {
                var entrenador = entrenadores.FirstOrDefault(e =>
                    e.Email.Equals(email, StringComparison.OrdinalIgnoreCase) &&
                    e.Contrasenia == contrasenia &&
                    e.Activo);

                if (entrenador != null)
                    return Exitoso(entrenador, "Entrenador");
            }

            // 3. Buscar en Socios
            var socios = await _httpClient.GetFromJsonAsync<List<SocioDTO>>("SocioRS");
            if (socios != null)
            {
                var socio = socios.FirstOrDefault(s =>
                    s.Email.Equals(email, StringComparison.OrdinalIgnoreCase) &&
                    s.Contrasenia == contrasenia &&
                    s.Activo);

                if (socio != null)
                    return Exitoso(socio, "Socio");
            }

            return Fallo("Correo o contraseña incorrectos.");
        }
        catch (HttpRequestException)
        {
            return Fallo("No se pudo conectar con el servidor. Verifica que el backend esté activo.");
        }
        catch (Exception ex)
        {
            return Fallo($"Error inesperado: {ex.Message}");
        }
    }

    private static LoginResponseDTO Exitoso(UsuarioDTO usuario, string rol) => new()
    {
        Exito = true,
        Mensaje = $"Bienvenido, {usuario.Nombres}",
        Rol = rol,
        IdUsuario = usuario.IdUsuario,
        Nombres = usuario.Nombres
    };

    private static LoginResponseDTO Fallo(string mensaje) => new()
    {
        Exito = false,
        Mensaje = mensaje
    };
}
