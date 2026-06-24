package pe.edu.pucp.gigachadsys.dao.impl.usuarios;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import pe.edu.pucp.gigachadsys.dao.inter.usuarios.EntrenadorDAO;
import pe.edu.pucp.gigachadsys.model.personas.Entrenador;
import pe.edu.pucp.gigachadsys.dao.manager.DBManager;

public class EntrenadorDAOImpl implements EntrenadorDAO {

    @Override
    public List<Entrenador> listAll() {
        List<Entrenador> list = new ArrayList<>();

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
                        "Especialidad, " +
                        "sueldo, " +
                        "tiempoTrabajo, " +
                        "activo " +
                        "FROM Entrenador";

        try (
                Connection connection = DBManager.getInstance().getConnection();
                Statement stm = connection.createStatement();
                ResultSet rs = stm.executeQuery(sql)
        ) {
            while (rs.next()) {
                list.add(mapearEntrenador(rs));
            }

            return list;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Entrenador load(Integer id) {
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
                        "Especialidad, " +
                        "sueldo, " +
                        "tiempoTrabajo, " +
                        "activo " +
                        "FROM Entrenador " +
                        "WHERE idUsuario = ?";

        try (
                Connection connection = DBManager.getInstance().getConnection();
                PreparedStatement pstmt = connection.prepareStatement(sql)
        ) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearEntrenador(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Entrenador save(Entrenador entrenador) {
        entrenador.setActivo(true);

        String sql =
                "INSERT INTO Entrenador (" +
                        "nombres, " +
                        "apellidoPaterno, " +
                        "apellidoMaterno, " +
                        "edad, " +
                        "DNI, " +
                        "email, " +
                        "telefono, " +
                        "contrasenia, " +
                        "rol, " +
                        "Especialidad, " +
                        "sueldo, " +
                        "tiempoTrabajo, " +
                        "activo" +
                        ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection connection = DBManager.getInstance().getConnection();
                PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            pstmt.setString(1, entrenador.getNombres());
            pstmt.setString(2, entrenador.getApellidoPaterno());
            pstmt.setString(3, entrenador.getApellidoMaterno());
            pstmt.setInt(4, entrenador.getEdad());
            pstmt.setInt(5, entrenador.getDni());
            pstmt.setString(6, entrenador.getEmail());
            pstmt.setInt(7, entrenador.getTelefono());
            pstmt.setString(8, entrenador.getContrasenia());
            pstmt.setString(9, entrenador.getRol() == null || entrenador.getRol().isBlank() ? "Entrenador" : entrenador.getRol());
            pstmt.setString(10, entrenador.getEspecialidad());
            pstmt.setDouble(11, entrenador.getSueldo());
            pstmt.setTime(12, entrenador.getTiempoTrabajado());
            pstmt.setBoolean(13, entrenador.getActivo() != null ? entrenador.getActivo() : true);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entrenador.setIdUsuario(generatedKeys.getInt(1));
                    }
                }
            }

            return entrenador;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Entrenador update(Entrenador entrenador) {
        String sql =
                "UPDATE Entrenador SET " +
                        "nombres = ?, " +
                        "apellidoPaterno = ?, " +
                        "apellidoMaterno = ?, " +
                        "edad = ?, " +
                        "DNI = ?, " +
                        "email = ?, " +
                        "telefono = ?, " +
                        "contrasenia = COALESCE(NULLIF(?, ''), contrasenia), " +
                        "rol = ?, " +
                        "Especialidad = ?, " +
                        "sueldo = ?, " +
                        "tiempoTrabajo = ?, " +
                        "activo = ? " +
                        "WHERE idUsuario = ?";

        try (
                Connection connection = DBManager.getInstance().getConnection();
                PreparedStatement pstmt = connection.prepareStatement(sql)
        ) {
            pstmt.setString(1, entrenador.getNombres());
            pstmt.setString(2, entrenador.getApellidoPaterno());
            pstmt.setString(3, entrenador.getApellidoMaterno());
            pstmt.setInt(4, entrenador.getEdad());
            pstmt.setInt(5, entrenador.getDni());
            pstmt.setString(6, entrenador.getEmail());
            pstmt.setInt(7, entrenador.getTelefono());
            pstmt.setString(8, entrenador.getContrasenia());
            pstmt.setString(9, entrenador.getRol() == null || entrenador.getRol().isBlank() ? "Entrenador" : entrenador.getRol());
            pstmt.setString(10, entrenador.getEspecialidad());
            pstmt.setDouble(11, entrenador.getSueldo());
            pstmt.setTime(12, entrenador.getTiempoTrabajado());
            pstmt.setBoolean(13, entrenador.getActivo() != null ? entrenador.getActivo() : true);
            pstmt.setInt(14, entrenador.getIdUsuario());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                return entrenador;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public void remove(Entrenador entrenador) {
        entrenador.setActivo(false);

        String sql =
                "UPDATE Entrenador " +
                        "SET activo = ? " +
                        "WHERE idUsuario = ?";

        try (
                Connection connection = DBManager.getInstance().getConnection();
                PreparedStatement pstmt = connection.prepareStatement(sql)
        ) {
            pstmt.setBoolean(1, entrenador.getActivo());
            pstmt.setInt(2, entrenador.getIdUsuario());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Entrenador mapearEntrenador(ResultSet rs) throws SQLException {
        Entrenador entrenador = new Entrenador();

        entrenador.setIdUsuario(rs.getInt("idUsuario"));
        entrenador.setNombres(rs.getString("nombres"));
        entrenador.setApellidoPaterno(rs.getString("apellidoPaterno"));
        entrenador.setApellidoMaterno(rs.getString("apellidoMaterno"));
        entrenador.setEdad(rs.getInt("edad"));
        entrenador.setDni(rs.getInt("DNI"));
        entrenador.setEmail(rs.getString("email"));
        entrenador.setTelefono(rs.getInt("telefono"));
        entrenador.setContrasenia(rs.getString("contrasenia"));
        entrenador.setRol(rs.getString("rol"));
        entrenador.setEspecialidad(rs.getString("Especialidad"));
        entrenador.setSueldo(rs.getDouble("sueldo"));
        entrenador.setTiempoTrabajado(rs.getTime("tiempoTrabajo"));
        entrenador.setActivo(rs.getBoolean("activo"));

        return entrenador;
    }
}