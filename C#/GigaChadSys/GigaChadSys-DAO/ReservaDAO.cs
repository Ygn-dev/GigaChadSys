using GigaChadSysModel;
using MySql.Data.MySqlClient;

namespace GigaChadSysDAO;

public interface ReservaDAO
{
    List<Reserva> ListAll();
    List<Reserva> ListBySocio(int idUsuario);
    List<Reserva> ListBySesion(int idSesion);
    Reserva? Load(int id);
    Reserva Save(Reserva reserva);
    Reserva Save(Reserva reserva, MySqlConnection conexion, MySqlTransaction transaccion);
    Reserva? Update(Reserva reserva);
    void Remove(Reserva reserva);
}