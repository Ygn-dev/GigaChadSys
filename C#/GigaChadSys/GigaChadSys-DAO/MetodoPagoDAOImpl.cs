using GigaChadSysDBManager;
using GigaChadSysModel;
using MySql.Data.MySqlClient;

namespace GigaChadSysDAO;

public class MetodoPagoDAOImpl : MetodoPagoDAO
{
    public List<MetodoPago> ListAll()
    {
        List<MetodoPago> metodos = new();

        string sql = @"SELECT idMetodoPago, tipo, detalle, activo
                       FROM MetodoPago
                       WHERE activo = 1";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);
            using MySqlDataReader reader = comando.ExecuteReader();

            while (reader.Read())
            {
                metodos.Add(MapearMetodoPago(reader));
            }
        }
        catch (Exception ex)
        {
            throw new Exception("Error al listar métodos de pago.", ex);
        }

        return metodos;
    }

    public MetodoPago? Load(int id)
    {
        MetodoPago? metodoPago = null;

        string sql = @"SELECT idMetodoPago, tipo, detalle, activo
                       FROM MetodoPago
                       WHERE idMetodoPago = @idMetodoPago";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@idMetodoPago", id);

            using MySqlDataReader reader = comando.ExecuteReader();

            if (reader.Read())
            {
                metodoPago = MapearMetodoPago(reader);
            }
        }
        catch (Exception ex)
        {
            throw new Exception("Error al cargar método de pago.", ex);
        }

        return metodoPago;
    }

    public MetodoPago Save(MetodoPago metodoPago)
    {
        metodoPago.Activo = true;

        string sql = @"INSERT INTO MetodoPago
                       (tipo, detalle, activo)
                       VALUES
                       (@tipo, @detalle, @activo)";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@tipo", metodoPago.Tipo);
            comando.Parameters.AddWithValue("@detalle", metodoPago.Detalle);
            comando.Parameters.AddWithValue("@activo", metodoPago.Activo);

            comando.ExecuteNonQuery();

            metodoPago.IdMetodoPago = (int)comando.LastInsertedId;
        }
        catch (Exception ex)
        {
            throw new Exception("Error al registrar método de pago.", ex);
        }

        return metodoPago;
    }

    public MetodoPago? Update(MetodoPago metodoPago)
    {
        string sql = @"UPDATE MetodoPago SET
                            tipo = @tipo,
                            detalle = @detalle,
                            activo = @activo
                       WHERE idMetodoPago = @idMetodoPago";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@tipo", metodoPago.Tipo);
            comando.Parameters.AddWithValue("@detalle", metodoPago.Detalle);
            comando.Parameters.AddWithValue("@activo", metodoPago.Activo);
            comando.Parameters.AddWithValue("@idMetodoPago", metodoPago.IdMetodoPago);

            int filas = comando.ExecuteNonQuery();

            return filas > 0 ? metodoPago : null;
        }
        catch (Exception ex)
        {
            throw new Exception("Error al actualizar método de pago.", ex);
        }
    }

    public void Remove(MetodoPago metodoPago)
    {
        metodoPago.Activo = false;

        string sql = @"UPDATE MetodoPago
                       SET activo = @activo
                       WHERE idMetodoPago = @idMetodoPago";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@activo", metodoPago.Activo);
            comando.Parameters.AddWithValue("@idMetodoPago", metodoPago.IdMetodoPago);

            comando.ExecuteNonQuery();
        }
        catch (Exception ex)
        {
            throw new Exception("Error al eliminar método de pago.", ex);
        }
    }

    private MetodoPago MapearMetodoPago(MySqlDataReader reader)
    {
        return new MetodoPago
        {
            IdMetodoPago = reader.GetInt32("idMetodoPago"),
            Tipo = reader.GetString("tipo"),
            Detalle = reader.IsDBNull(reader.GetOrdinal("detalle")) ? "" : reader.GetString("detalle"),
            Activo = reader.GetBoolean("activo")
        };
    }
}