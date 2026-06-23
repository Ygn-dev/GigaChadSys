package pe.edu.pucp.gigachadsys.dao.impl.membresias;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import pe.edu.pucp.gigachadsys.dao.inter.membresias.MembresiaBlackDAO;
import pe.edu.pucp.gigachadsys.model.membresias.MembresiaBasic;
import pe.edu.pucp.gigachadsys.model.membresias.MembresiaBlack;
import pe.edu.pucp.gigachadsys.dao.manager.DBManager;

public class MembresiaBlackDAOImpl implements MembresiaBlackDAO {

    @Override
    public List<MembresiaBlack> listAll() {
        List<MembresiaBlack> list = new ArrayList<>();

        String sql = "SELECT membresia_ID, nombrePlan, costoMantenimientoAnual, " +
                "cantidadInvitadosPorMes, activo " +
                "FROM MembresiaBlack " +
                "WHERE activo = 1";

        try (
                Connection connection = DBManager.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement(sql);
                ResultSet rs = pstm.executeQuery()
        ) {
            while (rs.next()) {
                MembresiaBlack membresia = new MembresiaBlack();

                membresia.setIdMembresia(rs.getInt("membresia_ID"));
                membresia.setNombre(rs.getString("nombrePlan"));
                membresia.setCostoMantenimientoAnual(
                        rs.getDouble("costoMantenimientoAnual")
                );
                membresia.setCantidadInvitadosPorMes(
                        rs.getInt("cantidadInvitadosPorMes")
                );
                membresia.setActiva(rs.getBoolean("activo"));

                list.add(membresia);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar membresías Black", e);
        }

        return list;
    }

    @Override
    public MembresiaBlack load(Integer id) {
        String sql = "SELECT membresia_ID, nombrePlan, costoMantenimientoAnual, " +
                "cantidadInvitadosPorMes, activo " +
                "FROM MembresiaBlack " +
                "WHERE membresia_ID = ?";

        try (
                Connection connection = DBManager.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement(sql)
        ) {
            pstm.setInt(1, id);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    MembresiaBlack membresia = new MembresiaBlack();

                    membresia.setIdMembresia(rs.getInt("membresia_ID"));
                    membresia.setNombre(rs.getString("nombrePlan"));
                    membresia.setCostoMantenimientoAnual(
                            rs.getDouble("costoMantenimientoAnual")
                    );
                    membresia.setCantidadInvitadosPorMes(
                            rs.getInt("cantidadInvitadosPorMes")
                    );
                    membresia.setActiva(rs.getBoolean("activo"));

                    return membresia;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener membresía Black por ID", e);
        }

        return null;
    }

    @Override
    public MembresiaBlack save(MembresiaBlack membresia) {

        String sql = "INSERT INTO MembresiaBlack (nombrePlan, costoMantenimientoAnual, cantidadInvitadosPorMes, activo) " +
                "VALUES (?, ?, ?, ?)";

        try(Connection connection = DBManager.getInstance().getConnection()){
            PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);


            pstm.setString(1, membresia.getNombre());
            pstm.setDouble(2, membresia.getCostoMantenimientoAnual());
            pstm.setInt(3, membresia.getCantidadInvitadosPorMes());
            pstm.setBoolean(4, membresia.isActiva());
            pstm.executeUpdate();
            int nuevoId;
            try(ResultSet rs = pstm.getGeneratedKeys()){
                if(rs.next()){
                    nuevoId= pstm.getGeneratedKeys().getInt(1);
                    membresia.setIdMembresia(nuevoId);
                }
            }
            return membresia;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public MembresiaBlack update(MembresiaBlack membresia) {

        String sql = "UPDATE MembresiaBlack SET " +
                "nombrePlan = ?, " +
                "costoMantenimientoAnual = ?, " +
                "cantidadInvitadosPorMes = ?," +
                "activo = ?"+
                "WHERE membresia_ID = ?";
        Connection connection = DBManager.getInstance().getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, membresia.getNombre());
            pstmt.setDouble(2, membresia.getCostoMantenimientoAnual());
            pstmt.setInt(3, membresia.getCantidadInvitadosPorMes());
            pstmt.setBoolean(4, membresia.isActiva());
            pstmt.setInt(5,membresia.getIdMembresia());
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

            pstmt.setBoolean(1, membresia.isActiva());
            pstmt.setInt(2, membresia.getIdMembresia());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}