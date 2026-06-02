using GigaChadSysDBManager;
using GigaChadSysModel;
using MySql.Data.MySqlClient;

namespace GigaChadSysDAO;

public class SesionClaseDAOImpl : SesionClaseDAO
{
    public List<SesionClase> ListAll()
    {
        List<SesionClase> sesiones = new();

        string sql = @"SELECT idSesion, fechaSesion, horaInicio, horaFin,
                              cuposDisponibles, activo, idSalon, idEntrenador, idClase
                       FROM SesionClase
                       WHERE activo = 1";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);
            using MySqlDataReader reader = comando.ExecuteReader();

            while (reader.Read())
            {
                sesiones.Add(MapearSesionClase(reader));
            }
        }
        catch (Exception ex)
        {
            throw new Exception("Error al listar sesiones de clase.", ex);
        }

        return sesiones;
    }

    public List<SesionClase> ListByEntrenador(int idEntrenador)
    {
        List<SesionClase> sesiones = new();

        string sql = @"SELECT idSesion, fechaSesion, horaInicio, horaFin,
                              cuposDisponibles, activo, idSalon, idEntrenador, idClase
                       FROM SesionClase
                       WHERE activo = 1 AND idEntrenador = @idEntrenador";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@idEntrenador", idEntrenador);

            using MySqlDataReader reader = comando.ExecuteReader();

            while (reader.Read())
            {
                sesiones.Add(MapearSesionClase(reader));
            }
        }
        catch (Exception ex)
        {
            throw new Exception("Error al listar sesiones del entrenador.", ex);
        }

        return sesiones;
    }

    public SesionClase? Load(int id)
    {
        SesionClase? sesion = null;

        string sql = @"SELECT idSesion, fechaSesion, horaInicio, horaFin,
                              cuposDisponibles, activo, idSalon, idEntrenador, idClase
                       FROM SesionClase
                       WHERE idSesion = @idSesion";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@idSesion", id);

            using MySqlDataReader reader = comando.ExecuteReader();

            if (reader.Read())
            {
                sesion = MapearSesionClase(reader);
            }
        }
        catch (Exception ex)
        {
            throw new Exception("Error al cargar sesión de clase.", ex);
        }

        return sesion;
    }

    public SesionClase Save(SesionClase sesionClase)
    {
        sesionClase.Activo = true;

        string sql = @"INSERT INTO SesionClase
                       (fechaSesion, horaInicio, horaFin, cuposDisponibles,
                        activo, idSalon, idEntrenador, idClase)
                       VALUES
                       (@fechaSesion, @horaInicio, @horaFin, @cuposDisponibles,
                        @activo, @idSalon, @idEntrenador, @idClase)";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@fechaSesion", sesionClase.FechaSesion);
            comando.Parameters.AddWithValue("@horaInicio", sesionClase.HoraInicio);
            comando.Parameters.AddWithValue("@horaFin", sesionClase.HoraFin);
            comando.Parameters.AddWithValue("@cuposDisponibles", sesionClase.CuposDisponibles);
            comando.Parameters.AddWithValue("@activo", sesionClase.Activo);
            comando.Parameters.AddWithValue("@idSalon", sesionClase.IdSalon);
            comando.Parameters.AddWithValue("@idEntrenador", sesionClase.IdEntrenador);
            comando.Parameters.AddWithValue("@idClase", sesionClase.IdClase);

            comando.ExecuteNonQuery();

            sesionClase.IdSesion = (int)comando.LastInsertedId;
        }
        catch (Exception ex)
        {
            throw new Exception("Error al registrar sesión de clase.", ex);
        }

        return sesionClase;
    }

    public SesionClase? Update(SesionClase sesionClase)
    {
        string sql = @"UPDATE SesionClase SET
                            fechaSesion = @fechaSesion,
                            horaInicio = @horaInicio,
                            horaFin = @horaFin,
                            cuposDisponibles = @cuposDisponibles,
                            activo = @activo,
                            idSalon = @idSalon,
                            idEntrenador = @idEntrenador,
                            idClase = @idClase
                       WHERE idSesion = @idSesion";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@fechaSesion", sesionClase.FechaSesion);
            comando.Parameters.AddWithValue("@horaInicio", sesionClase.HoraInicio);
            comando.Parameters.AddWithValue("@horaFin", sesionClase.HoraFin);
            comando.Parameters.AddWithValue("@cuposDisponibles", sesionClase.CuposDisponibles);
            comando.Parameters.AddWithValue("@activo", sesionClase.Activo);
            comando.Parameters.AddWithValue("@idSalon", sesionClase.IdSalon);
            comando.Parameters.AddWithValue("@idEntrenador", sesionClase.IdEntrenador);
            comando.Parameters.AddWithValue("@idClase", sesionClase.IdClase);
            comando.Parameters.AddWithValue("@idSesion", sesionClase.IdSesion);

            int filas = comando.ExecuteNonQuery();

            return filas > 0 ? sesionClase : null;
        }
        catch (Exception ex)
        {
            throw new Exception("Error al actualizar sesión de clase.", ex);
        }
    }

    public void Remove(SesionClase sesionClase)
    {
        sesionClase.Activo = false;

        string sql = @"UPDATE SesionClase
                       SET activo = @activo
                       WHERE idSesion = @idSesion";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@activo", sesionClase.Activo);
            comando.Parameters.AddWithValue("@idSesion", sesionClase.IdSesion);

            comando.ExecuteNonQuery();
        }
        catch (Exception ex)
        {
            throw new Exception("Error al eliminar sesión de clase.", ex);
        }
    }

    public void DisminuirCupo(int idSesion, MySqlConnection conexion, MySqlTransaction transaccion)
    {
        string sql = @"UPDATE SesionClase
                       SET cuposDisponibles = cuposDisponibles - 1
                       WHERE idSesion = @idSesion
                       AND cuposDisponibles > 0
                       AND activo = 1";

        using MySqlCommand comando = new(sql, conexion, transaccion);
        comando.Parameters.AddWithValue("@idSesion", idSesion);

        int filas = comando.ExecuteNonQuery();

        if (filas == 0)
        {
            throw new Exception("No hay cupos disponibles para esta sesión.");
        }
    }

    public void AumentarCupo(int idSesion, MySqlConnection conexion, MySqlTransaction transaccion)
    {
        string sql = @"UPDATE SesionClase
                       SET cuposDisponibles = cuposDisponibles + 1
                       WHERE idSesion = @idSesion
                       AND activo = 1";

        using MySqlCommand comando = new(sql, conexion, transaccion);
        comando.Parameters.AddWithValue("@idSesion", idSesion);

        comando.ExecuteNonQuery();
    }

    private SesionClase MapearSesionClase(MySqlDataReader reader)
    {
        return new SesionClase
        {
            IdSesion = reader.GetInt32("idSesion"),
            FechaSesion = reader.GetDateTime("fechaSesion"),
            HoraInicio = reader.GetDateTime("horaInicio"),
            HoraFin = reader.GetDateTime("horaFin"),
            CuposDisponibles = reader.GetInt32("cuposDisponibles"),
            Activo = reader.GetBoolean("activo"),
            IdSalon = reader.GetInt32("idSalon"),
            IdEntrenador = reader.GetInt32("idEntrenador"),
            IdClase = reader.GetInt32("idClase")
        };
    }
}