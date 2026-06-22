namespace GigaChadSys.Servicios.DTO;

/// <summary>
/// DTO para enviar las credenciales de login al servicio de autenticación.
/// </summary>
public class LoginRequestDTO
{
    public string Email { get; set; } = "";
    public string Contrasenia { get; set; } = "";
}
