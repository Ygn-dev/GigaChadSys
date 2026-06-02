using GigaChadSysDBManager;
using GigaChadSysModel;
using MySql.Data.MySqlClient;

namespace GigaChadSysDAO;

public class AdministradorDAOImpl : AdministradorDAO
{
    public List<Administrador> ListAll()
    {
        List<Administrador> administradores = new();

        string sql = @"SELECT idUsuario, nombreCompleto, dni, email, telefono,
                              contrasenia, sede, sueldo, cargo, activo
                       FROM Administrador
                       WHERE activo = 1";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);
            using MySqlDataReader reader = comando.ExecuteReader();

            while (reader.Read())
            {
                administradores.Add(MapearAdministrador(reader));
            }
        }
        catch (Exception ex)
        {
            throw new Exception("Error al listar administradores.", ex);
        }

        return administradores;
    }

    public Administrador? Load(int id)
    {
        Administrador? administrador = null;

        string sql = @"SELECT idUsuario, nombreCompleto, dni, email, telefono,
                              contrasenia, sede, sueldo, cargo, activo
                       FROM Administrador
                       WHERE idUsuario = @idUsuario";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@idUsuario", id);

            using MySqlDataReader reader = comando.ExecuteReader();

            if (reader.Read())
            {
                administrador = MapearAdministrador(reader);
            }
        }
        catch (Exception ex)
        {
            throw new Exception("Error al cargar administrador.", ex);
        }

        return administrador;
    }

    public Administrador Save(Administrador administrador)
    {
        administrador.Activo = true;

        string sql = @"INSERT INTO Administrador
                       (nombreCompleto, dni, email, telefono, contrasenia,
                        sede, sueldo, cargo, activo)
                       VALUES
                       (@nombreCompleto, @dni, @email, @telefono, @contrasenia,
                        @sede, @sueldo, @cargo, @activo)";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@nombreCompleto", administrador.NombreCompleto);
            comando.Parameters.AddWithValue("@dni", administrador.Dni);
            comando.Parameters.AddWithValue("@email", administrador.Email);
            comando.Parameters.AddWithValue("@telefono", administrador.Telefono);
            comando.Parameters.AddWithValue("@contrasenia", administrador.Contrasenia);
            comando.Parameters.AddWithValue("@sede", administrador.Sede);
            comando.Parameters.AddWithValue("@sueldo", administrador.Sueldo);
            comando.Parameters.AddWithValue("@cargo", administrador.Cargo);
            comando.Parameters.AddWithValue("@activo", administrador.Activo);

            comando.ExecuteNonQuery();

            administrador.IdUsuario = (int)comando.LastInsertedId;
        }
        catch (Exception ex)
        {
            throw new Exception("Error al registrar administrador.", ex);
        }

        return administrador;
    }

    public Administrador? Update(Administrador administrador)
    {
        string sql = @"UPDATE Administrador SET
                            nombreCompleto = @nombreCompleto,
                            dni = @dni,
                            email = @email,
                            telefono = @telefono,
                            contrasenia = @contrasenia,
                            sede = @sede,
                            sueldo = @sueldo,
                            cargo = @cargo,
                            activo = @activo
                       WHERE idUsuario = @idUsuario";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@nombreCompleto", administrador.NombreCompleto);
            comando.Parameters.AddWithValue("@dni", administrador.Dni);
            comando.Parameters.AddWithValue("@email", administrador.Email);
            comando.Parameters.AddWithValue("@telefono", administrador.Telefono);
            comando.Parameters.AddWithValue("@contrasenia", administrador.Contrasenia);
            comando.Parameters.AddWithValue("@sede", administrador.Sede);
            comando.Parameters.AddWithValue("@sueldo", administrador.Sueldo);
            comando.Parameters.AddWithValue("@cargo", administrador.Cargo);
            comando.Parameters.AddWithValue("@activo", administrador.Activo);
            comando.Parameters.AddWithValue("@idUsuario", administrador.IdUsuario);

            int filas = comando.ExecuteNonQuery();

            return filas > 0 ? administrador : null;
        }
        catch (Exception ex)
        {
            throw new Exception("Error al actualizar administrador.", ex);
        }
    }

    public Administrador? LoadByEmail(string email)
    {
        Administrador? administrador = null;

        string sql = @"SELECT idUsuario, nombreCompleto, dni, email, telefono,
                          contrasenia, sede, sueldo, cargo, activo
                   FROM Administrador
                   WHERE email = @email AND activo = 1";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@email", email);

            using MySqlDataReader reader = comando.ExecuteReader();

            if (reader.Read())
            {
                administrador = MapearAdministrador(reader);
            }
        }
        catch (Exception ex)
        {
            throw new Exception("Error al buscar administrador por email.", ex);
        }

        return administrador;
    }

    public void Remove(Administrador administrador)
    {
        administrador.Activo = false;

        string sql = @"UPDATE Administrador
                       SET activo = @activo
                       WHERE idUsuario = @idUsuario";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@activo", administrador.Activo);
            comando.Parameters.AddWithValue("@idUsuario", administrador.IdUsuario);

            comando.ExecuteNonQuery();
        }
        catch (Exception ex)
        {
            throw new Exception("Error al eliminar administrador.", ex);
        }
    }

    private Administrador MapearAdministrador(MySqlDataReader reader)
    {
        return new Administrador
        {
            IdUsuario = reader.GetInt32("idUsuario"),
            NombreCompleto = reader.GetString("nombreCompleto"),
            Dni = reader.IsDBNull(reader.GetOrdinal("dni")) ? "" : reader.GetString("dni"),
            Email = reader.GetString("email"),
            Telefono = reader.IsDBNull(reader.GetOrdinal("telefono")) ? "" : reader.GetString("telefono"),
            Contrasenia = reader.GetString("contrasenia"),
            Sede = reader.IsDBNull(reader.GetOrdinal("sede")) ? "" : reader.GetString("sede"),
            Sueldo = reader.GetDecimal("sueldo"),
            Cargo = reader.IsDBNull(reader.GetOrdinal("cargo")) ? "" : reader.GetString("cargo"),
            Activo = reader.GetBoolean("activo")
        };
    }
}