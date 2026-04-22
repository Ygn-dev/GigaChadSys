package pe.edu.pucp.gigachadsys.dao.impl.membresias;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import pe.edu.pucp.gigachadsys.inter.membresias.MembresiaBasicDAO;
import pe.edu.pucp.gigachadsys.model.MembresiaBasic;
import pe.edu.pucp.gigachadsys.dao.manager.DBManager;

public class MembresiaBasicDAOImpl implements MembresiaBasicDAO {

    @Override
    public List<MembresiaBasic> listAll() {
        List<MembresiaBasic> list = new ArrayList<>();

        String sql = "SELECT membresia_ID, nombrePlan, costoMantenimientoMensual FROM MembresiaBasic WHERE activo = 1";

        try (Connection connection = DBManager.getInstance().getConnection();
             Statement stm = connection.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {

            while (rs.next()) {

                MembresiaBasic membresia = new MembresiaBasic(
                        rs.getInt("membresia_ID"),
                        rs.getString("nombrePlan"),
                        rs.getDouble("costoMantenimientoMensual")
                );

                list.add(membresia);
            }

            return list;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MembresiaBasic load(Integer id) {

        String sql = "SELECT membresia_ID, nombrePlan, costoMantenimientoMensual FROM MembresiaBasic WHERE membresia_ID = ?";

        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {

                    return new MembresiaBasic(
                            rs.getInt("membresia_ID"),
                            rs.getString("nombrePlan"),
                            rs.getDouble("costoMantenimientoMensual")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public MembresiaBasic save(MembresiaBasic membresia) {

        String sql = "INSERT INTO MembresiaBasic (nombrePlan, costoMantenimientoMensual, activo) VALUES (?, ?, ?)";

        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, membresia.getNombre());
            pstmt.setDouble(2, membresia.getCostoMantenimientoMensual());
            pstmt.setBoolean(3, true);

            pstmt.executeUpdate();

            // recuperar ID generado (importante)
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
    public MembresiaBasic update(MembresiaBasic membresia) {

        String sql = "UPDATE MembresiaBasic SET nombrePlan = ?, costoMantenimientoMensual = ? WHERE membresia_ID = ?";

        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, membresia.getNombre());
            pstmt.setDouble(2, membresia.getCostoMantenimientoMensual());
            pstmt.setInt(3, membresia.getIdMembresia());

            pstmt.executeUpdate();

            return membresia;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(MembresiaBasic membresia) {

        String sql = "UPDATE MembresiaBasic SET activo = ? WHERE membresia_ID = ?";

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
