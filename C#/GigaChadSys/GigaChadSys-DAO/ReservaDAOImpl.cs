using GigaChadSysDBManager;
using GigaChadSysModel;
using MySql.Data.MySqlClient;

namespace GigaChadSysDAO;

public class ReservaDAOImpl : ReservaDAO
{
    public List<Reserva> ListAll()
    {
        List<Reserva> reservas = new();

        string sql = @"SELECT idReserva, fechaHoraReserva, asistio,
                              idSesion, idUsuario, activo
                       FROM Reserva
                       WHERE activo = 1";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);
            using MySqlDataReader reader = comando.ExecuteReader();

            while (reader.Read())
            {
                reservas.Add(MapearReserva(reader));
            }
        }
        catch (Exception ex)
        {
            throw new Exception("Error al listar reservas.", ex);
        }

        return reservas;
    }

    public List<Reserva> ListBySocio(int idUsuario)
    {
        List<Reserva> reservas = new();

        string sql = @"SELECT idReserva, fechaHoraReserva, asistio,
                              idSesion, idUsuario, activo
                       FROM Reserva
                       WHERE activo = 1 AND idUsuario = @idUsuario";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@idUsuario", idUsuario);

            using MySqlDataReader reader = comando.ExecuteReader();

            while (reader.Read())
            {
                reservas.Add(MapearReserva(reader));
            }
        }
        catch (Exception ex)
        {
            throw new Exception("Error al listar reservas del socio.", ex);
        }

        return reservas;
    }

    public List<Reserva> ListBySesion(int idSesion)
    {
        List<Reserva> reservas = new();

        string sql = @"SELECT idReserva, fechaHoraReserva, asistio,
                              idSesion, idUsuario, activo
                       FROM Reserva
                       WHERE activo = 1 AND idSesion = @idSesion";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@idSesion", idSesion);

            using MySqlDataReader reader = comando.ExecuteReader();

            while (reader.Read())
            {
                reservas.Add(MapearReserva(reader));
            }
        }
        catch (Exception ex)
        {
            throw new Exception("Error al listar reservas de la sesión.", ex);
        }

        return reservas;
    }

    public Reserva? Load(int id)
    {
        Reserva? reserva = null;

        string sql = @"SELECT idReserva, fechaHoraReserva, asistio,
                              idSesion, idUsuario, activo
                       FROM Reserva
                       WHERE idReserva = @idReserva";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@idReserva", id);

            using MySqlDataReader reader = comando.ExecuteReader();

            if (reader.Read())
            {
                reserva = MapearReserva(reader);
            }
        }
        catch (Exception ex)
        {
            throw new Exception("Error al cargar reserva.", ex);
        }

        return reserva;
    }

    public Reserva Save(Reserva reserva)
    {
        reserva.Activo = true;

        string sql = @"INSERT INTO Reserva
                       (fechaHoraReserva, asistio, idSesion, idUsuario, activo)
                       VALUES
                       (@fechaHoraReserva, @asistio, @idSesion, @idUsuario, @activo)";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            CargarParametrosSave(comando, reserva);

            comando.ExecuteNonQuery();

            reserva.IdReserva = (int)comando.LastInsertedId;
        }
        catch (Exception ex)
        {
            throw new Exception("Error al registrar reserva.", ex);
        }

        return reserva;
    }

    public Reserva Save(Reserva reserva, MySqlConnection conexion, MySqlTransaction transaccion)
    {
        reserva.Activo = true;

        string sql = @"INSERT INTO Reserva
                       (fechaHoraReserva, asistio, idSesion, idUsuario, activo)
                       VALUES
                       (@fechaHoraReserva, @asistio, @idSesion, @idUsuario, @activo)";

        using MySqlCommand comando = new(sql, conexion, transaccion);

        CargarParametrosSave(comando, reserva);

        comando.ExecuteNonQuery();

        reserva.IdReserva = (int)comando.LastInsertedId;

        return reserva;
    }

    public Reserva? Update(Reserva reserva)
    {
        string sql = @"UPDATE Reserva SET
                            fechaHoraReserva = @fechaHoraReserva,
                            asistio = @asistio,
                            idSesion = @idSesion,
                            idUsuario = @idUsuario,
                            activo = @activo
                       WHERE idReserva = @idReserva";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@fechaHoraReserva", reserva.FechaHoraReserva);
            comando.Parameters.AddWithValue("@asistio", reserva.Asistio);
            comando.Parameters.AddWithValue("@idSesion", reserva.IdSesion);
            comando.Parameters.AddWithValue("@idUsuario", reserva.IdUsuario);
            comando.Parameters.AddWithValue("@activo", reserva.Activo);
            comando.Parameters.AddWithValue("@idReserva", reserva.IdReserva);

            int filas = comando.ExecuteNonQuery();

            return filas > 0 ? reserva : null;
        }
        catch (Exception ex)
        {
            throw new Exception("Error al actualizar reserva.", ex);
        }
    }

    public void Remove(Reserva reserva)
    {
        reserva.Activo = false;

        string sql = @"UPDATE Reserva
                       SET activo = @activo
                       WHERE idReserva = @idReserva";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@activo", reserva.Activo);
            comando.Parameters.AddWithValue("@idReserva", reserva.IdReserva);

            comando.ExecuteNonQuery();
        }
        catch (Exception ex)
        {
            throw new Exception("Error al eliminar reserva.", ex);
        }
    }

    private void CargarParametrosSave(MySqlCommand comando, Reserva reserva)
    {
        comando.Parameters.AddWithValue("@fechaHoraReserva", reserva.FechaHoraReserva);
        comando.Parameters.AddWithValue("@asistio", reserva.Asistio);
        comando.Parameters.AddWithValue("@idSesion", reserva.IdSesion);
        comando.Parameters.AddWithValue("@idUsuario", reserva.IdUsuario);
        comando.Parameters.AddWithValue("@activo", reserva.Activo);
    }

    private Reserva MapearReserva(MySqlDataReader reader)
    {
        return new Reserva
        {
            IdReserva = reader.GetInt32("idReserva"),
            FechaHoraReserva = reader.GetDateTime("fechaHoraReserva"),
            Asistio = reader.GetBoolean("asistio"),
            IdSesion = reader.GetInt32("idSesion"),
            IdUsuario = reader.GetInt32("idUsuario"),
            Activo = reader.GetBoolean("activo")
        };
    }
}