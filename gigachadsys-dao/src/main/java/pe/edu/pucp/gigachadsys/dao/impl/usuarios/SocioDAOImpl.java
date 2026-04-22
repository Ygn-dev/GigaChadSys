package pe.edu.pucp.gigachadsys.dao.impl.usuarios;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import pe.edu.pucp.gigachadsys.dao.manager.DBManager;
import pe.edu.pucp.gigachadsys.inter.usuarios.SocioDAO;
import pe.edu.pucp.gigachadsys.model.Socio;

public class SocioDAOImpl implements SocioDAO {

    @Override
    public List<Socio> listAll() {
        List<Socio> list = new ArrayList<>();

        String sql = "SELECT idUsuario, nombres, apellidoPaterno, apellidoMaterno, edad, DNI, email, telefono, " +
                "contrasenia, rol, estadoMembresia FROM Socio WHERE activo = 1";

        try (Connection connection = DBManager.getInstance().getConnection();
             Statement stm = connection.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {

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

                list.add(socio);
            }

            return list;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Socio load(Integer id) {

        String sql = "SELECT idUsuario, nombres, apellidoPaterno, apellidoMaterno, edad, DNI, email, telefono, " +
                "contrasenia, rol, estadoMembresia FROM Socio WHERE idUsuario = ?";

        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {

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

        socio.setActive(true);

        String sql = "INSERT INTO Socio (idUsuario, nombres, apellidoPaterno, apellidoMaterno, edad, DNI, email, " +
                "telefono, contrasenia, rol, estadoMembresia, activo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, socio.getIdUsuario());
            pstmt.setString(2, socio.getNombres());
            pstmt.setString(3, socio.getApellidoPaterno());
            pstmt.setString(4, socio.getApellidoMaterno());
            pstmt.setInt(5, socio.getEdad());
            pstmt.setInt(6, socio.getDni());
            pstmt.setString(7, socio.getEmail());
            pstmt.setInt(8, socio.getTelefono());
            pstmt.setString(9, socio.getContrasenia());
            pstmt.setString(10, socio.getRol());
            pstmt.setBoolean(11, socio.isEstadoMembresia());
            pstmt.setBoolean(12, true);

            pstmt.executeUpdate();

            return socio;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Socio update(Socio socio) {

        String sql = "UPDATE Socio SET " +
                "nombres = ?, " +
                "apellidoPaterno = ?, " +
                "apellidoMaterno = ?, " +
                "edad = ?, " +
                "DNI = ?, " +
                "email = ?, " +
                "telefono = ?, " +
                "contrasenia = ?, " +
                "rol = ?, " +
                "estadoMembresia = ? " +
                "WHERE idUsuario = ?";

        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, socio.getNombres());
            pstmt.setString(2, socio.getApellidoPaterno());
            pstmt.setString(3, socio.getApellidoMaterno());
            pstmt.setInt(4, socio.getEdad());
            pstmt.setInt(5, socio.getDni());
            pstmt.setString(6, socio.getEmail());
            pstmt.setInt(7, socio.getTelefono());
            pstmt.setString(8, socio.getContrasenia());
            pstmt.setString(9, socio.getRol());
            pstmt.setBoolean(10, socio.isEstadoMembresia());

            pstmt.setInt(11, socio.getIdUsuario());

            pstmt.executeUpdate();

            return socio;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(Socio socio) {

        String sql = "UPDATE Socio SET activo = ? WHERE idUsuario = ?";

        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setBoolean(1, false);
            pstmt.setInt(2, socio.getIdUsuario());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
