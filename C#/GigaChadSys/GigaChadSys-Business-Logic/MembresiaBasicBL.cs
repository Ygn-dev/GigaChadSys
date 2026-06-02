using GigaChadSysDAO;
using GigaChadSysModel;

namespace GigaChadSysBL;

public class MembresiaBasicBL : MembresiaBasicIBL
{
    private readonly MembresiaBasicDAO membresiaBasicDAO;

    public MembresiaBasicBL()
    {
        membresiaBasicDAO = new MembresiaBasicDAOImpl();
    }

    public List<MembresiaBasic> ListAll()
    {
        return membresiaBasicDAO.ListAll();
    }

    public MembresiaBasic? Load(int id)
    {
        if (id <= 0)
        {
            throw new Exception("El id de la membresía basic no es válido.");
        }

        return membresiaBasicDAO.Load(id);
    }

    public MembresiaBasic Save(MembresiaBasic membresiaBasic)
    {
        if (membresiaBasic == null)
        {
            throw new Exception("La membresía basic no puede ser nula.");
        }

        if (string.IsNullOrWhiteSpace(membresiaBasic.NombrePlan))
        {
            membresiaBasic.NombrePlan = "Basic";
        }

        if (membresiaBasic.CostoMantenimientoMensual <= 0)
        {
            throw new Exception("El costo mensual de la membresía basic debe ser mayor a cero.");
        }

        return membresiaBasicDAO.Save(membresiaBasic);
    }

    public MembresiaBasic? Update(MembresiaBasic membresiaBasic)
    {
        if (membresiaBasic == null)
        {
            throw new Exception("La membresía basic no puede ser nula.");
        }

        if (membresiaBasic.MembresiaId <= 0)
        {
            throw new Exception("El id de la membresía basic no es válido.");
        }

        if (string.IsNullOrWhiteSpace(membresiaBasic.NombrePlan))
        {
            membresiaBasic.NombrePlan = "Basic";
        }

        if (membresiaBasic.CostoMantenimientoMensual <= 0)
        {
            throw new Exception("El costo mensual de la membresía basic debe ser mayor a cero.");
        }

        return membresiaBasicDAO.Update(membresiaBasic);
    }

    public void Remove(MembresiaBasic membresiaBasic)
    {
        if (membresiaBasic == null)
        {
            throw new Exception("La membresía basic no puede ser nula.");
        }

        if (membresiaBasic.MembresiaId <= 0)
        {
            throw new Exception("El id de la membresía basic no es válido.");
        }

        membresiaBasicDAO.Remove(membresiaBasic);
    }
}