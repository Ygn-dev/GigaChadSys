using GigaChadSysDBManager;
using GigaChadSysModel;
using MySql.Data.MySqlClient;

namespace GigaChadSysDAO;

public class MembresiaBlackDAOImpl : MembresiaBlackDAO
{
    public List<MembresiaBlack> ListAll()
    {
        List<MembresiaBlack> membresias = new();

        string sql = @"SELECT membresia_ID, nombrePlan, costoMantenimientoAnual,
                              cantidadInvitadosPorMes, activo
                       FROM MembresiaBlack
                       WHERE activo = 1";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);
            using MySqlDataReader reader = comando.ExecuteReader();

            while (reader.Read())
            {
                membresias.Add(MapearMembresiaBlack(reader));
            }
        }
        catch (Exception ex)
        {
            throw new Exception("Error al listar membresías black.", ex);
        }

        return membresias;
    }

    public MembresiaBlack? Load(int id)
    {
        MembresiaBlack? membresia = null;

        string sql = @"SELECT membresia_ID, nombrePlan, costoMantenimientoAnual,
                              cantidadInvitadosPorMes, activo
                       FROM MembresiaBlack
                       WHERE membresia_ID = @membresia_ID";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@membresia_ID", id);

            using MySqlDataReader reader = comando.ExecuteReader();

            if (reader.Read())
            {
                membresia = MapearMembresiaBlack(reader);
            }
        }
        catch (Exception ex)
        {
            throw new Exception("Error al cargar membresía black.", ex);
        }

        return membresia;
    }

    public MembresiaBlack Save(MembresiaBlack membresiaBlack)
    {
        membresiaBlack.Activo = true;

        string sql = @"INSERT INTO MembresiaBlack
                       (nombrePlan, costoMantenimientoAnual, cantidadInvitadosPorMes, activo)
                       VALUES
                       (@nombrePlan, @costoMantenimientoAnual, @cantidadInvitadosPorMes, @activo)";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@nombrePlan", membresiaBlack.NombrePlan);
            comando.Parameters.AddWithValue("@costoMantenimientoAnual", membresiaBlack.CostoMantenimientoAnual);
            comando.Parameters.AddWithValue("@cantidadInvitadosPorMes", membresiaBlack.CantidadInvitadosPorMes);
            comando.Parameters.AddWithValue("@activo", membresiaBlack.Activo);

            comando.ExecuteNonQuery();

            membresiaBlack.MembresiaId = (int)comando.LastInsertedId;
        }
        catch (Exception ex)
        {
            throw new Exception("Error al registrar membresía black.", ex);
        }

        return membresiaBlack;
    }

    public MembresiaBlack? Update(MembresiaBlack membresiaBlack)
    {
        string sql = @"UPDATE MembresiaBlack SET
                            nombrePlan = @nombrePlan,
                            costoMantenimientoAnual = @costoMantenimientoAnual,
                            cantidadInvitadosPorMes = @cantidadInvitadosPorMes,
                            activo = @activo
                       WHERE membresia_ID = @membresia_ID";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@nombrePlan", membresiaBlack.NombrePlan);
            comando.Parameters.AddWithValue("@costoMantenimientoAnual", membresiaBlack.CostoMantenimientoAnual);
            comando.Parameters.AddWithValue("@cantidadInvitadosPorMes", membresiaBlack.CantidadInvitadosPorMes);
            comando.Parameters.AddWithValue("@activo", membresiaBlack.Activo);
            comando.Parameters.AddWithValue("@membresia_ID", membresiaBlack.MembresiaId);

            int filas = comando.ExecuteNonQuery();

            return filas > 0 ? membresiaBlack : null;
        }
        catch (Exception ex)
        {
            throw new Exception("Error al actualizar membresía black.", ex);
        }
    }

    public void Remove(MembresiaBlack membresiaBlack)
    {
        membresiaBlack.Activo = false;

        string sql = @"UPDATE MembresiaBlack
                       SET activo = @activo
                       WHERE membresia_ID = @membresia_ID";

        try
        {
            using MySqlConnection conexion = DBManager.Instance.GetConnection();
            using MySqlCommand comando = new(sql, conexion);

            comando.Parameters.AddWithValue("@activo", membresiaBlack.Activo);
            comando.Parameters.AddWithValue("@membresia_ID", membresiaBlack.MembresiaId);

            comando.ExecuteNonQuery();
        }
        catch (Exception ex)
        {
            throw new Exception("Error al eliminar membresía black.", ex);
        }
    }

    private MembresiaBlack MapearMembresiaBlack(MySqlDataReader reader)
    {
        return new MembresiaBlack
        {
            MembresiaId = reader.GetInt32("membresia_ID"),
            NombrePlan = reader.GetString("nombrePlan"),
            CostoMantenimientoAnual = reader.GetDecimal("costoMantenimientoAnual"),
            CantidadInvitadosPorMes = reader.GetInt32("cantidadInvitadosPorMes"),
            Activo = reader.GetBoolean("activo")
        };
    }
}