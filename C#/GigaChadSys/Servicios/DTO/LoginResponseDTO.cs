namespace GigaChadSys.Servicios.DTO;

/// <summary>
/// DTO que representa el resultado de un intento de login.
/// </summary>
public class LoginResponseDTO
{
    /// <summary>Indica si el login fue exitoso.</summary>
    public bool Exito { get; set; }

    /// <summary>Mensaje descriptivo (error o bienvenida).</summary>
    public string Mensaje { get; set; } = "";

    /// <summary>Rol del usuario: "Socio", "Entrenador" o "Administrador".</summary>
    public string Rol { get; set; } = "";

    /// <summary>ID del usuario autenticado.</summary>
    public int IdUsuario { get; set; }

    /// <summary>Nombres del usuario autenticado.</summary>
    public string Nombres { get; set; } = "";
}
