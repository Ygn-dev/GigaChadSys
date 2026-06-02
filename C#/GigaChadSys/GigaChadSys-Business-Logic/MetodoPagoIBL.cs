using GigaChadSysModel;

namespace GigaChadSysBL;

public interface MetodoPagoIBL
{
    List<MetodoPago> ListAll();
    MetodoPago? Load(int id);
    MetodoPago Save(MetodoPago metodoPago);
    MetodoPago? Update(MetodoPago metodoPago);
    void Remove(MetodoPago metodoPago);
}