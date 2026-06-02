using GigaChadSysDBManager;
using GigaChadSysModel;
using MySql.Data.MySqlClient;

namespace GigaChadSysDAO;

public class SocioDAOImpl : SocioDAO
{
    public List<Socio> ListAll()
    {
        List<Socio> socios = new();

        string sql = @"SELECT idUsuario, nombreCompleto, dni, email, telefono,
                              contrasenia, estadoMembresia, activo
                       FROM Socio
                       WHERE activo = 1";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);
            using MySqlDataReader reader = comando.ExecuteReader();

            while (reader.Read())
            {
                socios.Add(MapearSocio(reader));
            }
        }
        catch (Exception ex)
        {
            throw new Exception("Error al listar socios.", ex);
        }

        return socios;
    }

    public Socio? Load(int id)
    {
        Socio? socio = null;

        string sql = @"SELECT idUsuario, nombreCompleto, dni, email, telefono,
                              contrasenia, estadoMembresia, activo
                       FROM Socio
                       WHERE idUsuario = @idUsuario";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@idUsuario", id);

            using MySqlDataReader reader = comando.ExecuteReader();

            if (reader.Read())
            {
                socio = MapearSocio(reader);
            }
        }
        catch (Exception ex)
        {
            throw new Exception("Error al cargar socio.", ex);
        }

        return socio;
    }

    public Socio? LoadByEmail(string email)
    {
        Socio? socio = null;

        string sql = @"SELECT idUsuario, nombreCompleto, dni, email, telefono,
                              contrasenia, estadoMembresia, activo
                       FROM Socio
                       WHERE email = @email AND activo = 1";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@email", email);

            using MySqlDataReader reader = comando.ExecuteReader();

            if (reader.Read())
            {
                socio = MapearSocio(reader);
            }
        }
        catch (Exception ex)
        {
            throw new Exception("Error al buscar socio por email.", ex);
        }

        return socio;
    }

    public Socio Save(Socio socio)
    {
        socio.Activo = true;

        string sql = @"INSERT INTO Socio
                       (nombreCompleto, dni, email, telefono, contrasenia,
                        estadoMembresia, activo)
                       VALUES
                       (@nombreCompleto, @dni, @email, @telefono, @contrasenia,
                        @estadoMembresia, @activo)";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@nombreCompleto", socio.NombreCompleto);
            comando.Parameters.AddWithValue("@dni", socio.Dni);
            comando.Parameters.AddWithValue("@email", socio.Email);
            comando.Parameters.AddWithValue("@telefono", socio.Telefono);
            comando.Parameters.AddWithValue("@contrasenia", socio.Contrasenia);
            comando.Parameters.AddWithValue("@estadoMembresia", socio.EstadoMembresia);
            comando.Parameters.AddWithValue("@activo", socio.Activo);

            comando.ExecuteNonQuery();

            socio.IdUsuario = (int)comando.LastInsertedId;
        }
        catch (Exception ex)
        {
            throw new Exception("Error al registrar socio.", ex);
        }

        return socio;
    }

    public Socio? Update(Socio socio)
    {
        string sql = @"UPDATE Socio SET
                            nombreCompleto = @nombreCompleto,
                            dni = @dni,
                            email = @email,
                            telefono = @telefono,
                            contrasenia = @contrasenia,
                            estadoMembresia = @estadoMembresia,
                            activo = @activo
                       WHERE idUsuario = @idUsuario";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@nombreCompleto", socio.NombreCompleto);
            comando.Parameters.AddWithValue("@dni", socio.Dni);
            comando.Parameters.AddWithValue("@email", socio.Email);
            comando.Parameters.AddWithValue("@telefono", socio.Telefono);
            comando.Parameters.AddWithValue("@contrasenia", socio.Contrasenia);
            comando.Parameters.AddWithValue("@estadoMembresia", socio.EstadoMembresia);
            comando.Parameters.AddWithValue("@activo", socio.Activo);
            comando.Parameters.AddWithValue("@idUsuario", socio.IdUsuario);

            int filas = comando.ExecuteNonQuery();

            return filas > 0 ? socio : null;
        }
        catch (Exception ex)
        {
            throw new Exception("Error al actualizar socio.", ex);
        }
    }

    public void Remove(Socio socio)
    {
        socio.Activo = false;

        string sql = @"UPDATE Socio
                       SET activo = @activo
                       WHERE idUsuario = @idUsuario";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@activo", socio.Activo);
            comando.Parameters.AddWithValue("@idUsuario", socio.IdUsuario);

            comando.ExecuteNonQuery();
        }
        catch (Exception ex)
        {
            throw new Exception("Error al eliminar socio.", ex);
        }
    }



    public void ActualizarEstadoMembresia(int idUsuario, bool estadoMembresia,
                                          MySqlConnection conexion, MySqlTransaction transaccion)
    {
        string sql = @"UPDATE Socio
                       SET estadoMembresia = @estadoMembresia
                       WHERE idUsuario = @idUsuario
                       AND activo = 1";

        using MySqlCommand comando = new(sql, conexion, transaccion);

        comando.Parameters.AddWithValue("@estadoMembresia", estadoMembresia);
        comando.Parameters.AddWithValue("@idUsuario", idUsuario);

        comando.ExecuteNonQuery();
    }

    private Socio MapearSocio(MySqlDataReader reader)
    {
        return new Socio
        {
            IdUsuario = reader.GetInt32("idUsuario"),
            NombreCompleto = reader.GetString("nombreCompleto"),
            Dni = reader.IsDBNull(reader.GetOrdinal("dni")) ? "" : reader.GetString("dni"),
            Email = reader.GetString("email"),
            Telefono = reader.IsDBNull(reader.GetOrdinal("telefono")) ? "" : reader.GetString("telefono"),
            Contrasenia = reader.GetString("contrasenia"),
            EstadoMembresia = reader.GetBoolean("estadoMembresia"),
            Activo = reader.GetBoolean("activo")
        };
    }
}