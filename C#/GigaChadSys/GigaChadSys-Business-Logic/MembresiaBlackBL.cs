using GigaChadSysDAO;
using GigaChadSysModel;

namespace GigaChadSysBL;

public class MembresiaBlackBL : MembresiaBlackIBL
{
    private readonly MembresiaBlackDAO membresiaBlackDAO;

    public MembresiaBlackBL()
    {
        membresiaBlackDAO = new MembresiaBlackDAOImpl();
    }

    public List<MembresiaBlack> ListAll()
    {
        return membresiaBlackDAO.ListAll();
    }

    public MembresiaBlack? Load(int id)
    {
        if (id <= 0)
        {
            throw new Exception("El id de la membresía black no es válido.");
        }

        return membresiaBlackDAO.Load(id);
    }

    public MembresiaBlack Save(MembresiaBlack membresiaBlack)
    {
        if (membresiaBlack == null)
        {
            throw new Exception("La membresía black no puede ser nula.");
        }

        if (string.IsNullOrWhiteSpace(membresiaBlack.NombrePlan))
        {
            membresiaBlack.NombrePlan = "Black";
        }

        if (membresiaBlack.CostoMantenimientoAnual <= 0)
        {
            throw new Exception("El costo anual de la membresía black debe ser mayor a cero.");
        }

        if (membresiaBlack.CantidadInvitadosPorMes < 0)
        {
            throw new Exception("La cantidad de invitados no puede ser negativa.");
        }

        return membresiaBlackDAO.Save(membresiaBlack);
    }

    public MembresiaBlack? Update(MembresiaBlack membresiaBlack)
    {
        if (membresiaBlack == null)
        {
            throw new Exception("La membresía black no puede ser nula.");
        }

        if (membresiaBlack.MembresiaId <= 0)
        {
            throw new Exception("El id de la membresía black no es válido.");
        }

        if (string.IsNullOrWhiteSpace(membresiaBlack.NombrePlan))
        {
            membresiaBlack.NombrePlan = "Black";
        }

        if (membresiaBlack.CostoMantenimientoAnual <= 0)
        {
            throw new Exception("El costo anual de la membresía black debe ser mayor a cero.");
        }

        if (membresiaBlack.CantidadInvitadosPorMes < 0)
        {
            throw new Exception("La cantidad de invitados no puede ser negativa.");
        }

        return membresiaBlackDAO.Update(membresiaBlack);
    }

    public void Remove(MembresiaBlack membresiaBlack)
    {
        if (membresiaBlack == null)
        {
            throw new Exception("La membresía black no puede ser nula.");
        }

        if (membresiaBlack.MembresiaId <= 0)
        {
            throw new Exception("El id de la membresía black no es válido.");
        }

        membresiaBlackDAO.Remove(membresiaBlack);
    }
}