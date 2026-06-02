using GigaChadSysModel;
using MySql.Data.MySqlClient;

namespace GigaChadSysDAO;

public interface SesionClaseDAO
{
    List<SesionClase> ListAll();
    List<SesionClase> ListByEntrenador(int idEntrenador);
    SesionClase? Load(int id);
    SesionClase Save(SesionClase sesionClase);
    SesionClase? Update(SesionClase sesionClase);
    void Remove(SesionClase sesionClase);

    void DisminuirCupo(int idSesion, MySqlConnection conexion, MySqlTransaction transaccion);
    void AumentarCupo(int idSesion, MySqlConnection conexion, MySqlTransaction transaccion);
}