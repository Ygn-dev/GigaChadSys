using GigaChadSysDAO;
using GigaChadSysModel;

namespace GigaChadSysBL;

public class AuthBL : AuthIBL
{
    private readonly AdministradorDAO administradorDAO;
    private readonly SocioDAO socioDAO;
    private readonly EntrenadorDAO entrenadorDAO;

    public AuthBL()
    {
        administradorDAO = new AdministradorDAOImpl();
        socioDAO = new SocioDAOImpl();
        entrenadorDAO = new EntrenadorDAOImpl();
    }

    public Usuario? Login(string email, string contrasenia)
    {
        if (string.IsNullOrWhiteSpace(email))
        {
            throw new Exception("El correo es obligatorio.");
        }

        if (string.IsNullOrWhiteSpace(contrasenia))
        {
            throw new Exception("La contraseña es obligatoria.");
        }

        Administrador? administrador = administradorDAO.LoadByEmail(email);

        if (administrador != null && administrador.Contrasenia == contrasenia)
        {
            return administrador;
        }

        Socio? socio = socioDAO.LoadByEmail(email);

        if (socio != null && socio.Contrasenia == contrasenia)
        {
            return socio;
        }

        Entrenador? entrenador = entrenadorDAO.LoadByEmail(email);

        if (entrenador != null && entrenador.Contrasenia == contrasenia)
        {
            return entrenador;
        }

        return null;
    }
}