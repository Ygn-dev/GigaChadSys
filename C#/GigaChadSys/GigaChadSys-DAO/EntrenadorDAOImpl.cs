using GigaChadSysDBManager;
using GigaChadSysModel;
using MySql.Data.MySqlClient;

namespace GigaChadSysDAO;

public class EntrenadorDAOImpl : EntrenadorDAO
{
    public List<Entrenador> ListAll()
    {
        List<Entrenador> entrenadores = new();

        string sql = @"SELECT idUsuario, nombreCompleto, dni, email, telefono,
                              contrasenia, especialidad, sueldo, tiempoTrabajo, activo
                       FROM Entrenador
                       WHERE activo = 1";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);
            using MySqlDataReader reader = comando.ExecuteReader();

            while (reader.Read())
            {
                entrenadores.Add(MapearEntrenador(reader));
            }
        }
        catch (Exception ex)
        {
            throw new Exception("Error al listar entrenadores.", ex);
        }

        return entrenadores;
    }

    public Entrenador? Load(int id)
    {
        Entrenador? entrenador = null;

        string sql = @"SELECT idUsuario, nombreCompleto, dni, email, telefono,
                              contrasenia, especialidad, sueldo, tiempoTrabajo, activo
                       FROM Entrenador
                       WHERE idUsuario = @idUsuario";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@idUsuario", id);

            using MySqlDataReader reader = comando.ExecuteReader();

            if (reader.Read())
            {
                entrenador = MapearEntrenador(reader);
            }
        }
        catch (Exception ex)
        {
            throw new Exception("Error al cargar entrenador.", ex);
        }

        return entrenador;
    }

    public Entrenador Save(Entrenador entrenador)
    {
        entrenador.Activo = true;

        string sql = @"INSERT INTO Entrenador
                       (nombreCompleto, dni, email, telefono, contrasenia,
                        especialidad, sueldo, tiempoTrabajo, activo)
                       VALUES
                       (@nombreCompleto, @dni, @email, @telefono, @contrasenia,
                        @especialidad, @sueldo, @tiempoTrabajo, @activo)";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@nombreCompleto", entrenador.NombreCompleto);
            comando.Parameters.AddWithValue("@dni", entrenador.Dni);
            comando.Parameters.AddWithValue("@email", entrenador.Email);
            comando.Parameters.AddWithValue("@telefono", entrenador.Telefono);
            comando.Parameters.AddWithValue("@contrasenia", entrenador.Contrasenia);
            comando.Parameters.AddWithValue("@especialidad", entrenador.Especialidad);
            comando.Parameters.AddWithValue("@sueldo", entrenador.Sueldo);
            comando.Parameters.AddWithValue("@tiempoTrabajo", entrenador.TiempoTrabajo);
            comando.Parameters.AddWithValue("@activo", entrenador.Activo);

            comando.ExecuteNonQuery();

            entrenador.IdUsuario = (int)comando.LastInsertedId;
        }
        catch (Exception ex)
        {
            throw new Exception("Error al registrar entrenador.", ex);
        }

        return entrenador;
    }

    public Entrenador? Update(Entrenador entrenador)
    {
        string sql = @"UPDATE Entrenador SET
                            nombreCompleto = @nombreCompleto,
                            dni = @dni,
                            email = @email,
                            telefono = @telefono,
                            contrasenia = @contrasenia,
                            especialidad = @especialidad,
                            sueldo = @sueldo,
                            tiempoTrabajo = @tiempoTrabajo,
                            activo = @activo
                       WHERE idUsuario = @idUsuario";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@nombreCompleto", entrenador.NombreCompleto);
            comando.Parameters.AddWithValue("@dni", entrenador.Dni);
            comando.Parameters.AddWithValue("@email", entrenador.Email);
            comando.Parameters.AddWithValue("@telefono", entrenador.Telefono);
            comando.Parameters.AddWithValue("@contrasenia", entrenador.Contrasenia);
            comando.Parameters.AddWithValue("@especialidad", entrenador.Especialidad);
            comando.Parameters.AddWithValue("@sueldo", entrenador.Sueldo);
            comando.Parameters.AddWithValue("@tiempoTrabajo", entrenador.TiempoTrabajo);
            comando.Parameters.AddWithValue("@activo", entrenador.Activo);
            comando.Parameters.AddWithValue("@idUsuario", entrenador.IdUsuario);

            int filas = comando.ExecuteNonQuery();

            return filas > 0 ? entrenador : null;
        }
        catch (Exception ex)
        {
            throw new Exception("Error al actualizar entrenador.", ex);
        }
    }

    public void Remove(Entrenador entrenador)
    {
        entrenador.Activo = false;

        string sql = @"UPDATE Entrenador
                       SET activo = @activo
                       WHERE idUsuario = @idUsuario";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@activo", entrenador.Activo);
            comando.Parameters.AddWithValue("@idUsuario", entrenador.IdUsuario);

            comando.ExecuteNonQuery();
        }
        catch (Exception ex)
        {
            throw new Exception("Error al eliminar entrenador.", ex);
        }
    }

    private Entrenador MapearEntrenador(MySqlDataReader reader)
    {
        return new Entrenador
        {
            IdUsuario = reader.GetInt32("idUsuario"),
            NombreCompleto = reader.GetString("nombreCompleto"),
            Dni = reader.IsDBNull(reader.GetOrdinal("dni")) ? "" : reader.GetString("dni"),
            Email = reader.GetString("email"),
            Telefono = reader.IsDBNull(reader.GetOrdinal("telefono")) ? "" : reader.GetString("telefono"),
            Contrasenia = reader.GetString("contrasenia"),
            Especialidad = reader.IsDBNull(reader.GetOrdinal("especialidad")) ? "" : reader.GetString("especialidad"),
            Sueldo = reader.GetDecimal("sueldo"),
            TiempoTrabajo = reader.GetTimeSpan("tiempoTrabajo"),
            Activo = reader.GetBoolean("activo")
        };
    }

    public Entrenador? LoadByEmail(string email)
    {
        Entrenador? entrenador = null;

        string sql = @"SELECT idUsuario, nombreCompleto, dni, email, telefono,
                          contrasenia, especialidad, sueldo, tiempoTrabajo, activo
                   FROM Entrenador
                   WHERE email = @email AND activo = 1";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@email", email);

            using MySqlDataReader reader = comando.ExecuteReader();

            if (reader.Read())
            {
                entrenador = MapearEntrenador(reader);
            }
        }
        catch (Exception ex)
        {
            throw new Exception("Error al buscar entrenador por email.", ex);
        }

        return entrenador;
    }
}