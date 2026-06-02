using GigaChadSysDAO;
using GigaChadSysModel;

namespace GigaChadSysBL;

public class EntrenadorBL : EntrenadorIBL
{
    private readonly EntrenadorDAO entrenadorDAO;

    public EntrenadorBL()
    {
        entrenadorDAO = new EntrenadorDAOImpl();
    }

    public List<Entrenador> ListAll()
    {
        return entrenadorDAO.ListAll();
    }

    public Entrenador? Load(int id)
    {
        if (id <= 0)
        {
            throw new Exception("El id del entrenador no es válido.");
        }

        return entrenadorDAO.Load(id);
    }

    public Entrenador Save(Entrenador entrenador)
    {
        if (entrenador == null)
        {
            throw new Exception("El entrenador no puede ser nulo.");
        }

        if (string.IsNullOrWhiteSpace(entrenador.NombreCompleto))
        {
            throw new Exception("El nombre completo del entrenador es obligatorio.");
        }

        if (string.IsNullOrWhiteSpace(entrenador.Email))
        {
            throw new Exception("El correo del entrenador es obligatorio.");
        }

        if (string.IsNullOrWhiteSpace(entrenador.Contrasenia))
        {
            throw new Exception("La contraseña del entrenador es obligatoria.");
        }

        if (string.IsNullOrWhiteSpace(entrenador.Especialidad))
        {
            throw new Exception("La especialidad del entrenador es obligatoria.");
        }

        if (entrenador.Sueldo < 0)
        {
            throw new Exception("El sueldo del entrenador no puede ser negativo.");
        }

        return entrenadorDAO.Save(entrenador);
    }

    public Entrenador? Update(Entrenador entrenador)
    {
        if (entrenador == null)
        {
            throw new Exception("El entrenador no puede ser nulo.");
        }

        if (entrenador.IdUsuario <= 0)
        {
            throw new Exception("El id del entrenador no es válido.");
        }

        if (string.IsNullOrWhiteSpace(entrenador.NombreCompleto))
        {
            throw new Exception("El nombre completo del entrenador es obligatorio.");
        }

        if (string.IsNullOrWhiteSpace(entrenador.Email))
        {
            throw new Exception("El correo del entrenador es obligatorio.");
        }

        if (string.IsNullOrWhiteSpace(entrenador.Especialidad))
        {
            throw new Exception("La especialidad del entrenador es obligatoria.");
        }

        if (entrenador.Sueldo < 0)
        {
            throw new Exception("El sueldo del entrenador no puede ser negativo.");
        }

        return entrenadorDAO.Update(entrenador);
    }

    public void Remove(Entrenador entrenador)
    {
        if (entrenador == null)
        {
            throw new Exception("El entrenador no puede ser nulo.");
        }

        if (entrenador.IdUsuario <= 0)
        {
            throw new Exception("El id del entrenador no es válido.");
        }

        entrenadorDAO.Remove(entrenador);
    }
}