package pe.edu.pucp.gigachadsys.dao.impl.clases;

import pe.edu.pucp.gigachadsys.dao.ClaseGrupalDAO;
import pe.edu.pucp.gigachadsys.dao.manager.DBManager;
import pe.edu.pucp.gigachadsys.model.ClaseGrupal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClaseGrupalDAOImpl implements ClaseGrupalDAO {

    @Override
    public List<ClaseGrupal> listAll() {
        List<ClaseGrupal> lista = new ArrayList<>();
        String sql = "SELECT * FROM ClaseGrupal WHERE activo = 1";

        try(Connection con = DBManager.getInstance().getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql)) {

            while(rs.next()){
                ClaseGrupal c = new ClaseGrupal(
                        rs.getInt("idClase"),
                        rs.getString("nombreDisciplina"),
                        rs.getString("descripcion"),
                        rs.getInt("duracionMinutos"),
                        rs.getString("nivel")
                );
                c.setActive(rs.getBoolean("activo"));
                lista.add(c);
            }

        } catch(SQLException e){ throw new RuntimeException(e); }

        return lista;
    }

    @Override
    public ClaseGrupal load(Integer id) {
        String sql = "SELECT * FROM ClaseGrupal WHERE idClase=?";

        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return new ClaseGrupal(
                        rs.getInt("idClase"),
                        rs.getString("nombreDisciplina"),
                        rs.getString("descripcion"),
                        rs.getInt("duracionMinutos"),
                        rs.getString("nivel")
                );
            }

        } catch(SQLException e){ throw new RuntimeException(e); }

        return null;
    }

    @Override
    public ClaseGrupal save(ClaseGrupal c) {
        String sql = "INSERT INTO ClaseGrupal(nombreDisciplina, descripcion, duracionMinutos, nivel, activo) VALUES (?,?,?,?,1)";

        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getNombreDisciplina());
            ps.setString(2, c.getDescripcion());
            ps.setInt(3, c.getDuracionMinutos());
            ps.setString(4, c.getNivel());

            ps.executeUpdate();

        } catch(SQLException e){ throw new RuntimeException(e); }

        return c;
    }

    @Override
    public ClaseGrupal update(ClaseGrupal c) {
        String sql = "UPDATE ClaseGrupal SET nombreDisciplina=?, descripcion=?, duracionMinutos=?, nivel=? WHERE idClase=?";

        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getNombreDisciplina());
            ps.setString(2, c.getDescripcion());
            ps.setInt(3, c.getDuracionMinutos());
            ps.setString(4, c.getNivel());
            ps.setInt(5, c.getIdClase());

            ps.executeUpdate();

        } catch(SQLException e){ throw new RuntimeException(e); }

        return c;
    }

    @Override
    public void remove(ClaseGrupal c) {
        String sql = "UPDATE ClaseGrupal SET activo = 0 WHERE idClase=?";

        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, c.getIdClase());
            ps.executeUpdate();

        } catch(SQLException e){ throw new RuntimeException(e); }
    }
}}