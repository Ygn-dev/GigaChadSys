using GigaChadSysModel;

namespace GigaChadSysBL;

public interface SuscripcionIBL
{
    List<Suscripcion> ListAll();
    List<Suscripcion> ListBySocio(int idUsuario);
    Suscripcion? Load(int id);
    Suscripcion? LoadActivaBySocio(int idUsuario);

    Suscripcion Save(Suscripcion suscripcion);
    Suscripcion RegistrarConPago(Suscripcion suscripcion, Pago pago);

    Suscripcion? Update(Suscripcion suscripcion);
    void Remove(Suscripcion suscripcion);
}