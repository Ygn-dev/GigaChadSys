using GigaChadSysDBManager;
using GigaChadSysModel;
using MySql.Data.MySqlClient;

namespace GigaChadSysDAO;

public class MaquinaDAOImpl : MaquinaDAO
{
    public List<Maquina> ListAll()
    {
        List<Maquina> maquinas = new();

        string sql = @"SELECT idMaquina, nombre, marca, estado,
                              fechaUltimoMantenimiento, activo
                       FROM Maquina
                       WHERE activo = 1";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);
            using MySqlDataReader reader = comando.ExecuteReader();

            while (reader.Read())
            {
                maquinas.Add(MapearMaquina(reader));
            }
        }
        catch (Exception ex)
        {
            throw new Exception("Error al listar máquinas.", ex);
        }

        return maquinas;
    }

    public Maquina? Load(int id)
    {
        Maquina? maquina = null;

        string sql = @"SELECT idMaquina, nombre, marca, estado,
                              fechaUltimoMantenimiento, activo
                       FROM Maquina
                       WHERE idMaquina = @idMaquina";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@idMaquina", id);

            using MySqlDataReader reader = comando.ExecuteReader();

            if (reader.Read())
            {
                maquina = MapearMaquina(reader);
            }
        }
        catch (Exception ex)
        {
            throw new Exception("Error al cargar máquina.", ex);
        }

        return maquina;
    }

    public Maquina Save(Maquina maquina)
    {
        maquina.Activo = true;

        string sql = @"INSERT INTO Maquina
                       (nombre, marca, estado, fechaUltimoMantenimiento, activo)
                       VALUES
                       (@nombre, @marca, @estado, @fechaUltimoMantenimiento, @activo)";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@nombre", maquina.Nombre);
            comando.Parameters.AddWithValue("@marca", maquina.Marca);
            comando.Parameters.AddWithValue("@estado", maquina.Estado);
            comando.Parameters.AddWithValue("@fechaUltimoMantenimiento",
                maquina.FechaUltimoMantenimiento.HasValue
                    ? maquina.FechaUltimoMantenimiento.Value
                    : DBNull.Value);
            comando.Parameters.AddWithValue("@activo", maquina.Activo);

            comando.ExecuteNonQuery();

            maquina.IdMaquina = (int)comando.LastInsertedId;
        }
        catch (Exception ex)
        {
            throw new Exception("Error al registrar máquina.", ex);
        }

        return maquina;
    }

    public Maquina? Update(Maquina maquina)
    {
        string sql = @"UPDATE Maquina SET
                            nombre = @nombre,
                            marca = @marca,
                            estado = @estado,
                            fechaUltimoMantenimiento = @fechaUltimoMantenimiento,
                            activo = @activo
                       WHERE idMaquina = @idMaquina";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@nombre", maquina.Nombre);
            comando.Parameters.AddWithValue("@marca", maquina.Marca);
            comando.Parameters.AddWithValue("@estado", maquina.Estado);
            comando.Parameters.AddWithValue("@fechaUltimoMantenimiento",
                maquina.FechaUltimoMantenimiento.HasValue
                    ? maquina.FechaUltimoMantenimiento.Value
                    : DBNull.Value);
            comando.Parameters.AddWithValue("@activo", maquina.Activo);
            comando.Parameters.AddWithValue("@idMaquina", maquina.IdMaquina);

            int filas = comando.ExecuteNonQuery();

            return filas > 0 ? maquina : null;
        }
        catch (Exception ex)
        {
            throw new Exception("Error al actualizar máquina.", ex);
        }
    }

    public void Remove(Maquina maquina)
    {
        maquina.Activo = false;

        string sql = @"UPDATE Maquina
                       SET activo = @activo
                       WHERE idMaquina = @idMaquina";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@activo", maquina.Activo);
            comando.Parameters.AddWithValue("@idMaquina", maquina.IdMaquina);

            comando.ExecuteNonQuery();
        }
        catch (Exception ex)
        {
            throw new Exception("Error al eliminar máquina.", ex);
        }
    }

    private Maquina MapearMaquina(MySqlDataReader reader)
    {
        return new Maquina
        {
            IdMaquina = reader.GetInt32("idMaquina"),
            Nombre = reader.GetString("nombre"),
            Marca = reader.IsDBNull(reader.GetOrdinal("marca")) ? "" : reader.GetString("marca"),
            Estado = reader.IsDBNull(reader.GetOrdinal("estado")) ? "" : reader.GetString("estado"),
            FechaUltimoMantenimiento = reader.IsDBNull(reader.GetOrdinal("fechaUltimoMantenimiento"))
                ? null
                : reader.GetDateTime("fechaUltimoMantenimiento"),
            Activo = reader.GetBoolean("activo")
        };
    }
}