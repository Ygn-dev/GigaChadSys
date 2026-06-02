using GigaChadSysDBManager;
using GigaChadSysModel;
using MySql.Data.MySqlClient;

namespace GigaChadSysDAO;

public class ClaseGrupalDAOImpl : ClaseGrupalDAO
{
    public List<ClaseGrupal> ListAll()
    {
        List<ClaseGrupal> clases = new();

        string sql = @"SELECT idClase, nombreDisciplina, descripcion,
                              duracionMinutos, nivel, activo
                       FROM ClaseGrupal
                       WHERE activo = 1";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);
            using MySqlDataReader reader = comando.ExecuteReader();

            while (reader.Read())
            {
                clases.Add(MapearClaseGrupal(reader));
            }
        }
        catch (Exception ex)
        {
            throw new Exception("Error al listar clases grupales.", ex);
        }

        return clases;
    }

    public ClaseGrupal? Load(int id)
    {
        ClaseGrupal? claseGrupal = null;

        string sql = @"SELECT idClase, nombreDisciplina, descripcion,
                              duracionMinutos, nivel, activo
                       FROM ClaseGrupal
                       WHERE idClase = @idClase";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@idClase", id);

            using MySqlDataReader reader = comando.ExecuteReader();

            if (reader.Read())
            {
                claseGrupal = MapearClaseGrupal(reader);
            }
        }
        catch (Exception ex)
        {
            throw new Exception("Error al cargar clase grupal.", ex);
        }

        return claseGrupal;
    }

    public ClaseGrupal Save(ClaseGrupal claseGrupal)
    {
        claseGrupal.Activo = true;

        string sql = @"INSERT INTO ClaseGrupal
                       (nombreDisciplina, descripcion, duracionMinutos, nivel, activo)
                       VALUES
                       (@nombreDisciplina, @descripcion, @duracionMinutos, @nivel, @activo)";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@nombreDisciplina", claseGrupal.NombreDisciplina);
            comando.Parameters.AddWithValue("@descripcion", claseGrupal.Descripcion);
            comando.Parameters.AddWithValue("@duracionMinutos", claseGrupal.DuracionMinutos);
            comando.Parameters.AddWithValue("@nivel", claseGrupal.Nivel);
            comando.Parameters.AddWithValue("@activo", claseGrupal.Activo);

            comando.ExecuteNonQuery();

            claseGrupal.IdClase = (int)comando.LastInsertedId;
        }
        catch (Exception ex)
        {
            throw new Exception("Error al registrar clase grupal.", ex);
        }

        return claseGrupal;
    }

    public ClaseGrupal? Update(ClaseGrupal claseGrupal)
    {
        string sql = @"UPDATE ClaseGrupal SET
                            nombreDisciplina = @nombreDisciplina,
                            descripcion = @descripcion,
                            duracionMinutos = @duracionMinutos,
                            nivel = @nivel,
                            activo = @activo
                       WHERE idClase = @idClase";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@nombreDisciplina", claseGrupal.NombreDisciplina);
            comando.Parameters.AddWithValue("@descripcion", claseGrupal.Descripcion);
            comando.Parameters.AddWithValue("@duracionMinutos", claseGrupal.DuracionMinutos);
            comando.Parameters.AddWithValue("@nivel", claseGrupal.Nivel);
            comando.Parameters.AddWithValue("@activo", claseGrupal.Activo);
            comando.Parameters.AddWithValue("@idClase", claseGrupal.IdClase);

            int filas = comando.ExecuteNonQuery();

            return filas > 0 ? claseGrupal : null;
        }
        catch (Exception ex)
        {
            throw new Exception("Error al actualizar clase grupal.", ex);
        }
    }
    public void Remove(ClaseGrupal claseGrupal)
    {
        claseGrupal.Activo = false;

        string sql = @"UPDATE ClaseGrupal
                       SET activo = @activo
                       WHERE idClase = @idClase";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@activo", claseGrupal.Activo);
            comando.Parameters.AddWithValue("@idClase", claseGrupal.IdClase);

            comando.ExecuteNonQuery();
        }
        catch (Exception ex)
        {
            throw new Exception("Error al eliminar clase grupal.", ex);
        }
    }

    private ClaseGrupal MapearClaseGrupal(MySqlDataReader reader)
    {
        return new ClaseGrupal
        {
            IdClase = reader.GetInt32("idClase"),
            NombreDisciplina = reader.GetString("nombreDisciplina"),
            Descripcion = reader.IsDBNull(reader.GetOrdinal("descripcion")) ? "" : reader.GetString("descripcion"),
            DuracionMinutos = reader.GetInt32("duracionMinutos"),
            Nivel = reader.IsDBNull(reader.GetOrdinal("nivel")) ? "" : reader.GetString("nivel"),
            Activo = reader.GetBoolean("activo")
        };
    }
}