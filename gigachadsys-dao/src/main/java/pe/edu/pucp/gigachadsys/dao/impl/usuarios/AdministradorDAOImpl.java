package pe.edu.pucp.gigachadsys.dao.impl.usuarios;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import pe.edu.pucp.gigachadsys.dao.inter.usuarios.AdministradorDAO;
import pe.edu.pucp.gigachadsys.model.personas.Administrador;
import pe.edu.pucp.gigachadsys.dao.manager.DBManager;

public class AdministradorDAOImpl implements AdministradorDAO {

    @Override
    public List<Administrador> listAll() {
        List<Administrador> list = new ArrayList<>();

        //SQL QUERY
        String sql = "SELECT idUsuario, nombres, apellidoPaterno, apellidoMaterno,edad,DNI,email,telefono,contrasenia,rol,sede,sueldo,cargo from Administrador active id = 1";

        try(Connection connection = DBManager.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(sql)) {
            while (rs.next()) {

                Administrador administrador = new Administrador(
                        rs.getInt("idUsuario"),
                        rs.getString("nombres"),
                        rs.getString("apellidoMaterno"),
                        rs.getString("apellidoPaterno"),
                        rs.getInt("edad"),
                        rs.getInt("DNI"),
                        rs.getString("email"),
                        rs.getInt("telefono"),
                        rs.getString("contrasenia"),
                        rs.getString("rol"),
                        rs.getString("sede"),
                        rs.getDouble("sueldo")
                );

                list.add(administrador);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Administrador load(Integer id) {
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
                        "Sede, " +
                        "sueldo, " +
                        "cargo, " +
                        "activo " +
                        "FROM Administrador " +
                        "WHERE idUsuario = ?";

        try (
                Connection connection = DBManager.getInstance().getConnection();
                PreparedStatement pstmt = connection.prepareStatement(sql)
        ) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                Administrador administrador = new Administrador();

                if (rs.next()) {
                    administrador.setIdUsuario(rs.getInt("idUsuario"));
                    administrador.setNombres(rs.getString("nombres"));
                    administrador.setApellidoPaterno(rs.getString("apellidoPaterno"));
                    administrador.setApellidoMaterno(rs.getString("apellidoMaterno"));
                    administrador.setEdad(rs.getInt("edad"));
                    administrador.setDni(rs.getInt("DNI"));
                    administrador.setEmail(rs.getString("email"));
                    administrador.setTelefono(rs.getInt("telefono"));
                    administrador.setContrasenia(rs.getString("contrasenia"));
                    administrador.setRol(rs.getString("rol"));
                    administrador.setSede(rs.getString("Sede"));
                    administrador.setSueldo(rs.getDouble("sueldo"));
                    administrador.setCargo(rs.getString("cargo"));
                    administrador.setActivo(rs.getBoolean("activo"));

                    return administrador;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Administrador save(Administrador administrador) {
        administrador.setActive(true);

        //SQL QUERY
        String sql =
        "INSERT INTO Administrador (" +
            "nombres, " +
            "apellidoPaterno, " +
            "apellidoMaterno, " +
            "edad, " +
            "DNI, " +
            "email, " +
            "telefono, " +
            "contrasenia, " +
            "rol, " +
            "Sede, " +
            "sueldo, " +
            "cargo, " +
            "activo" +
            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, administrador.getNombres());
            pstmt.setString(2, administrador.getApellidoPaterno());
            pstmt.setString(3, administrador.getApellidoMaterno());
            pstmt.setInt(4, administrador.getEdad());
            pstmt.setInt(5, administrador.getDni());
            pstmt.setString(6, administrador.getEmail());
            pstmt.setInt(7, administrador.getTelefono());
            pstmt.setString(8, administrador.getContrasenia());
            pstmt.setString(9, administrador.getRol());
            pstmt.setString(10, administrador.getSede());
            pstmt.setDouble(11, administrador.getSueldo());
            pstmt.setString(12, administrador.getCargo());
            pstmt.setBoolean(13, administrador.getActivo());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int newId = generatedKeys.getInt(1);
                        administrador.setIdUsuario(newId);
                    }
                }
            }
            return administrador;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Administrador update(Administrador administrador) {

        String sql =
            "UPDATE Administrador SET " +
                "nombres = ?, " +
                "apellidoPaterno = ?, " +
                "apellidoMaterno = ?, " +
                "edad = ?, " +
                "DNI = ?, " +
                "email = ?, " +
                "telefono = ?, " +
                "contrasenia = ?, " +
                "rol = ?, " +
                "Sede = ?, " +
                "sueldo = ?, " +
                "cargo = ?, " +
                "activo = ? " +
            "WHERE idUsuario = ?";

        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, administrador.getNombres());
            pstmt.setString(2, administrador.getApellidoPaterno());
            pstmt.setString(3, administrador.getApellidoMaterno());
            pstmt.setInt(4, administrador.getEdad());
            pstmt.setInt(5, administrador.getDni());
            pstmt.setString(6, administrador.getEmail());
            pstmt.setInt(7, administrador.getTelefono());
            pstmt.setString(8, administrador.getContrasenia());
            pstmt.setString(9, administrador.getRol());
            pstmt.setString(10, administrador.getSede());
            pstmt.setDouble(11, administrador.getSueldo());
            pstmt.setString(12, administrador.getCargo());
            pstmt.setBoolean(13, administrador.getActivo());

            // ID para el WHERE
            pstmt.setInt(14, administrador.getIdUsuario());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                return administrador;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }


    @Override
    public void remove(Administrador administrador) {

        // Eliminación lógica
        administrador.setActivo(false);

        // SQL QUERY
        String sql =
        "UPDATE Administrador " +
        "SET activo = ? " +
        "WHERE idUsuario = ?";

        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setBoolean(1, administrador.getActivo());
            pstmt.setInt(2, administrador.getIdUsuario());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
