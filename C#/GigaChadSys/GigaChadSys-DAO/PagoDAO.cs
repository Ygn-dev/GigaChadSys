using GigaChadSysModel;
using MySql.Data.MySqlClient;

namespace GigaChadSysDAO;

public interface PagoDAO
{
    List<Pago> ListAll();
    Pago? Load(int id);
    Pago Save(Pago pago);
    Pago Save(Pago pago, MySqlConnection conexion, MySqlTransaction transaccion);
    Pago? Update(Pago pago);
    void Remove(Pago pago);
}