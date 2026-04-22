package pe.edu.pucp.gigachadsys.dao.impl.membresias;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import pe.edu.pucp.gigachadsys.inter.membresias.MembresiaBlackDAO;
import pe.edu.pucp.gigachadsys.model.MembresiaBlack;
import pe.edu.pucp.gigachadsys.dao.manager.DBManager;

public class MembresiaBlackDAOImpl implements MembresiaBlackDAO {

    @Override
    public List<MembresiaBlack> listAll() {
        List<MembresiaBlack> list = new ArrayList<>();

        String sql = "SELECT membresia_ID, nombre, costoAnual, cantidadInvitados FROM MembresiaBlack WHERE activo = 1";

        try (Connection connection = DBManager.getInstance().getConnection();
             Statement stm = connection.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {

            while (rs.next()) {

                MembresiaBlack membresia = new MembresiaBlack(
                        rs.getInt("membresia_ID"),
                        rs.getString("nombrePlan"),
                        rs.getDouble("costoMantenimientoAnual"),
                        rs.getInt("cantidadInvitadosPorMes")
                );

                list.add(membresia);
            }

            return list;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MembresiaBlack load(Integer id) {

        String sql = "SELECT membresia_ID, nombre, costoAnual, cantidadInvitados " +
                "FROM MembresiaBlack WHERE membresia_ID = ?";

        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {

                    return new MembresiaBlack(
                            rs.getInt("membresia_ID"),
                            rs.getString("nombre"),
                            rs.getDouble("costoAnual"),
                            rs.getInt("cantidadInvitados")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public MembresiaBlack save(MembresiaBlack membresia) {

        String sql = "INSERT INTO MembresiaBlack (nombre, costoAnual, cantidadInvitados, activo) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, membresia.getNombre());
            pstmt.setDouble(2, membresia.getCostoMantenimientoAnual());
            pstmt.setInt(3, membresia.getCantidadInvitadosPorMes());
            pstmt.setBoolean(4, true);

            pstmt.executeUpdate();

            // recuperar ID generado
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    membresia.setIdMembresia(rs.getInt(1));
                }
            }

            return membresia;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MembresiaBlack update(MembresiaBlack membresia) {

        String sql = "UPDATE MembresiaBlack SET " +
                "nombre = ?, " +
                "costoAnual = ?, " +
                "cantidadInvitados = ? " +
                "WHERE membresia_ID = ?";

        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, membresia.getNombre());
            pstmt.setDouble(2, membresia.getCostoMantenimientoAnual());
            pstmt.setInt(3, membresia.getCantidadInvitadosPorMes());
            pstmt.setInt(4, membresia.getIdMembresia());

            pstmt.executeUpdate();

            return membresia;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(MembresiaBlack membresia) {

        String sql = "UPDATE MembresiaBlack SET activo = ? WHERE membresia_ID = ?";

        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setBoolean(1, false);
            pstmt.setInt(2, membresia.getIdMembresia());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
