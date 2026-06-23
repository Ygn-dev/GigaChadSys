using GigaChadSys.Servicios.DTO;

namespace GigaChadSys.Servicios;

/// <summary>
/// Servicio Scoped que mantiene el estado del usuario autenticado
/// durante la sesión de Blazor Server.
///
/// Uso:
///   1. En Login.razor: Session.IniciarSesion(loginResponse);
///   2. En cualquier página: @inject SessionServicio Session → Session.IdUsuario
///
/// Nota: Al ser Scoped en Blazor Server, el estado se pierde
/// si el usuario recarga la página (F5). Para persistencia, usar
/// ProtectedSessionStorage como mejora futura.
/// </summary>
public class SessionServicio
{
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
}
