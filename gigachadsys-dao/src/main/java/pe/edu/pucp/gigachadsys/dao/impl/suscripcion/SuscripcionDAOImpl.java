package pe.edu.pucp.gigachadsys.dao.impl.suscripcion;

import pe.edu.pucp.gigachadsys.dao.manager.DBManager;
import pe.edu.pucp.gigachadsys.inter.suscripcion.SuscripcionDAO;
import pe.edu.pucp.gigachadsys.model.Suscripcion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SuscripcionDAOImpl implements SuscripcionDAO {

    @Override
    public List<Suscripcion> listAll() {
        List<Suscripcion> lista = new ArrayList<>();
        String sql = "SELECT * FROM Suscripcion WHERE activo = 1";

        try(Connection con = DBManager.getInstance().getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql)) {

            while(rs.next()){
                Suscripcion s = new Suscripcion(
                        rs.getInt("idSuscripcion"),
                        rs.getString("estadoMembresia"),
                        rs.getDate("fechaIngreso"),
                        rs.getDate("fechaFinMembresia"),
                        rs.getInt("idPago"),
                        rs.getInt("idUsuario"),
                        rs.getObject("membresia_ID_basic") != null ? rs.getInt("membresia_ID_basic") : null,
                        rs.getObject("membresia_ID_black") != null ? rs.getInt("membresia_ID_black") : null
                );
                s.setActive(rs.getBoolean("activo"));
                lista.add(s);
            }

        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        return lista;
    }

    @Override
    public Suscripcion load(Integer id) {
        String sql = "SELECT * FROM Suscripcion WHERE idSuscripcion=?";

        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                Suscripcion s = new Suscripcion(
                        rs.getInt("idSuscripcion"),
                        rs.getString("estadoMembresia"),
                        rs.getDate("fechaIngreso"),
                        rs.getDate("fechaFinMembresia"),
                        rs.getInt("idPago"),
                        rs.getInt("idUsuario"),
                        rs.getObject("membresia_ID_basic") != null ? rs.getInt("membresia_ID_basic") : null,
                        rs.getObject("membresia_ID_black") != null ? rs.getInt("membresia_ID_black") : null
                );
                s.setActive(rs.getBoolean("activo"));
                return s;
            }

        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Suscripcion save(Suscripcion s) {
        String sql = "INSERT INTO Suscripcion(" +
                "estadoMembresia, fechaIngreso, fechaFinMembresia, idPago, idUsuario, membresia_ID_basic, membresia_ID_black, activo) " +
                "VALUES (?,?,?,?,?,?,?,1)";

        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(2, new java.sql.Date(s.getFechaIngreso().getTime()));
            ps.setDate(3, new java.sql.Date(s.getFechaFinMembresia().getTime()));


            ps.setNull(7, Types.INTEGER);

            ps.executeUpdate();

        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        return s;
    }

    @Override
    public Suscripcion update(Suscripcion s) {
        String sql = "UPDATE Suscripcion SET " +
                "estadoMembresia=?, " +
                "fechaIngreso=?, " +
                "fechaFinMembresia=?, " +
                "idPago=?, " +
                "idUsuario=?, " +
                "membresia_ID_basic=?, " +
                "membresia_ID_black=? " +
                "WHERE idSuscripcion=?";

        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(2, new java.sql.Date(s.getFechaIngreso().getTime()));
            ps.setDate(3, new java.sql.Date(s.getFechaFinMembresia().getTime()));

                ps.setNull(6, Types.INTEGER);

                ps.setNull(7, Types.INTEGER);


            ps.executeUpdate();

        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        return s;
    }

    @Override
    public void remove(Suscripcion s) {
        String sql = "UPDATE Suscripcion SET activo=0 WHERE idSuscripcion=?";

        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.executeUpdate();

        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}