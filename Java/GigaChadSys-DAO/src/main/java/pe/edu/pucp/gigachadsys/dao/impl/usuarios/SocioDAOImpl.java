package pe.edu.pucp.gigachadsys.dao.impl.usuarios;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import pe.edu.pucp.gigachadsys.dao.manager.DBManager;
import pe.edu.pucp.gigachadsys.dao.inter.usuarios.SocioDAO;
import pe.edu.pucp.gigachadsys.model.personas.Socio;

public class SocioDAOImpl implements SocioDAO {

    @Override
    public List<Socio> listAll() {

        List<Socio> list = new ArrayList<>();

        // SQL QUERY
        String sql =
                "SELECT " +
                        "idUsuario, " +
                        "nombres, " +
                        "apellidoPaterno, " +
                        "apellidoMaterno, " +
                        "edad, " +
                        "DNI, " +
                        "email, " +
                        "telefono, " +
                        "contrasenia, " +
                        "rol, " +
                        "estadoMembresia, " +
                        "activo " +
                        "FROM Socio ";

        try (
                Connection connection = DBManager.getInstance().getConnection();
                Statement stm = connection.createStatement();
                ResultSet rs = stm.executeQuery(sql)
        ) {

            while (rs.next()) {

                Socio socio = new Socio(
                        rs.getInt("idUsuario"),
                        rs.getString("nombres"),
                        rs.getString("apellidoPaterno"),
                        rs.getString("apellidoMaterno"),
                        rs.getInt("edad"),
                        rs.getInt("DNI"),
                        rs.getString("email"),
                        rs.getInt("telefono"),
                        rs.getString("contrasenia"),
                        rs.getString("rol"),
                        rs.getBoolean("estadoMembresia")
                );

                socio.setActivo(rs.getBoolean("activo"));

                list.add(socio);
            }

            return list;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Socio load(Integer id) {

        // SQL QUERY
        String sql =
                "SELECT " +
                        "idUsuario, " +
                        "nombres, " +
                        "apellidoPaterno, " +
                        "apellidoMaterno, " +
                        "edad, " +
                        "DNI, " +
                        "email, " +
                        "telefono, " +
                        "contrasenia, " +
                        "rol, " +
                        "estadoMembresia, " +
                        "activo " +
                        "FROM Socio " +
                        "WHERE idUsuario = ?";

        try (
                Connection connection = DBManager.getInstance().getConnection();
                PreparedStatement pstmt = connection.prepareStatement(sql)
        ) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {

                Socio socio = new Socio();

                if (rs.next()) {

                    socio.setIdUsuario(rs.getInt("idUsuario"));
                    socio.setNombres(rs.getString("nombres"));
                    socio.setApellidoPaterno(rs.getString("apellidoPaterno"));
                    socio.setApellidoMaterno(rs.getString("apellidoMaterno"));
                    socio.setEdad(rs.getInt("edad"));
                    socio.setDni(rs.getInt("DNI"));
                    socio.setEmail(rs.getString("email"));
                    socio.setTelefono(rs.getInt("telefono"));
                    socio.setContrasenia(rs.getString("contrasenia"));
                    socio.setRol(rs.getString("rol"));
                    socio.setEstadoMembresia(rs.getBoolean("estadoMembresia"));
                    socio.setActivo(rs.getBoolean("activo"));

                    return socio;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Socio save(Socio socio) {
        // SQL QUERY
        String sql =
                "INSERT INTO Socio (" +
                        "nombres, " +
                        "apellidoPaterno, " +
                        "apellidoMaterno, " +
                        "edad, " +
                        "DNI, " +
                        "email, " +
                        "telefono, " +
                        "contrasenia, " +
                        "rol, " +
                        "estadoMembresia, " +
                        "activo" +
                        ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection connection = DBManager.getInstance().getConnection();
                PreparedStatement pstmt = connection.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {

            pstmt.setString(1, socio.getNombres());
            pstmt.setString(2, socio.getApellidoPaterno());
            pstmt.setString(3, socio.getApellidoMaterno());
            pstmt.setInt(4, socio.getEdad());
            pstmt.setInt(5, socio.getDni());
            pstmt.setString(6, socio.getEmail());
            pstmt.setInt(7, socio.getTelefono());
            pstmt.setString(8, socio.getContrasenia());
            pstmt.setString(9, socio.getRol());
            pstmt.setBoolean(10, socio.getEstadoMembresia());
            pstmt.setBoolean(11, socio.getActivo());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {

                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {

                    if (generatedKeys.next()) {

                        int newId = generatedKeys.getInt(1);
                        socio.setIdUsuario(newId);
                    }
                }
            }

            return socio;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Socio update(Socio socio) {

        // SQL QUERY
        String sql =
                "UPDATE Socio SET " +
                        "nombres = ?, " +
                        "apellidoPaterno = ?, " +
                        "apellidoMaterno = ?, " +
                        "edad = ?, " +
                        "DNI = ?, " +
                        "email = ?, " +
                        "telefono = ?, " +
                        "contrasenia = ?, " +
                        "rol = ?, " +
                        "estadoMembresia = ?, " +
                        "activo = ? " +
                        "WHERE idUsuario = ?";

        try (
                Connection connection = DBManager.getInstance().getConnection();
                PreparedStatement pstmt = connection.prepareStatement(sql)
        ) {

            pstmt.setString(1, socio.getNombres());
            pstmt.setString(2, socio.getApellidoPaterno());
            pstmt.setString(3, socio.getApellidoMaterno());
            pstmt.setInt(4, socio.getEdad());
            pstmt.setInt(5, socio.getDni());
            pstmt.setString(6, socio.getEmail());
            pstmt.setInt(7, socio.getTelefono());
            pstmt.setString(8, socio.getContrasenia());
            pstmt.setString(9, socio.getRol());
            pstmt.setBoolean(10, socio.getEstadoMembresia());
            pstmt.setBoolean(11, socio.getActivo());

            // ID del WHERE
            pstmt.setInt(12, socio.getIdUsuario());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                return socio;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public void remove(Socio socio) {

        // Eliminación lógica
        socio.setActivo(false);

        // SQL QUERY
        String sql =
                "UPDATE Socio " +
                        "SET activo = ? " +
                        "WHERE idUsuario = ?";

        try (
                Connection connection = DBManager.getInstance().getConnection();
                PreparedStatement pstmt = connection.prepareStatement(sql)
        ) {

            pstmt.setBoolean(1, socio.getActivo());
            pstmt.setInt(2, socio.getIdUsuario());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
