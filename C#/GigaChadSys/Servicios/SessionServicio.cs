using GigaChadSys.Servicios.DTO;
using Microsoft.AspNetCore.Components.Server.ProtectedBrowserStorage;

namespace GigaChadSys.Servicios;

/// <summary>
/// Servicio Scoped que mantiene el estado del usuario autenticado
/// durante la sesión de Blazor Server.
///
/// Uso:
///   1. En Login.razor: Session.IniciarSesion(loginResponse);
///      await Session.GuardarEnStorageAsync(storage);
///   2. En cualquier Layout: OnAfterRenderAsync → await Session.RestaurarDesdeStorageAsync(storage);
///   3. En cualquier página: @inject SessionServicio Session → Session.IdUsuario
///
/// La sesión se persiste en ProtectedSessionStorage del navegador,
/// por lo que sobrevive a recargas de página (F5).
/// </summary>
public class SessionServicio
{
    private const string StorageKey = "GigaChadSys_Session";

    private LoginResponseDTO? _usuarioActual;

    // ── Eventos ────────────────────────────────────────────────────────────
    public event Action? OnCambio;

    // ── Estado ────────────────────────────────────────────────────────────
    public LoginResponseDTO? UsuarioActual => _usuarioActual;
    public bool EstaAutenticado => _usuarioActual != null;

    // ── Datos del usuario actual ──────────────────────────────────────────
    public int IdUsuario => _usuarioActual?.IdUsuario ?? 0;
    public string Nombres => _usuarioActual?.Nombres ?? "";
    public string Rol => _usuarioActual?.Rol ?? "";

    public bool EsAdmin => Rol?.ToLower() == "administrador";
    public bool EsSocio => Rol?.ToLower() == "socio";
    public bool EsEntrenador => Rol?.ToLower() == "entrenador";

    // ── Métodos ──────────────────────────────────────────────────────────
    /// <summary>
    /// Inicia sesión guardando el DTO de respuesta del login.
    /// Llamar desde Login.razor después de un login exitoso.
    /// </summary>
    public void IniciarSesion(LoginResponseDTO usuario)
    {
        _usuarioActual = usuario;
        OnCambio?.Invoke();
    }

    /// <summary>
    /// Cierra la sesión limpiando el estado.
    /// </summary>
    public void CerrarSesion()
    {
        _usuarioActual = null;
        OnCambio?.Invoke();
    }

    // ── Persistencia en ProtectedSessionStorage ──────────────────────────

    /// <summary>
    /// Guarda la sesión actual en el almacenamiento protegido del navegador.
    /// Llamar después de IniciarSesion en Login.razor.
    /// </summary>
    public async Task GuardarEnStorageAsync(ProtectedSessionStorage storage)
    {
        if (_usuarioActual != null)
        {
            await storage.SetAsync(StorageKey, _usuarioActual);
        }
    }

    /// <summary>
    /// Restaura la sesión desde el almacenamiento protegido del navegador.
    /// Llamar en OnAfterRenderAsync(firstRender: true) de cada Layout.
    /// </summary>
    public async Task RestaurarDesdeStorageAsync(ProtectedSessionStorage storage)
    {
        if (_usuarioActual != null) return; // Ya hay sesión en memoria

        try
        {
            var result = await storage.GetAsync<LoginResponseDTO>(StorageKey);
            if (result.Success && result.Value != null)
            {
                _usuarioActual = result.Value;
                OnCambio?.Invoke();
            }
        }
        catch
        {
            // Si falla la lectura (datos corruptos, etc.) se ignora silenciosamente
        }
    }

    /// <summary>
    /// Limpia la sesión del almacenamiento protegido del navegador.
    /// Llamar al cerrar sesión.
    /// </summary>
    public async Task LimpiarStorageAsync(ProtectedSessionStorage storage)
    {
        await storage.DeleteAsync(StorageKey);
    }
}
