using GigaChadSysDAO;
using GigaChadSysModel;

namespace GigaChadSysBL;

public class PagoBL : PagoIBL
{
    private readonly PagoDAO pagoDAO;

    public PagoBL()
    {
        pagoDAO = new PagoDAOImpl();
    }

    public List<Pago> ListAll()
    {
        return pagoDAO.ListAll();
    }

    public Pago? Load(int id)
    {
        if (id <= 0)
        {
            throw new Exception("El id del pago no es válido.");
        }

        return pagoDAO.Load(id);
    }

    public Pago Save(Pago pago)
    {
        if (pago == null)
        {
            throw new Exception("El pago no puede ser nulo.");
        }

        if (pago.MontoTotal <= 0)
        {
            throw new Exception("El monto del pago debe ser mayor a cero.");
        }

        if (pago.IdMetodoPago <= 0)
        {
            throw new Exception("Debe seleccionar un método de pago válido.");
        }

        if (string.IsNullOrWhiteSpace(pago.Tipo))
        {
            throw new Exception("El tipo de pago es obligatorio.");
        }

        if (pago.FechaPago == null)
        {
            pago.FechaPago = DateTime.Now;
        }

        return pagoDAO.Save(pago);
    }

    public Pago? Update(Pago pago)
    {
        if (pago == null)
        {
            throw new Exception("El pago no puede ser nulo.");
        }

        if (pago.IdPago <= 0)
        {
            throw new Exception("El id del pago no es válido.");
        }

        if (pago.MontoTotal <= 0)
        {
            throw new Exception("El monto del pago debe ser mayor a cero.");
        }

        if (pago.IdMetodoPago <= 0)
        {
            throw new Exception("Debe seleccionar un método de pago válido.");
        }

        if (string.IsNullOrWhiteSpace(pago.Tipo))
        {
            throw new Exception("El tipo de pago es obligatorio.");
        }

        return pagoDAO.Update(pago);
    }

    public void Remove(Pago pago)
    {
        if (pago == null)
        {
            throw new Exception("El pago no puede ser nulo.");
        }

        if (pago.IdPago <= 0)
        {
            throw new Exception("El id del pago no es válido.");
        }

        pagoDAO.Remove(pago);
    }
}