using GigaChadSysDAO;
using GigaChadSysModel;

namespace GigaChadSysBL;

public class MaquinaBL : MaquinaIBL
{
    private readonly MaquinaDAO maquinaDAO;

    public MaquinaBL()
    {
        maquinaDAO = new MaquinaDAOImpl();
    }

    public List<Maquina> ListAll()
    {
        return maquinaDAO.ListAll();
    }

    public Maquina? Load(int id)
    {
        if (id <= 0)
        {
            throw new Exception("El id de la máquina no es válido.");
        }

        return maquinaDAO.Load(id);
    }

    public Maquina Save(Maquina maquina)
    {
        if (maquina == null)
        {
            throw new Exception("La máquina no puede ser nula.");
        }

        if (string.IsNullOrWhiteSpace(maquina.Nombre))
        {
            throw new Exception("El nombre de la máquina es obligatorio.");
        }

        if (string.IsNullOrWhiteSpace(maquina.Estado))
        {
            maquina.Estado = "Operativa";
        }

        return maquinaDAO.Save(maquina);
    }

    public Maquina? Update(Maquina maquina)
    {
        if (maquina == null)
        {
            throw new Exception("La máquina no puede ser nula.");
        }

        if (maquina.IdMaquina <= 0)
        {
            throw new Exception("El id de la máquina no es válido.");
        }

        if (string.IsNullOrWhiteSpace(maquina.Nombre))
        {
            throw new Exception("El nombre de la máquina es obligatorio.");
        }

        if (string.IsNullOrWhiteSpace(maquina.Estado))
        {
            maquina.Estado = "Operativa";
        }

        return maquinaDAO.Update(maquina);
    }

    public void Remove(Maquina maquina)
    {
        if (maquina == null)
        {
            throw new Exception("La máquina no puede ser nula.");
        }

        if (maquina.IdMaquina <= 0)
        {
            throw new Exception("El id de la máquina no es válido.");
        }

        maquinaDAO.Remove(maquina);
    }
}