using GigaChadSysModel;

namespace GigaChadSysBL;

public interface ReservaIBL
{
    List<Reserva> ListAll();
    List<Reserva> ListBySocio(int idUsuario);
    List<Reserva> ListBySesion(int idSesion);
    Reserva? Load(int id);
    Reserva Save(Reserva reserva);
    Reserva? Update(Reserva reserva);
    void Remove(Reserva reserva);
}