package pe.edu.pucp.gigachadsys.dao.impl.membresias;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pe.edu.pucp.gigachadsys.dao.inter.membresias.MembresiaBasicDAO;
import pe.edu.pucp.gigachadsys.dao.manager.DBManager;
import pe.edu.pucp.gigachadsys.model.membresias.MembresiaBasic;

public class MembresiaBasicDAOImpl implements MembresiaBasicDAO {

    @Override
    public List<MembresiaBasic> listAll() {
        List<MembresiaBasic> list = new ArrayList<>();

        // NO filtrar por activo.
        // Si filtras WHERE activo = 1, nunca podrás volver a activar una membresía inactiva desde admin.
        String sql =
                "SELECT membresia_ID, nombrePlan, costoMantenimientoMensual, activo " +
                        "FROM MembresiaBasic";

        try (
                Connection connection = DBManager.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement(sql);
                ResultSet rs = pstm.executeQuery()
        ) {
            while (rs.next()) {
                MembresiaBasic membresia = mapearMembresiaBasic(rs);
                list.add(membresia);
            }

            return list;

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar membresías Basic", e);
        }
    }

    @Override
    public MembresiaBasic load(Integer id) {
        String sql =
                "SELECT membresia_ID, nombrePlan, costoMantenimientoMensual, activo " +
                        "FROM MembresiaBasic " +
                        "WHERE membresia_ID = ?";

        try (
                Connection connection = DBManager.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement(sql)
        ) {
            pstm.setInt(1, id);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return mapearMembresiaBasic(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener membresía Basic por ID", e);
        }

        return null;
    }

    @Override
    public MembresiaBasic save(MembresiaBasic membresia) {
        String sql =
                "INSERT INTO MembresiaBasic " +
                        "(nombrePlan, costoMantenimientoMensual, activo) " +
                        "VALUES (?, ?, ?)";

        try (
                Connection connection = DBManager.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            pstm.setString(1, normalizarNombre(membresia.getNombre(), "Membresía Basic"));
            pstm.setDouble(2, membresia.getCostoMantenimientoMensual());
            pstm.setBoolean(3, membresia.isActiva());

            pstm.executeUpdate();

            try (ResultSet rs = pstm.getGeneratedKeys()) {
                if (rs.next()) {
                    membresia.setIdMembresia(rs.getInt(1));
                }
            }

            return membresia;

        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar membresía Basic", e);
        }
    }

    @Override
    public MembresiaBasic update(MembresiaBasic membresia) {
        String sql =
                "UPDATE MembresiaBasic SET " +
                        "nombrePlan = ?, " +
                        "costoMantenimientoMensual = ?, " +
                        "activo = ? " +
                        "WHERE membresia_ID = ?";

        try (
                Connection connection = DBManager.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement(sql)
        ) {
            pstm.setString(1, normalizarNombre(membresia.getNombre(), "Membresía Basic"));
            pstm.setDouble(2, membresia.getCostoMantenimientoMensual());
            pstm.setBoolean(3, membresia.isActiva());
            pstm.setInt(4, membresia.getIdMembresia());

            int filas = pstm.executeUpdate();

            if (filas == 0) {
                throw new RuntimeException("No se encontró la membresía Basic con ID " + membresia.getIdMembresia());
            }

            return membresia;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar membresía Basic", e);
        }
    }

    @Override
    public void remove(MembresiaBasic membresia) {
        String sql =
                "UPDATE MembresiaBasic SET " +
                        "activo = 0 " +
                        "WHERE membresia_ID = ?";

        try (
                Connection connection = DBManager.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement(sql)
        ) {
            pstm.setInt(1, membresia.getIdMembresia());
            pstm.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al desactivar membresía Basic", e);
        }
    }

    private MembresiaBasic mapearMembresiaBasic(ResultSet rs) throws SQLException {
        MembresiaBasic membresia = new MembresiaBasic();

        membresia.setIdMembresia(rs.getInt("membresia_ID"));
        membresia.setNombre(rs.getString("nombrePlan"));
        membresia.setCostoMantenimientoMensual(rs.getDouble("costoMantenimientoMensual"));
        membresia.setActiva(rs.getBoolean("activo"));

        return membresia;
    }

    private String normalizarNombre(String nombre, String valorPorDefecto) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return valorPorDefecto;
        }

        return nombre.trim();
    }
}