package pe.edu.pucp.gigachadsys.dao.impl.usuarios;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import pe.edu.pucp.gigachadsys.inter.usuarios.AdministradorDAO;
import pe.edu.pucp.gigachadsys.model.Administrador;
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
        //SQL QUERY
        String sql = "SELECT idUsuario, nombres, apellidoPaterno, apellidoMaterno,edad,DNI,email,telefono,contrasenia" +
                ",rol,sede,sueldo,cargo from Administrador where idUsuario = ?";

        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try(ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {

                    return new Administrador(
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
        String sql = "INSERT INTO Administrador (idUsuario, nombres, apellidoPaterno, apellidoMaterno, edad, DNI, email"+
                ", telefono, contrasenia, rol, sede, sueldo, cargo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, administrador.getIdUsuario());
            pstmt.setString(2, administrador.getNombres());
            pstmt.setString(3, administrador.getApellidoPaterno());
            pstmt.setString(4, administrador.getApellidoMaterno());
            pstmt.setInt(5, administrador.getEdad());
            pstmt.setInt(6, administrador.getDni());
            pstmt.setString(7, administrador.getEmail());
            pstmt.setInt(8, administrador.getTelefono());
            pstmt.setString(9, administrador.getContrasenia());
            pstmt.setString(10, administrador.getRol());
            pstmt.setString(11, administrador.getSede());
            pstmt.setDouble(12, administrador.getSueldo());
            pstmt.setString(13, administrador.getCargo());

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

        String sql = "UPDATE Administrador SET " +
                "nombres = ?, " +
                "apellidoPaterno = ?, " +
                "apellidoMaterno = ?, " +
                "edad = ?, " +
                "DNI = ?, " +
                "email = ?, " +
                "telefono = ?, " +
                "contrasenia = ?, " +
                "rol = ?, " +
                "sede = ?, " +
                "sueldo = ?, " +
                "cargo = ? " +
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

            // ID para el WHERE
            pstmt.setInt(13, administrador.getIdUsuario());

            pstmt.executeUpdate();

            return administrador;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void remove(Administrador administrador) {
        // TODO: please implement logical removal
        administrador.setActive(false);

        //SQL QUERY
        String sql = "UPDATE Administrador SET activo = ? WHERE idUsuario = ?";

        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setBoolean(1, false);
            pstmt.setInt(2, administrador.getIdUsuario());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
