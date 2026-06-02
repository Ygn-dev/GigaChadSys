using GigaChadSysDBManager;
using GigaChadSysModel;
using MySql.Data.MySqlClient;

namespace GigaChadSysDAO;

public class SalonDAOImpl : SalonDAO
{
    public List<Salon> ListAll()
    {
        List<Salon> salones = new();

        string sql = @"SELECT idSalon, nombreSalon, aforoMaximo, activo
                       FROM Salon
                       WHERE activo = 1";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);
            using MySqlDataReader reader = comando.ExecuteReader();

            while (reader.Read())
            {
                salones.Add(MapearSalon(reader));
            }
        }
        catch (Exception ex)
        {
            throw new Exception("Error al listar salones.", ex);
        }

        return salones;
    }

    public Salon? Load(int id)
    {
        Salon? salon = null;

        string sql = @"SELECT idSalon, nombreSalon, aforoMaximo, activo
                       FROM Salon
                       WHERE idSalon = @idSalon";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@idSalon", id);

            using MySqlDataReader reader = comando.ExecuteReader();

            if (reader.Read())
            {
                salon = MapearSalon(reader);
            }
        }
        catch (Exception ex)
        {
            throw new Exception("Error al cargar salón.", ex);
        }

        return salon;
    }

    public Salon Save(Salon salon)
    {
        salon.Activo = true;

        string sql = @"INSERT INTO Salon
                       (nombreSalon, aforoMaximo, activo)
                       VALUES
                       (@nombreSalon, @aforoMaximo, @activo)";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@nombreSalon", salon.NombreSalon);
            comando.Parameters.AddWithValue("@aforoMaximo", salon.AforoMaximo);
            comando.Parameters.AddWithValue("@activo", salon.Activo);

            comando.ExecuteNonQuery();

            salon.IdSalon = (int)comando.LastInsertedId;
        }
        catch (Exception ex)
        {
            throw new Exception("Error al registrar salón.", ex);
        }

        return salon;
    }

    public Salon? Update(Salon salon)
    {
        string sql = @"UPDATE Salon SET
                            nombreSalon = @nombreSalon,
                            aforoMaximo = @aforoMaximo,
                            activo = @activo
                       WHERE idSalon = @idSalon";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@nombreSalon", salon.NombreSalon);
            comando.Parameters.AddWithValue("@aforoMaximo", salon.AforoMaximo);
            comando.Parameters.AddWithValue("@activo", salon.Activo);
            comando.Parameters.AddWithValue("@idSalon", salon.IdSalon);

            int filas = comando.ExecuteNonQuery();

            return filas > 0 ? salon : null;
        }
        catch (Exception ex)
        {
            throw new Exception("Error al actualizar salón.", ex);
        }
    }

    public void Remove(Salon salon)
    {
        salon.Activo = false;

        string sql = @"UPDATE Salon
                       SET activo = @activo
                       WHERE idSalon = @idSalon";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@activo", salon.Activo);
            comando.Parameters.AddWithValue("@idSalon", salon.IdSalon);

            comando.ExecuteNonQuery();
        }
        catch (Exception ex)
        {
            throw new Exception("Error al eliminar salón.", ex);
        }
    }

    private Salon MapearSalon(MySqlDataReader reader)
    {
        return new Salon
        {
            IdSalon = reader.GetInt32("idSalon"),
            NombreSalon = reader.GetString("nombreSalon"),
            AforoMaximo = reader.GetInt32("aforoMaximo"),
            Activo = reader.GetBoolean("activo")
        };
    }
}