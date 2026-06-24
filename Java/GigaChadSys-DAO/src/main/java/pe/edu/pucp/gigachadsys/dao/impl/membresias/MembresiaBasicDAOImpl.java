package pe.edu.pucp.gigachadsys.dao.impl.membresias;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import pe.edu.pucp.gigachadsys.dao.inter.membresias.MembresiaBasicDAO;
import pe.edu.pucp.gigachadsys.model.membresias.MembresiaBasic;
import pe.edu.pucp.gigachadsys.dao.manager.DBManager;
import pe.edu.pucp.gigachadsys.model.membresias.MembresiaBlack;

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

        String sql= "select membresia_ID, nombrePlan, costoMantenimientoMensual "
                + "FROM MembresiaBlack where = membresia_ID = ?";

        Connection connection = DBManager.getInstance().getConnection();

        try(PreparedStatement pstm = connection.prepareStatement(sql)){
            pstm.setInt(1,id);
            try(ResultSet rs =pstm.executeQuery()){
                if(rs.next()){
                    MembresiaBasic membresia = new MembresiaBasic();
                    membresia.setIdMembresia(rs.getInt(1));
                    membresia.setNombre(rs.getString(2));
                    membresia.setCostoMantenimientoMensual(rs.getDouble(3));
                    membresia.setActiva(rs.getBoolean(4));
                    return membresia;
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public MembresiaBasic save(MembresiaBasic membresia) {

        String sql = "INSERT INTO MembresiaBasic (nombrePlan, costoMantenimientoMensual, activo) " +
                "VALUES (?, ?, ?)";

        try(Connection connection = DBManager.getInstance().getConnection()){
            PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);


            pstm.setString(1, membresia.getNombre());
            pstm.setDouble(2, membresia.getCostoMantenimientoMensual());
            pstm.setBoolean(3, membresia.isActiva());
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
    public MembresiaBasic update(MembresiaBasic membresia) {

        System.out.println("=== Actualizando MembresiaBasic ===");
        System.out.println("ID: " + membresia.getIdMembresia());
        System.out.println("Nombre: " + membresia.getNombre());
        System.out.println("Costo mantenimiento mensual: " + membresia.getCostoMantenimientoMensual());
        System.out.println("Activa: " + membresia.isActiva());
        System.out.println("==============================");


        String sql = "UPDATE MembresiaBasic SET " +
                "nombrePlan = ?, " +
                "costoMantenimientoMensual = ?, " +
                "activo = ? "+
                "WHERE membresia_ID = ?";
        Connection connection = DBManager.getInstance().getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, membresia.getNombre());
            pstmt.setDouble(2, membresia.getCostoMantenimientoMensual());
            pstmt.setBoolean(3, membresia.isActiva());
            pstmt.setInt(4,membresia.getIdMembresia());
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

            pstmt.setBoolean(1, membresia.isActiva());
            pstmt.setInt(2, membresia.getIdMembresia());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}