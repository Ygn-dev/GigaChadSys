using GigaChadSysModel;
using MySql.Data.MySqlClient;

namespace GigaChadSysDAO;

public interface SuscripcionDAO
{
    List<Suscripcion> ListAll();
    List<Suscripcion> ListBySocio(int idUsuario);
    Suscripcion? Load(int id);
    Suscripcion? LoadActivaBySocio(int idUsuario);
    Suscripcion Save(Suscripcion suscripcion);
    Suscripcion Save(Suscripcion suscripcion, MySqlConnection conexion, MySqlTransaction transaccion);
    Suscripcion? Update(Suscripcion suscripcion);
    void Remove(Suscripcion suscripcion);
}