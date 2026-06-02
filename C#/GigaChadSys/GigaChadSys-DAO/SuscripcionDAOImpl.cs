using GigaChadSysDBManager;
using GigaChadSysModel;
using MySql.Data.MySqlClient;

namespace GigaChadSysDAO;

public class SuscripcionDAOImpl : SuscripcionDAO
{
    public List<Suscripcion> ListAll()
    {
        List<Suscripcion> suscripciones = new();

        string sql = @"SELECT idSuscripcion, estadoMembresia, fechaIngreso,
                              fechaFinMembresia, idPago, idUsuario,
                              membresia_ID_basic, membresia_ID_black, activo
                       FROM Suscripcion
                       WHERE activo = 1";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);
            using MySqlDataReader reader = comando.ExecuteReader();

            while (reader.Read())
            {
                suscripciones.Add(MapearSuscripcion(reader));
            }
        }
        catch (Exception ex)
        {
            throw new Exception("Error al listar suscripciones.", ex);
        }

        return suscripciones;
    }

    public List<Suscripcion> ListBySocio(int idUsuario)
    {
        List<Suscripcion> suscripciones = new();

        string sql = @"SELECT idSuscripcion, estadoMembresia, fechaIngreso,
                              fechaFinMembresia, idPago, idUsuario,
                              membresia_ID_basic, membresia_ID_black, activo
                       FROM Suscripcion
                       WHERE activo = 1 AND idUsuario = @idUsuario";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@idUsuario", idUsuario);

            using MySqlDataReader reader = comando.ExecuteReader();

            while (reader.Read())
            {
                suscripciones.Add(MapearSuscripcion(reader));
            }
        }
        catch (Exception ex)
        {
            throw new Exception("Error al listar suscripciones del socio.", ex);
        }

        return suscripciones;
    }

    public Suscripcion? Load(int id)
    {
        Suscripcion? suscripcion = null;

        string sql = @"SELECT idSuscripcion, estadoMembresia, fechaIngreso,
                              fechaFinMembresia, idPago, idUsuario,
                              membresia_ID_basic, membresia_ID_black, activo
                       FROM Suscripcion
                       WHERE idSuscripcion = @idSuscripcion";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@idSuscripcion", id);

            using MySqlDataReader reader = comando.ExecuteReader();

            if (reader.Read())
            {
                suscripcion = MapearSuscripcion(reader);
            }
        }
        catch (Exception ex)
        {
            throw new Exception("Error al cargar suscripción.", ex);
        }

        return suscripcion;
    }

    public Suscripcion? LoadActivaBySocio(int idUsuario)
    {
        Suscripcion? suscripcion = null;

        string sql = @"SELECT idSuscripcion, estadoMembresia, fechaIngreso,
                              fechaFinMembresia, idPago, idUsuario,
                              membresia_ID_basic, membresia_ID_black, activo
                       FROM Suscripcion
                       WHERE idUsuario = @idUsuario
                       AND estadoMembresia = 1
                       AND activo = 1
                       AND fechaFinMembresia >= CURDATE()
                       ORDER BY fechaFinMembresia DESC
                       LIMIT 1";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@idUsuario", idUsuario);

            using MySqlDataReader reader = comando.ExecuteReader();

            if (reader.Read())
            {
                suscripcion = MapearSuscripcion(reader);
            }
        }
        catch (Exception ex)
        {
            throw new Exception("Error al cargar suscripción activa del socio.", ex);
        }

        return suscripcion;
    }

    public Suscripcion Save(Suscripcion suscripcion)
    {
        suscripcion.Activo = true;

        string sql = @"INSERT INTO Suscripcion
                       (estadoMembresia, fechaIngreso, fechaFinMembresia,
                        idPago, idUsuario, membresia_ID_basic,
                        membresia_ID_black, activo)
                       VALUES
                       (@estadoMembresia, @fechaIngreso, @fechaFinMembresia,
                        @idPago, @idUsuario, @membresia_ID_basic,
                        @membresia_ID_black, @activo)";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            CargarParametrosSave(comando, suscripcion);

            comando.ExecuteNonQuery();

            suscripcion.IdSuscripcion = (int)comando.LastInsertedId;
        }
        catch (Exception ex)
        {
            throw new Exception("Error al registrar suscripción.", ex);
        }

        return suscripcion;
    }

    public Suscripcion Save(Suscripcion suscripcion, MySqlConnection conexion, MySqlTransaction transaccion)
    {
        suscripcion.Activo = true;

        string sql = @"INSERT INTO Suscripcion
                       (estadoMembresia, fechaIngreso, fechaFinMembresia,
                        idPago, idUsuario, membresia_ID_basic,
                        membresia_ID_black, activo)
                       VALUES
                       (@estadoMembresia, @fechaIngreso, @fechaFinMembresia,
                        @idPago, @idUsuario, @membresia_ID_basic,
                        @membresia_ID_black, @activo)";

        using MySqlCommand comando = new(sql, conexion, transaccion);

        CargarParametrosSave(comando, suscripcion);

        comando.ExecuteNonQuery();

        suscripcion.IdSuscripcion = (int)comando.LastInsertedId;

        return suscripcion;
    }

    public Suscripcion? Update(Suscripcion suscripcion)
    {
        string sql = @"UPDATE Suscripcion SET
                            estadoMembresia = @estadoMembresia,
                            fechaIngreso = @fechaIngreso,
                            fechaFinMembresia = @fechaFinMembresia,
                            idPago = @idPago,
                            idUsuario = @idUsuario,
                            membresia_ID_basic = @membresia_ID_basic,
                            membresia_ID_black = @membresia_ID_black,
                            activo = @activo
                       WHERE idSuscripcion = @idSuscripcion";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            CargarParametrosSave(comando, suscripcion);
            comando.Parameters.AddWithValue("@idSuscripcion", suscripcion.IdSuscripcion);

            int filas = comando.ExecuteNonQuery();

            return filas > 0 ? suscripcion : null;
        }
        catch (Exception ex)
        {
            throw new Exception("Error al actualizar suscripción.", ex);
        }
    }

    public void Remove(Suscripcion suscripcion)
    {
        suscripcion.Activo = false;

        string sql = @"UPDATE Suscripcion
                       SET activo = @activo
                       WHERE idSuscripcion = @idSuscripcion";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@activo", suscripcion.Activo);
            comando.Parameters.AddWithValue("@idSuscripcion", suscripcion.IdSuscripcion);

            comando.ExecuteNonQuery();
        }
        catch (Exception ex)
        {
            throw new Exception("Error al eliminar suscripción.", ex);
        }
    }

    private void CargarParametrosSave(MySqlCommand comando, Suscripcion suscripcion)
    {
        comando.Parameters.AddWithValue("@estadoMembresia", suscripcion.EstadoMembresia);
        comando.Parameters.AddWithValue("@fechaIngreso", suscripcion.FechaIngreso);
        comando.Parameters.AddWithValue("@fechaFinMembresia", suscripcion.FechaFinMembresia);
        comando.Parameters.AddWithValue("@idPago", suscripcion.IdPago);
        comando.Parameters.AddWithValue("@idUsuario", suscripcion.IdUsuario);

        comando.Parameters.AddWithValue("@membresia_ID_basic",
            suscripcion.MembresiaIdBasic.HasValue
                ? suscripcion.MembresiaIdBasic.Value
                : DBNull.Value);

        comando.Parameters.AddWithValue("@membresia_ID_black",
            suscripcion.MembresiaIdBlack.HasValue
                ? suscripcion.MembresiaIdBlack.Value
                : DBNull.Value);

        comando.Parameters.AddWithValue("@activo", suscripcion.Activo);
    }

    private Suscripcion MapearSuscripcion(MySqlDataReader reader)
    {
        return new Suscripcion
        {
            IdSuscripcion = reader.GetInt32("idSuscripcion"),
            EstadoMembresia = reader.GetBoolean("estadoMembresia"),
            FechaIngreso = reader.GetDateTime("fechaIngreso"),
            FechaFinMembresia = reader.GetDateTime("fechaFinMembresia"),
            IdPago = reader.GetInt32("idPago"),
            IdUsuario = reader.GetInt32("idUsuario"),
            MembresiaIdBasic = reader.IsDBNull(reader.GetOrdinal("membresia_ID_basic"))
                ? null
                : reader.GetInt32("membresia_ID_basic"),
            MembresiaIdBlack = reader.IsDBNull(reader.GetOrdinal("membresia_ID_black"))
                ? null
                : reader.GetInt32("membresia_ID_black"),
            Activo = reader.GetBoolean("activo")
        };
    }
}