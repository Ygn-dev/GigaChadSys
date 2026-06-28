package pe.edu.pucp.gigachadsys.dao.impl.membresias;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pe.edu.pucp.gigachadsys.dao.inter.membresias.MembresiaBlackDAO;
import pe.edu.pucp.gigachadsys.dao.manager.DBManager;
import pe.edu.pucp.gigachadsys.model.membresias.MembresiaBlack;

public class MembresiaBlackDAOImpl implements MembresiaBlackDAO {

    @Override
    public List<MembresiaBlack> listAll() {
        List<MembresiaBlack> list = new ArrayList<>();

        // NO filtrar por activo.
        // Si filtras WHERE activo = 1, nunca podrás volver a activar una membresía inactiva desde admin.
        String sql =
                "SELECT membresia_ID, nombrePlan, costoMantenimientoAnual, cantidadInvitadosPorMes, activo " +
                        "FROM MembresiaBlack";

        try (
                Connection connection = DBManager.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement(sql);
                ResultSet rs = pstm.executeQuery()
        ) {
            while (rs.next()) {
                MembresiaBlack membresia = mapearMembresiaBlack(rs);
                list.add(membresia);
            }

            return list;

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar membresías Black", e);
        }
    }

    @Override
    public MembresiaBlack load(Integer id) {
        String sql =
                "SELECT membresia_ID, nombrePlan, costoMantenimientoAnual, cantidadInvitadosPorMes, activo " +
                        "FROM MembresiaBlack " +
                        "WHERE membresia_ID = ?";

        try (
                Connection connection = DBManager.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement(sql)
        ) {
            pstm.setInt(1, id);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return mapearMembresiaBlack(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener membresía Black por ID", e);
        }

        return null;
    }

    @Override
    public MembresiaBlack save(MembresiaBlack membresia) {
        String sql =
                "INSERT INTO MembresiaBlack " +
                        "(nombrePlan, costoMantenimientoAnual, cantidadInvitadosPorMes, activo) " +
                        "VALUES (?, ?, ?, ?)";

        try (
                Connection connection = DBManager.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            pstm.setString(1, normalizarNombre(membresia.getNombre(), "Membresía Black"));
            pstm.setDouble(2, membresia.getCostoMantenimientoAnual());
            pstm.setInt(3, membresia.getCantidadInvitadosPorMes());
            pstm.setBoolean(4, membresia.isActiva());

            pstm.executeUpdate();

            try (ResultSet rs = pstm.getGeneratedKeys()) {
                if (rs.next()) {
                    membresia.setIdMembresia(rs.getInt(1));
                }
            }

            return membresia;

        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar membresía Black", e);
        }
    }

    @Override
    public MembresiaBlack update(MembresiaBlack membresia) {
        String sql =
                "UPDATE MembresiaBlack SET " +
                        "nombrePlan = ?, " +
                        "costoMantenimientoAnual = ?, " +
                        "cantidadInvitadosPorMes = ?, " +
                        "activo = ? " +
                        "WHERE membresia_ID = ?";

        try (
                Connection connection = DBManager.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement(sql)
        ) {
            pstm.setString(1, normalizarNombre(membresia.getNombre(), "Membresía Black"));
            pstm.setDouble(2, membresia.getCostoMantenimientoAnual());

            int invitados = membresia.getCantidadInvitadosPorMes();
            if (invitados < 0) {
                invitados = 0;
            }

            pstm.setInt(3, invitados);
            pstm.setBoolean(4, membresia.isActiva());
            pstm.setInt(5, membresia.getIdMembresia());

            int filas = pstm.executeUpdate();

            if (filas == 0) {
                throw new RuntimeException("No se encontró la membresía Black con ID " + membresia.getIdMembresia());
            }

            return membresia;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar membresía Black", e);
        }
    }

    @Override
    public void remove(MembresiaBlack membresia) {
        String sql =
                "UPDATE MembresiaBlack SET " +
                        "activo = 0 " +
                        "WHERE membresia_ID = ?";

        try (
                Connection connection = DBManager.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement(sql)
        ) {
            pstm.setInt(1, membresia.getIdMembresia());
            pstm.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al desactivar membresía Black", e);
        }
    }

    private MembresiaBlack mapearMembresiaBlack(ResultSet rs) throws SQLException {
        MembresiaBlack membresia = new MembresiaBlack();

        membresia.setIdMembresia(rs.getInt("membresia_ID"));
        membresia.setNombre(rs.getString("nombrePlan"));
        membresia.setCostoMantenimientoAnual(rs.getDouble("costoMantenimientoAnual"));
        membresia.setCantidadInvitadosPorMes(rs.getInt("cantidadInvitadosPorMes"));
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