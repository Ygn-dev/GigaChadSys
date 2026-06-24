package pe.edu.pucp.gigachadsys.dao.impl.clases;

import pe.edu.pucp.gigachadsys.dao.manager.DBManager;
import pe.edu.pucp.gigachadsys.dao.inter.clases.ClaseGrupalDAO;
import pe.edu.pucp.gigachadsys.model.clases.ClaseGrupal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClaseGrupalDAOImpl implements ClaseGrupalDAO {

    @Override
    public List<ClaseGrupal> listAll() {
        List<ClaseGrupal> lista = new ArrayList<>();

        String sql =
                "SELECT idClase, nombreDisciplina, descripcion, duracionMinutos, nivel, activo " +
                        "FROM ClaseGrupal";

        try (
                Connection con = DBManager.getInstance().getConnection();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql)
        ) {
            while (rs.next()) {
                lista.add(mapearClaseGrupal(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    @Override
    public ClaseGrupal load(Integer id) {
        String sql =
                "SELECT idClase, nombreDisciplina, descripcion, duracionMinutos, nivel, activo " +
                        "FROM ClaseGrupal " +
                        "WHERE idClase = ?";

        try (
                Connection con = DBManager.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearClaseGrupal(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public ClaseGrupal save(ClaseGrupal c) {
        String sql =
                "INSERT INTO ClaseGrupal(nombreDisciplina, descripcion, duracionMinutos, nivel, activo) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection con = DBManager.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, c.getNombreDisciplina());
            ps.setString(2, c.getDescripcion());
            ps.setInt(3, c.getDuracionMinutos());
            ps.setString(4, c.getNivel());
            ps.setBoolean(5, c.getActivo());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        c.setIdClase(generatedKeys.getInt(1));
                    }
                }
            }

            return c;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ClaseGrupal update(ClaseGrupal c) {
        String sql =
                "UPDATE ClaseGrupal SET " +
                        "nombreDisciplina = ?, " +
                        "descripcion = ?, " +
                        "duracionMinutos = ?, " +
                        "nivel = ?, " +
                        "activo = ? " +
                        "WHERE idClase = ?";

        try (
                Connection con = DBManager.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, c.getNombreDisciplina());
            ps.setString(2, c.getDescripcion());
            ps.setInt(3, c.getDuracionMinutos());
            ps.setString(4, c.getNivel());
            ps.setBoolean(5, c.getActivo());
            ps.setInt(6, c.getIdClase());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return c;
    }

    @Override
    public void remove(ClaseGrupal c) {
        String sql = "UPDATE ClaseGrupal SET activo = 0 WHERE idClase = ?";

        try (
                Connection con = DBManager.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, c.getIdClase());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ClaseGrupal mapearClaseGrupal(ResultSet rs) throws SQLException {
        ClaseGrupal c = new ClaseGrupal(
                rs.getInt("idClase"),
                rs.getString("nombreDisciplina"),
                rs.getString("descripcion"),
                rs.getInt("duracionMinutos"),
                rs.getString("nivel")
        );

        c.setActivo(rs.getBoolean("activo"));

        return c;
    }
}