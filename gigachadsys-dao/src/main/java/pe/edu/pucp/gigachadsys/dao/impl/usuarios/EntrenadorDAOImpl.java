package pe.edu.pucp.gigachadsys.dao.impl.usuarios;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import pe.edu.pucp.gigachadsys.inter.usuarios.EntrenadorDAO;
import pe.edu.pucp.gigachadsys.model.Entrenador;
import pe.edu.pucp.gigachadsys.dao.manager.DBManager;


public class EntrenadorDAOImpl implements EntrenadorDAO {

    @Override
    public List<Entrenador> listAll() {
        List<Entrenador> list = new ArrayList<>();

        String sql = "SELECT idUsuario, nombres, apellidoPaterno, apellidoMaterno, edad, DNI, email, telefono, "+
                "contrasenia, rol, especialidad, sueldo, tiempoTrabajo FROM Entrenador WHERE activo = 1";

        try (Connection connection = DBManager.getInstance().getConnection();
             Statement stm = connection.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {

            while (rs.next()) {

                Entrenador entrenador = new Entrenador(
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
                        rs.getString("especialidad"),
                        rs.getDouble("sueldo"),
                        rs.getTime("tiempoTrabajo")
                );

                list.add(entrenador);
            }

            return list;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Entrenador load(Integer id) {

        String sql = "SELECT idUsuario, nombres, apellidoPaterno, apellidoMaterno, edad, DNI, email, telefono, +" +
                "contrasenia, rol, especialidad, sueldo, tiempoTrabajo FROM Entrenador WHERE idUsuario = ?";

        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {

                    return new Entrenador(
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
                            rs.getString("especialidad"),
                            rs.getDouble("sueldo"),
                            rs.getTime("tiempoTrabajo")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }


    @Override
    public Entrenador save(Entrenador entrenador) {

        entrenador.setActive(true);

        String sql = "INSERT INTO Entrenador (idUsuario, nombres, apellidoPaterno, apellidoMaterno, edad, DNI, email, " +
                "telefono, contrasenia, rol, especialidad, sueldo, tiempoTrabajo, activo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, entrenador.getIdUsuario());
            pstmt.setString(2, entrenador.getNombres());
            pstmt.setString(3, entrenador.getApellidoPaterno());
            pstmt.setString(4, entrenador.getApellidoMaterno());
            pstmt.setInt(5, entrenador.getEdad());
            pstmt.setInt(6, entrenador.getDni());
            pstmt.setString(7, entrenador.getEmail());
            pstmt.setInt(8, entrenador.getTelefono());
            pstmt.setString(9, entrenador.getContrasenia());
            pstmt.setString(10, entrenador.getRol());
            pstmt.setString(11, entrenador.getEspecialidad());
            pstmt.setDouble(12, entrenador.getSueldo());
            pstmt.setTime(13, entrenador.getTiempoTrabajado());
            pstmt.setBoolean(14, true);

            pstmt.executeUpdate();

            return entrenador;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Entrenador update(Entrenador entrenador) {

        String sql = "UPDATE Entrenador SET " +
                "nombres = ?, " +
                "apellidoPaterno = ?, " +
                "apellidoMaterno = ?, " +
                "edad = ?, " +
                "DNI = ?, " +
                "email = ?, " +
                "telefono = ?, " +
                "contrasenia = ?, " +
                "rol = ?, " +
                "especialidad = ?, " +
                "sueldo = ?, " +
                "tiempoTrabajo = ? " +
                "WHERE idUsuario = ?";

        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, entrenador.getNombres());
            pstmt.setString(2, entrenador.getApellidoPaterno());
            pstmt.setString(3, entrenador.getApellidoMaterno());
            pstmt.setInt(4, entrenador.getEdad());
            pstmt.setInt(5, entrenador.getDni());
            pstmt.setString(6, entrenador.getEmail());
            pstmt.setInt(7, entrenador.getTelefono());
            pstmt.setString(8, entrenador.getContrasenia());
            pstmt.setString(9, entrenador.getRol());
            pstmt.setString(10, entrenador.getEspecialidad());
            pstmt.setDouble(11, entrenador.getSueldo());
            pstmt.setTime(12, entrenador.getTiempoTrabajado());

            pstmt.setInt(13, entrenador.getIdUsuario());

            pstmt.executeUpdate();

            return entrenador;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void remove(Entrenador entrenador) {

        String sql = "UPDATE Entrenador SET activo = ? WHERE idUsuario = ?";

        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setBoolean(1, false);
            pstmt.setInt(2, entrenador.getIdUsuario());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
