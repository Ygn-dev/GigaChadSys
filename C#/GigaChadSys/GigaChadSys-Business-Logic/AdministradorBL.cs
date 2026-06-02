using GigaChadSysDAO;
using GigaChadSysModel;

namespace GigaChadSysBL;

public class AdministradorBL : AdministradorIBL
{
    private readonly AdministradorDAO administradorDAO;

    public AdministradorBL()
    {
        administradorDAO = new AdministradorDAOImpl();
    }

    public List<Administrador> ListAll()
    {
        return administradorDAO.ListAll();
    }

    public Administrador? Load(int id)
    {
        if (id <= 0)
        {
            throw new Exception("El id del administrador no es válido.");
        }

        return administradorDAO.Load(id);
    }

    public Administrador Save(Administrador administrador)
    {
        if (administrador == null)
        {
            throw new Exception("El administrador no puede ser nulo.");
        }

        if (string.IsNullOrWhiteSpace(administrador.NombreCompleto))
        {
            throw new Exception("El nombre completo del administrador es obligatorio.");
        }

        if (string.IsNullOrWhiteSpace(administrador.Email))
        {
            throw new Exception("El correo del administrador es obligatorio.");
        }

        if (string.IsNullOrWhiteSpace(administrador.Contrasenia))
        {
            throw new Exception("La contraseña del administrador es obligatoria.");
        }

        return administradorDAO.Save(administrador);
    }

    public Administrador? Update(Administrador administrador)
    {
        if (administrador == null)
        {
            throw new Exception("El administrador no puede ser nulo.");
        }

        if (administrador.IdUsuario <= 0)
        {
            throw new Exception("El id del administrador no es válido.");
        }

        if (string.IsNullOrWhiteSpace(administrador.NombreCompleto))
        {
            throw new Exception("El nombre completo del administrador es obligatorio.");
        }

        if (string.IsNullOrWhiteSpace(administrador.Email))
        {
            throw new Exception("El correo del administrador es obligatorio.");
        }

        return administradorDAO.Update(administrador);
    }

    public void Remove(Administrador administrador)
    {
        if (administrador == null)
        {
            throw new Exception("El administrador no puede ser nulo.");
        }

        if (administrador.IdUsuario <= 0)
        {
            throw new Exception("El id del administrador no es válido.");
        }

        administradorDAO.Remove(administrador);
    }
}