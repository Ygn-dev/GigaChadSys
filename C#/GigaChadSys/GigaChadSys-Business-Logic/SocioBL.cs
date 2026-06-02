using GigaChadSysDAO;
using GigaChadSysModel;

namespace GigaChadSysBL;

public class SocioBL : SocioIBL
{
    private readonly SocioDAO socioDAO;

    public SocioBL()
    {
        socioDAO = new SocioDAOImpl();
    }

    public List<Socio> ListAll()
    {
        return socioDAO.ListAll();
    }

    public Socio? Load(int id)
    {
        if (id <= 0)
        {
            throw new Exception("El id del socio no es válido.");
        }

        return socioDAO.Load(id);
    }

    public Socio? LoadByEmail(string email)
    {
        if (string.IsNullOrWhiteSpace(email))
        {
            throw new Exception("El correo del socio es obligatorio.");
        }

        return socioDAO.LoadByEmail(email);
    }

    public Socio Save(Socio socio)
    {
        if (socio == null)
        {
            throw new Exception("El socio no puede ser nulo.");
        }

        if (string.IsNullOrWhiteSpace(socio.NombreCompleto))
        {
            throw new Exception("El nombre completo del socio es obligatorio.");
        }

        if (string.IsNullOrWhiteSpace(socio.Email))
        {
            throw new Exception("El correo del socio es obligatorio.");
        }

        if (string.IsNullOrWhiteSpace(socio.Contrasenia))
        {
            throw new Exception("La contraseña del socio es obligatoria.");
        }

        if (!string.IsNullOrWhiteSpace(socio.Dni) && socio.Dni.Length != 8)
        {
            throw new Exception("El DNI debe tener 8 dígitos.");
        }

        if (!string.IsNullOrWhiteSpace(socio.Telefono) && socio.Telefono.Length != 9)
        {
            throw new Exception("El teléfono debe tener 9 dígitos.");
        }

        socio.EstadoMembresia = false;
        socio.Activo = true;

        return socioDAO.Save(socio);
    }

    public Socio? Update(Socio socio)
    {
        if (socio == null)
        {
            throw new Exception("El socio no puede ser nulo.");
        }

        if (socio.IdUsuario <= 0)
        {
            throw new Exception("El id del socio no es válido.");
        }

        if (string.IsNullOrWhiteSpace(socio.NombreCompleto))
        {
            throw new Exception("El nombre completo del socio es obligatorio.");
        }

        if (string.IsNullOrWhiteSpace(socio.Email))
        {
            throw new Exception("El correo del socio es obligatorio.");
        }

        if (!string.IsNullOrWhiteSpace(socio.Dni) && socio.Dni.Length != 8)
        {
            throw new Exception("El DNI debe tener 8 dígitos.");
        }

        if (!string.IsNullOrWhiteSpace(socio.Telefono) && socio.Telefono.Length != 9)
        {
            throw new Exception("El teléfono debe tener 9 dígitos.");
        }

        return socioDAO.Update(socio);
    }

    public void Remove(Socio socio)
    {
        if (socio == null)
        {
            throw new Exception("El socio no puede ser nulo.");
        }

        if (socio.IdUsuario <= 0)
        {
            throw new Exception("El id del socio no es válido.");
        }

        socioDAO.Remove(socio);
    }
}