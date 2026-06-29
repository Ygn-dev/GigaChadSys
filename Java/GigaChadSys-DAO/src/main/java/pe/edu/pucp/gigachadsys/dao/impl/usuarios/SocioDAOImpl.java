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
                        "FROM Socio";

        try (
                Connection connection = DBManager.getInstance().getConnection();
                Statement stm = connection.createStatement();
                ResultSet rs = stm.executeQuery(sql)
        ) {
            while (rs.next()) {
                Socio socio = mapearSocio(rs);
                list.add(socio);
            }

            return list;

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar socios", e);
        }
    }

    @Override
    public Socio load(Integer id) {
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
                if (rs.next()) {
                    return mapearSocio(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener socio por ID", e);
        }

        return null;
    }

    @Override
    public Socio save(Socio socio) {
        normalizarDatosAntesDeGuardar(socio);
        socio.setEstadoMembresia(false);
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
                        socio.setIdUsuario(generatedKeys.getInt(1));
                    }
                }
            }

            return socio;

        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar socio", e);
        }
    }

    @Override
    public Socio update(Socio socio) {
        Socio existente = load(socio.getIdUsuario());

        if (existente != null) {
            if (esTextoVacio(socio.getContrasenia())) {
                socio.setContrasenia(existente.getContrasenia());
            }

            if (esTextoVacio(socio.getRol())) {
                socio.setRol(esTextoVacio(existente.getRol()) ? "Socio" : existente.getRol());
            }

            if (socio.getEdad() <= 0) {
                socio.setEdad(existente.getEdad());
            }

            if (esTextoVacio(socio.getApellidoPaterno())) {
                socio.setApellidoPaterno(existente.getApellidoPaterno());
            }

            if (esTextoVacio(socio.getApellidoMaterno())) {
                socio.setApellidoMaterno(existente.getApellidoMaterno());
            }
        }

        normalizarDatosAntesDeGuardar(socio);

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
            pstmt.setInt(12, socio.getIdUsuario());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                return socio;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar socio", e);
        }

        return null;
    }

    @Override
    public void remove(Socio socio) {
        String sql =
                "UPDATE Socio " +
                        "SET activo = 0 " +
                        "WHERE idUsuario = ?";

        try (
                Connection connection = DBManager.getInstance().getConnection();
                PreparedStatement pstmt = connection.prepareStatement(sql)
        ) {
            pstmt.setInt(1, socio.getIdUsuario());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar socio", e);
        }
    }

    private Socio mapearSocio(ResultSet rs) throws SQLException {
        Socio socio = new Socio();

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

    private void normalizarDatosAntesDeGuardar(Socio socio) {
        if (socio.getNombres() != null) {
            socio.setNombres(socio.getNombres().trim());
        }

        if (socio.getApellidoPaterno() != null) {
            socio.setApellidoPaterno(socio.getApellidoPaterno().trim());
        }

        if (socio.getApellidoMaterno() != null) {
            socio.setApellidoMaterno(socio.getApellidoMaterno().trim());
        }

        if (socio.getEmail() != null) {
            socio.setEmail(socio.getEmail().trim());
        }

        if (esTextoVacio(socio.getRol())) {
            socio.setRol("Socio");
        }

        if (esTextoVacio(socio.getContrasenia())) {
            socio.setContrasenia("123456");
        }

        if (socio.getEdad() <= 0) {
            socio.setEdad(18);
        }
    }

    private boolean esTextoVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
}