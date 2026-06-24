package pe.edu.pucp.gigachadsys.dao.impl.suscripcion;

import pe.edu.pucp.gigachadsys.dao.manager.DBManager;
import pe.edu.pucp.gigachadsys.dao.inter.suscripcion.SuscripcionDAO;
import pe.edu.pucp.gigachadsys.model.membresias.Suscripcion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SuscripcionDAOImpl implements SuscripcionDAO {

    @Override
    public List<Suscripcion> listAll() {
        List<Suscripcion> lista = new ArrayList<>();
        String sql = "SELECT * FROM Suscripcion";

        try (
                Connection con = DBManager.getInstance().getConnection();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql)
        ) {
            while (rs.next()) {
                lista.add(mapearSuscripcion(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    @Override
    public Suscripcion load(Integer id) {
        String sql = "SELECT * FROM Suscripcion WHERE idSuscripcion = ?";

        try (
                Connection con = DBManager.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearSuscripcion(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Suscripcion save(Suscripcion s) {
        String sql =
                "INSERT INTO Suscripcion(" +
                        "estadoMembresia, " +
                        "fechaIngreso, " +
                        "fechaFinMembresia, " +
                        "idPago, " +
                        "idUsuario, " +
                        "membresia_ID_basic, " +
                        "membresia_ID_black, " +
                        "activo" +
                        ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection con = DBManager.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, s.getEstadoMembresia());
            ps.setDate(2, obtenerFechaIngresoSQL(s));
            ps.setDate(3, obtenerFechaFinSQL(s));
            ps.setInt(4, s.getIdPago());
            ps.setInt(5, s.getIdUsuario());

            if (s.getIdMembresiaBasic() != null) {
                ps.setInt(6, s.getIdMembresiaBasic());
            } else {
                ps.setNull(6, Types.INTEGER);
            }

            if (s.getIdMembresiaBlack() != null) {
                ps.setInt(7, s.getIdMembresiaBlack());
            } else {
                ps.setNull(7, Types.INTEGER);
            }

            ps.setBoolean(8, s.isActivo());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    s.setIdSuscripcion(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return s;
    }

    @Override
    public Suscripcion update(Suscripcion s) {
        String sql =
                "UPDATE Suscripcion SET " +
                        "estadoMembresia = ?, " +
                        "fechaIngreso = ?, " +
                        "fechaFinMembresia = ?, " +
                        "idPago = ?, " +
                        "idUsuario = ?, " +
                        "membresia_ID_basic = ?, " +
                        "membresia_ID_black = ?, " +
                        "activo = ? " +
                        "WHERE idSuscripcion = ?";

        try (
                Connection con = DBManager.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, s.getEstadoMembresia());
            ps.setDate(2, obtenerFechaIngresoSQL(s));
            ps.setDate(3, obtenerFechaFinSQL(s));
            ps.setInt(4, s.getIdPago());
            ps.setInt(5, s.getIdUsuario());

            if (s.getIdMembresiaBasic() != null) {
                ps.setInt(6, s.getIdMembresiaBasic());
            } else {
                ps.setNull(6, Types.INTEGER);
            }

            if (s.getIdMembresiaBlack() != null) {
                ps.setInt(7, s.getIdMembresiaBlack());
            } else {
                ps.setNull(7, Types.INTEGER);
            }

            ps.setBoolean(8, s.isActivo());
            ps.setInt(9, s.getIdSuscripcion());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return s;
    }

    @Override
    public void remove(Suscripcion s) {
        String sql = "UPDATE Suscripcion SET activo = 0 WHERE idSuscripcion = ?";

        try (
                Connection con = DBManager.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, s.getIdSuscripcion());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Suscripcion mapearSuscripcion(ResultSet rs) throws SQLException {
        java.sql.Date fechaIngresoSQL = rs.getDate("fechaIngreso");
        java.sql.Date fechaFinSQL = rs.getDate("fechaFinMembresia");

        Suscripcion s = new Suscripcion(
                rs.getInt("idSuscripcion"),
                rs.getString("estadoMembresia"),
                fechaIngresoSQL,
                fechaFinSQL,
                rs.getInt("idPago"),
                rs.getInt("idUsuario"),
                rs.getObject("membresia_ID_basic") != null ? rs.getInt("membresia_ID_basic") : null,
                rs.getObject("membresia_ID_black") != null ? rs.getInt("membresia_ID_black") : null
        );

        if (fechaIngresoSQL != null) {
            s.setFechaIngresoTexto(fechaIngresoSQL.toString());
        }

        if (fechaFinSQL != null) {
            s.setFechaFinMembresiaTexto(fechaFinSQL.toString());
        }

        s.setActivo(rs.getBoolean("activo"));

        return s;
    }

    private java.sql.Date obtenerFechaIngresoSQL(Suscripcion s) {
        String fechaTexto = s.getFechaIngresoTexto();

        if (fechaTexto != null && !fechaTexto.isBlank()) {
            return java.sql.Date.valueOf(fechaTexto);
        }

        if (s.getFechaIngreso() == null) {
            return null;
        }

        return new java.sql.Date(s.getFechaIngreso().getTime());
    }

    private java.sql.Date obtenerFechaFinSQL(Suscripcion s) {
        String fechaTexto = s.getFechaFinMembresiaTexto();

        if (fechaTexto != null && !fechaTexto.isBlank()) {
            return java.sql.Date.valueOf(fechaTexto);
        }

        if (s.getFechaFinMembresia() == null) {
            return null;
        }

        return new java.sql.Date(s.getFechaFinMembresia().getTime());
    }
}