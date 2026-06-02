using GigaChadSysModel;
using MySql.Data.MySqlClient;

namespace GigaChadSysDAO;

public interface SocioDAO
{
    List<Socio> ListAll();
    Socio? Load(int id);
    Socio? LoadByEmail(string email);
    Socio Save(Socio socio);
    Socio? Update(Socio socio);
    void Remove(Socio socio);

    void ActualizarEstadoMembresia(int idUsuario, bool estadoMembresia,
                                   MySqlConnection conexion, MySqlTransaction transaccion);
}