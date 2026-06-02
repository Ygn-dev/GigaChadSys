using GigaChadSysDAO;
using GigaChadSysModel;

namespace GigaChadSysBL;

public class MetodoPagoBL : MetodoPagoIBL
{
    private readonly MetodoPagoDAO metodoPagoDAO;

    public MetodoPagoBL()
    {
        metodoPagoDAO = new MetodoPagoDAOImpl();
    }

    public List<MetodoPago> ListAll()
    {
        return metodoPagoDAO.ListAll();
    }

    public MetodoPago? Load(int id)
    {
        if (id <= 0)
        {
            throw new Exception("El id del método de pago no es válido.");
        }

        return metodoPagoDAO.Load(id);
    }

    public MetodoPago Save(MetodoPago metodoPago)
    {
        if (metodoPago == null)
        {
            throw new Exception("El método de pago no puede ser nulo.");
        }

        if (string.IsNullOrWhiteSpace(metodoPago.Tipo))
        {
            throw new Exception("El tipo de método de pago es obligatorio.");
        }

        return metodoPagoDAO.Save(metodoPago);
    }

    public MetodoPago? Update(MetodoPago metodoPago)
    {
        if (metodoPago == null)
        {
            throw new Exception("El método de pago no puede ser nulo.");
        }

        if (metodoPago.IdMetodoPago <= 0)
        {
            throw new Exception("El id del método de pago no es válido.");
        }

        if (string.IsNullOrWhiteSpace(metodoPago.Tipo))
        {
            throw new Exception("El tipo de método de pago es obligatorio.");
        }

        return metodoPagoDAO.Update(metodoPago);
    }

    public void Remove(MetodoPago metodoPago)
    {
        if (metodoPago == null)
        {
            throw new Exception("El método de pago no puede ser nulo.");
        }

        if (metodoPago.IdMetodoPago <= 0)
        {
            throw new Exception("El id del método de pago no es válido.");
        }

        metodoPagoDAO.Remove(metodoPago);
    }
}