using GigaChadSysDAO;
using GigaChadSysModel;

namespace GigaChadSysBL;

public class SalonBL : SalonIBL
{
    private readonly SalonDAO salonDAO;

    public SalonBL()
    {
        salonDAO = new SalonDAOImpl();
    }

    public List<Salon> ListAll()
    {
        return salonDAO.ListAll();
    }

    public Salon? Load(int id)
    {
        if (id <= 0)
        {
            throw new Exception("El id del salón no es válido.");
        }

        return salonDAO.Load(id);
    }

    public Salon Save(Salon salon)
    {
        if (salon == null)
        {
            throw new Exception("El salón no puede ser nulo.");
        }

        if (string.IsNullOrWhiteSpace(salon.NombreSalon))
        {
            throw new Exception("El nombre del salón es obligatorio.");
        }

        if (salon.AforoMaximo <= 0)
        {
            throw new Exception("El aforo máximo debe ser mayor a cero.");
        }

        return salonDAO.Save(salon);
    }

    public Salon? Update(Salon salon)
    {
        if (salon == null)
        {
            throw new Exception("El salón no puede ser nulo.");
        }

        if (salon.IdSalon <= 0)
        {
            throw new Exception("El id del salón no es válido.");
        }

        if (string.IsNullOrWhiteSpace(salon.NombreSalon))
        {
            throw new Exception("El nombre del salón es obligatorio.");
        }

        if (salon.AforoMaximo <= 0)
        {
            throw new Exception("El aforo máximo debe ser mayor a cero.");
        }

        return salonDAO.Update(salon);
    }

    public void Remove(Salon salon)
    {
        if (salon == null)
        {
            throw new Exception("El salón no puede ser nulo.");
        }

        if (salon.IdSalon <= 0)
        {
            throw new Exception("El id del salón no es válido.");
        }

        salonDAO.Remove(salon);
    }
}