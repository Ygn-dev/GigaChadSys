package pe.edu.pucp.gigachadsys.dao.impl.clases;

import pe.edu.pucp.gigachadsys.dao.SalonDAO;
import pe.edu.pucp.gigachadsys.model.Salon;

import java.sql.Connection;

import pe.edu.pucp.gigachadsys.dao.SalonDAO;
import pe.edu.pucp.gigachadsys.dao.manager.DBManager;
import pe.edu.pucp.gigachadsys.model.Salon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalonDAOImpl implements SalonDAO {

    @Override
    public List<Salon> listAll() {
        List<Salon> lista = new ArrayList<>();
        String sql = "SELECT * FROM Salon WHERE activo = 1";

        try(Connection con = DBManager.getInstance().getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql)) {

            while(rs.next()){
                Salon s = new Salon(
                        rs.getInt("idSalon"),
                        rs.getString("nombreSalon"),
                        rs.getInt("aforoMaximo")
                );
                s.setActive(rs.getBoolean("activo"));
                lista.add(s);
            }

        } catch(SQLException e){ throw new RuntimeException(e); }

        return lista;
    }

    @Override
    public Salon load(Integer id) {
        String sql = "SELECT * FROM Salon WHERE idSalon=?";

        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return new Salon(
                        rs.getInt("idSalon"),
                        rs.getString("nombreSalon"),
                        rs.getInt("aforoMaximo")
                );
            }

        } catch(SQLException e){ throw new RuntimeException(e); }

        return null;
    }

    @Override
    public Salon save(Salon s) {
        String sql = "INSERT INTO Salon(nombreSalon, aforoMaximo, activo) VALUES (?,?,1)";

        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, s.getNombreSalon());
            ps.setInt(2, s.getAforoMaximo());
            ps.executeUpdate();

        } catch(SQLException e){ throw new RuntimeException(e); }

        return s;
    }

    @Override
    public Salon update(Salon s) {
        String sql = "UPDATE Salon SET nombreSalon=?, aforoMaximo=? WHERE idSalon=?";

        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, s.getNombreSalon());
            ps.setInt(2, s.getAforoMaximo());
            ps.setInt(3, s.getIdSalon());

            ps.executeUpdate();

        } catch(SQLException e){ throw new RuntimeException(e); }

        return s;
    }

    @Override
    public void remove(Salon s) {
        String sql = "UPDATE Salon SET activo=0 WHERE idSalon=?";

        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, s.getIdSalon());
            ps.executeUpdate();

        } catch(SQLException e){ throw new RuntimeException(e); }
    }
}