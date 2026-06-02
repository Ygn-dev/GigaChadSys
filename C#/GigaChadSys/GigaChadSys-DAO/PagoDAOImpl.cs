using GigaChadSysDBManager;
using GigaChadSysModel;
using MySql.Data.MySqlClient;


namespace GigaChadSysDAO;

public class PagoDAOImpl : PagoDAO
{
    public List<Pago> ListAll()
    {
        List<Pago> pagos = new();

        string sql = @"SELECT idPago, fechaPago, montoTotal, tipo, idMetodoPago, activo
                       FROM Pago
                       WHERE activo = 1";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);
            using MySqlDataReader reader = comando.ExecuteReader();

            while (reader.Read())
            {
                pagos.Add(MapearPago(reader));
            }
        }
        catch (Exception ex)
        {
            throw new Exception("Error al listar pagos.", ex);
        }

        return pagos;
    }

    public Pago? Load(int id)
    {
        Pago? pago = null;

        string sql = @"SELECT idPago, fechaPago, montoTotal, tipo, idMetodoPago, activo
                       FROM Pago
                       WHERE idPago = @idPago";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@idPago", id);

            using MySqlDataReader reader = comando.ExecuteReader();

            if (reader.Read())
            {
                pago = MapearPago(reader);
            }
        }
        catch (Exception ex)
        {
            throw new Exception("Error al cargar pago.", ex);
        }

        return pago;
    }

    public Pago Save(Pago pago)
    {
        pago.Activo = true;

        string sql = @"INSERT INTO Pago
                       (fechaPago, montoTotal, tipo, idMetodoPago, activo)
                       VALUES
                       (@fechaPago, @montoTotal, @tipo, @idMetodoPago, @activo)";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            CargarParametrosSave(comando, pago);

            comando.ExecuteNonQuery();

            pago.IdPago = (int)comando.LastInsertedId;
        }
        catch (Exception ex)
        {
            throw new Exception("Error al registrar pago.", ex);
        }

        return pago;
    }

    public Pago Save(Pago pago, MySqlConnection conexion, MySqlTransaction transaccion)
    {
        pago.Activo = true;

        string sql = @"INSERT INTO Pago
                       (fechaPago, montoTotal, tipo, idMetodoPago, activo)
                       VALUES
                       (@fechaPago, @montoTotal, @tipo, @idMetodoPago, @activo)";

        using MySqlCommand comando = new(sql, conexion, transaccion);

        CargarParametrosSave(comando, pago);

        comando.ExecuteNonQuery();

        pago.IdPago = (int)comando.LastInsertedId;

        return pago;
    }

    public Pago? Update(Pago pago)
    {
        string sql = @"UPDATE Pago SET
                            fechaPago = @fechaPago,
                            montoTotal = @montoTotal,
                            tipo = @tipo,
                            idMetodoPago = @idMetodoPago,
                            activo = @activo
                       WHERE idPago = @idPago";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@fechaPago", (object?)pago.FechaPago ?? DBNull.Value);
            comando.Parameters.AddWithValue("@montoTotal", pago.MontoTotal);
            comando.Parameters.AddWithValue("@tipo", pago.Tipo);
            comando.Parameters.AddWithValue("@idMetodoPago", pago.IdMetodoPago);
            comando.Parameters.AddWithValue("@activo", pago.Activo);
            comando.Parameters.AddWithValue("@idPago", pago.IdPago);

            int filas = comando.ExecuteNonQuery();

            return filas > 0 ? pago : null;
        }
        catch (Exception ex)
        {
            throw new Exception("Error al actualizar pago.", ex);
        }
    }

    public void Remove(Pago pago)
    {
        pago.Activo = false;

        string sql = @"UPDATE Pago
                       SET activo = @activo
                       WHERE idPago = @idPago";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@activo", pago.Activo);
            comando.Parameters.AddWithValue("@idPago", pago.IdPago);

            comando.ExecuteNonQuery();
        }
        catch (Exception ex)
        {
            throw new Exception("Error al eliminar pago.", ex);
        }
    }

    private void CargarParametrosSave(MySqlCommand comando, Pago pago)
    {
        comando.Parameters.AddWithValue("@fechaPago", (object?)pago.FechaPago ?? DBNull.Value);
        comando.Parameters.AddWithValue("@montoTotal", pago.MontoTotal);
        comando.Parameters.AddWithValue("@tipo", pago.Tipo);
        comando.Parameters.AddWithValue("@idMetodoPago", pago.IdMetodoPago);
        comando.Parameters.AddWithValue("@activo", pago.Activo);
    }

    private Pago MapearPago(MySqlDataReader reader)
    {
        return new Pago
        {
            IdPago = reader.GetInt32("idPago"),
            FechaPago = reader.IsDBNull(reader.GetOrdinal("fechaPago"))
                ? null
                : reader.GetDateTime("fechaPago"),
            MontoTotal = reader.GetDecimal("montoTotal"),
            Tipo = reader.IsDBNull(reader.GetOrdinal("tipo")) ? "" : reader.GetString("tipo"),
            IdMetodoPago = reader.GetInt32("idMetodoPago"),
            Activo = reader.GetBoolean("activo")
        };
    }
}