using GigaChadSysDBManager;
using GigaChadSysModel;
using MySql.Data.MySqlClient;

namespace GigaChadSysDAO;

public class MembresiaBasicDAOImpl : MembresiaBasicDAO
{
    public List<MembresiaBasic> ListAll()
    {
        List<MembresiaBasic> membresias = new();

        string sql = @"SELECT membresia_ID, nombrePlan, costoMantenimientoMensual, activo
                       FROM MembresiaBasic
                       WHERE activo = 1";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);
            using MySqlDataReader reader = comando.ExecuteReader();

            while (reader.Read())
            {
                membresias.Add(MapearMembresiaBasic(reader));
            }
        }
        catch (Exception ex)
        {
            throw new Exception("Error al listar membresías basic.", ex);
        }

        return membresias;
    }

    public MembresiaBasic? Load(int id)
    {
        MembresiaBasic? membresia = null;

        string sql = @"SELECT membresia_ID, nombrePlan, costoMantenimientoMensual, activo
                       FROM MembresiaBasic
                       WHERE membresia_ID = @membresia_ID";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@membresia_ID", id);

            using MySqlDataReader reader = comando.ExecuteReader();

            if (reader.Read())
            {
                membresia = MapearMembresiaBasic(reader);
            }
        }
        catch (Exception ex)
        {
            throw new Exception("Error al cargar membresía basic.", ex);
        }

        return membresia;
    }

    public MembresiaBasic Save(MembresiaBasic membresiaBasic)
    {
        membresiaBasic.Activo = true;

        string sql = @"INSERT INTO MembresiaBasic
                       (nombrePlan, costoMantenimientoMensual, activo)
                       VALUES
                       (@nombrePlan, @costoMantenimientoMensual, @activo)";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@nombrePlan", membresiaBasic.NombrePlan);
            comando.Parameters.AddWithValue("@costoMantenimientoMensual", membresiaBasic.CostoMantenimientoMensual);
            comando.Parameters.AddWithValue("@activo", membresiaBasic.Activo);

            comando.ExecuteNonQuery();

            membresiaBasic.MembresiaId = (int)comando.LastInsertedId;
        }
        catch (Exception ex)
        {
            throw new Exception("Error al registrar membresía basic.", ex);
        }

        return membresiaBasic;
    }

    public MembresiaBasic? Update(MembresiaBasic membresiaBasic)
    {
        string sql = @"UPDATE MembresiaBasic SET
                            nombrePlan = @nombrePlan,
                            costoMantenimientoMensual = @costoMantenimientoMensual,
                            activo = @activo
                       WHERE membresia_ID = @membresia_ID";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@nombrePlan", membresiaBasic.NombrePlan);
            comando.Parameters.AddWithValue("@costoMantenimientoMensual", membresiaBasic.CostoMantenimientoMensual);
            comando.Parameters.AddWithValue("@activo", membresiaBasic.Activo);
            comando.Parameters.AddWithValue("@membresia_ID", membresiaBasic.MembresiaId);

            int filas = comando.ExecuteNonQuery();

            return filas > 0 ? membresiaBasic : null;
        }
        catch (Exception ex)
        {
            throw new Exception("Error al actualizar membresía basic.", ex);
        }
    }

    public void Remove(MembresiaBasic membresiaBasic)
    {
        membresiaBasic.Activo = false;

        string sql = @"UPDATE MembresiaBasic
                       SET activo = @activo
                       WHERE membresia_ID = @membresia_ID";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@activo", membresiaBasic.Activo);
            comando.Parameters.AddWithValue("@membresia_ID", membresiaBasic.MembresiaId);

            comando.ExecuteNonQuery();
        }
        catch (Exception ex)
        {
            throw new Exception("Error al eliminar membresía basic.", ex);
        }
    }

    private MembresiaBasic MapearMembresiaBasic(MySqlDataReader reader)
    {
        return new MembresiaBasic
        {
            MembresiaId = reader.GetInt32("membresia_ID"),
            NombrePlan = reader.GetString("nombrePlan"),
            CostoMantenimientoMensual = reader.GetDecimal("costoMantenimientoMensual"),
            Activo = reader.GetBoolean("activo")
        };
    }
}