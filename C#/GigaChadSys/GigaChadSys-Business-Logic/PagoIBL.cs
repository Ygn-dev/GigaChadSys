using GigaChadSysModel;

namespace GigaChadSysBL;

public interface PagoIBL
{
    List<Pago> ListAll();
    Pago? Load(int id);
    Pago Save(Pago pago);
    Pago? Update(Pago pago);
    void Remove(Pago pago);
}