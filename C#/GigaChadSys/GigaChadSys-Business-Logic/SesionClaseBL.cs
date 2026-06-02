using GigaChadSysDAO;
using GigaChadSysModel;

namespace GigaChadSysBL;

public class SesionClaseBL : SesionClaseIBL
{
    private readonly SesionClaseDAO sesionClaseDAO;

    public SesionClaseBL()
    {
        sesionClaseDAO = new SesionClaseDAOImpl();
    }

    public List<SesionClase> ListAll()
    {
        return sesionClaseDAO.ListAll();
    }

    public List<SesionClase> ListByEntrenador(int idEntrenador)
    {
        if (idEntrenador <= 0)
        {
            throw new Exception("El id del entrenador no es válido.");
        }

        return sesionClaseDAO.ListByEntrenador(idEntrenador);
    }

    public SesionClase? Load(int id)
    {
        if (id <= 0)
        {
            throw new Exception("El id de la sesión no es válido.");
        }

        return sesionClaseDAO.Load(id);
    }

    public SesionClase Save(SesionClase sesionClase)
    {
        if (sesionClase == null)
        {
            throw new Exception("La sesión de clase no puede ser nula.");
        }

        if (sesionClase.IdSalon <= 0)
        {
            throw new Exception("Debe seleccionar un salón válido.");
        }

        if (sesionClase.IdEntrenador <= 0)
        {
            throw new Exception("Debe seleccionar un entrenador válido.");
        }

        if (sesionClase.IdClase <= 0)
        {
            throw new Exception("Debe seleccionar una clase grupal válida.");
        }

        if (sesionClase.FechaSesion == default)
        {
            throw new Exception("La fecha de la sesión es obligatoria.");
        }

        if (sesionClase.HoraInicio == default)
        {
            throw new Exception("La hora de inicio es obligatoria.");
        }

        if (sesionClase.HoraFin == default)
        {
            throw new Exception("La hora de fin es obligatoria.");
        }

        if (sesionClase.HoraFin <= sesionClase.HoraInicio)
        {
            throw new Exception("La hora de fin debe ser mayor a la hora de inicio.");
        }

        if (sesionClase.CuposDisponibles <= 0)
        {
            throw new Exception("Los cupos disponibles deben ser mayores a cero.");
        }

        return sesionClaseDAO.Save(sesionClase);
    }

    public SesionClase? Update(SesionClase sesionClase)
    {
        if (sesionClase == null)
        {
            throw new Exception("La sesión de clase no puede ser nula.");
        }

        if (sesionClase.IdSesion <= 0)
        {
            throw new Exception("El id de la sesión no es válido.");
        }

        if (sesionClase.IdSalon <= 0)
        {
            throw new Exception("Debe seleccionar un salón válido.");
        }

        if (sesionClase.IdEntrenador <= 0)
        {
            throw new Exception("Debe seleccionar un entrenador válido.");
        }

        if (sesionClase.IdClase <= 0)
        {
            throw new Exception("Debe seleccionar una clase grupal válida.");
        }

        if (sesionClase.HoraFin <= sesionClase.HoraInicio)
        {
            throw new Exception("La hora de fin debe ser mayor a la hora de inicio.");
        }

        if (sesionClase.CuposDisponibles < 0)
        {
            throw new Exception("Los cupos disponibles no pueden ser negativos.");
        }

        return sesionClaseDAO.Update(sesionClase);
    }

    public void Remove(SesionClase sesionClase)
    {
        if (sesionClase == null)
        {
            throw new Exception("La sesión de clase no puede ser nula.");
        }

        if (sesionClase.IdSesion <= 0)
        {
            throw new Exception("El id de la sesión no es válido.");
        }

        sesionClaseDAO.Remove(sesionClase);
    }
}