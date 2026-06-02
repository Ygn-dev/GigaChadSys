using GigaChadSysDAO;
using GigaChadSysModel;

namespace GigaChadSysBL;

public class ClaseGrupalBL : ClaseGrupalIBL
{
    private readonly ClaseGrupalDAO claseGrupalDAO;

    public ClaseGrupalBL()
    {
        claseGrupalDAO = new ClaseGrupalDAOImpl();
    }

    public List<ClaseGrupal> ListAll()
    {
        return claseGrupalDAO.ListAll();
    }

    public ClaseGrupal? Load(int id)
    {
        if (id <= 0)
        {
            throw new Exception("El id de la clase grupal no es válido.");
        }

        return claseGrupalDAO.Load(id);
    }

    public ClaseGrupal Save(ClaseGrupal claseGrupal)
    {
        if (claseGrupal == null)
        {
            throw new Exception("La clase grupal no puede ser nula.");
        }

        if (string.IsNullOrWhiteSpace(claseGrupal.NombreDisciplina))
        {
            throw new Exception("El nombre de la disciplina es obligatorio.");
        }

        if (claseGrupal.DuracionMinutos <= 0)
        {
            throw new Exception("La duración de la clase debe ser mayor a cero.");
        }

        return claseGrupalDAO.Save(claseGrupal);
    }

    public ClaseGrupal? Update(ClaseGrupal claseGrupal)
    {
        if (claseGrupal == null)
        {
            throw new Exception("La clase grupal no puede ser nula.");
        }

        if (claseGrupal.IdClase <= 0)
        {
            throw new Exception("El id de la clase grupal no es válido.");
        }

        if (string.IsNullOrWhiteSpace(claseGrupal.NombreDisciplina))
        {
            throw new Exception("El nombre de la disciplina es obligatorio.");
        }

        if (claseGrupal.DuracionMinutos <= 0)
        {
            throw new Exception("La duración de la clase debe ser mayor a cero.");
        }

        return claseGrupalDAO.Update(claseGrupal);
    }

    public void Remove(ClaseGrupal claseGrupal)
    {
        if (claseGrupal == null)
        {
            throw new Exception("La clase grupal no puede ser nula.");
        }

        if (claseGrupal.IdClase <= 0)
        {
            throw new Exception("El id de la clase grupal no es válido.");
        }

        claseGrupalDAO.Remove(claseGrupal);
    }
}