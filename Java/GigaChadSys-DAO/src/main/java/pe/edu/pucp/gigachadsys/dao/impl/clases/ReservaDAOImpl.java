package pe.edu.pucp.gigachadsys.dao.impl.clases;

import pe.edu.pucp.gigachadsys.dao.manager.DBManager;
import pe.edu.pucp.gigachadsys.dao.inter.clases.ReservaDAO;
import pe.edu.pucp.gigachadsys.dao.inter.clases.SesionClaseDAO;
import pe.edu.pucp.gigachadsys.model.clases.Reserva;
import pe.edu.pucp.gigachadsys.model.clases.SesionClase;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservaDAOImpl implements ReservaDAO {

    @Override
    public List<Reserva> listAll() {
        List<Reserva> lista = new ArrayList<>();

        String sql =
                "SELECT idReserva, fechaHoraReserva, asistio, idSesion, idUsuario, activo " +
                        "FROM Reserva";

        SesionClaseDAO sesionDAO = new SesionClaseDAOImpl();

        Map<Integer, SesionClase> sesionMap = new HashMap<>();
        for (SesionClase s : sesionDAO.listAll()) {
            sesionMap.put(s.getIdSesion(), s);
        }

        try (
                Connection con = DBManager.getInstance().getConnection();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql)
        ) {
            while (rs.next()) {
                Reserva r = mapearReserva(rs);
                r.setSesionClase(sesionMap.get(rs.getInt("idSesion")));
                lista.add(r);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    @Override
    public Reserva load(Integer id) {
        String sql =
                "SELECT idReserva, fechaHoraReserva, asistio, idSesion, idUsuario, activo " +
                        "FROM Reserva " +
                        "WHERE idReserva = ?";

        SesionClaseDAO sesionDAO = new SesionClaseDAOImpl();

        try (
                Connection con = DBManager.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Reserva r = mapearReserva(rs);
                    r.setSesionClase(sesionDAO.load(rs.getInt("idSesion")));
                    return r;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Reserva save(Reserva r) {
        int idSesion = r.getSesionClase() != null ? r.getSesionClase().getIdSesion() : 0;
        int idUsuario = r.getIdUsuario();

        if (idUsuario <= 0) {
            throw new RuntimeException("No se recibió el socio de la reserva.");
        }

        if (idSesion <= 0) {
            throw new RuntimeException("No se recibió la sesión de clase de la reserva.");
        }

        validarSesionReservable(idSesion);
        validarReservaDuplicadaPorClase(idUsuario, idSesion);
        validarCuposDisponibles(idSesion);

        String sql =
                "INSERT INTO Reserva(fechaHoraReserva, asistio, idSesion, idUsuario, activo) " +
                        "VALUES (?, ?, ?, ?, 1)";

        try (
                Connection con = DBManager.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            Timestamp fechaReserva = r.getFechaHoraReserva() != null
                    ? r.getFechaHoraReserva()
                    : new Timestamp(System.currentTimeMillis());

            ps.setTimestamp(1, fechaReserva);
            ps.setBoolean(2, r.isAsistio());
            ps.setInt(3, idSesion);
            ps.setInt(4, idUsuario);

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        r.setIdReserva(generatedKeys.getInt(1));
                    }
                }
            }

            r.setFechaHoraReserva(fechaReserva);
            r.setActivo(true);

            return r;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Reserva update(Reserva r) {
        String sql =
                "UPDATE Reserva SET " +
                        "fechaHoraReserva = ?, " +
                        "asistio = ?, " +
                        "idSesion = ?, " +
                        "idUsuario = ?, " +
                        "activo = ? " +
                        "WHERE idReserva = ?";

        try (
                Connection con = DBManager.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            Timestamp fechaReserva = r.getFechaHoraReserva() != null
                    ? r.getFechaHoraReserva()
                    : new Timestamp(System.currentTimeMillis());

            int idSesion = r.getSesionClase() != null ? r.getSesionClase().getIdSesion() : 0;

            ps.setTimestamp(1, fechaReserva);
            ps.setBoolean(2, r.isAsistio());
            ps.setInt(3, idSesion);
            ps.setInt(4, r.getIdUsuario());
            ps.setBoolean(5, r.getActivo());
            ps.setInt(6, r.getIdReserva());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return r;
    }

    @Override
    public void remove(Reserva r) {
        String sql = "UPDATE Reserva SET activo = 0 WHERE idReserva = ?";

        try (
                Connection con = DBManager.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, r.getIdReserva());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Reserva mapearReserva(ResultSet rs) throws SQLException {
        Reserva r = new Reserva(
                rs.getInt("idReserva"),
                rs.getTimestamp("fechaHoraReserva"),
                rs.getBoolean("asistio"),
                rs.getInt("idSesion"),
                rs.getInt("idUsuario")
        );

        r.setActivo(rs.getBoolean("activo"));

        return r;
    }

    private void validarSesionReservable(int idSesion) {
        String sql =
                "SELECT activo, horaFin " +
                        "FROM SesionClase " +
                        "WHERE idSesion = ?";

        try (
                Connection con = DBManager.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, idSesion);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new RuntimeException("La sesión seleccionada no existe.");
                }

                boolean activa = rs.getBoolean("activo");
                Timestamp horaFin = rs.getTimestamp("horaFin");

                if (!activa) {
                    throw new RuntimeException("La sesión seleccionada está cancelada.");
                }

                if (horaFin != null && horaFin.before(new Timestamp(System.currentTimeMillis()))) {
                    throw new RuntimeException("No se puede reservar una sesión vencida.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void validarReservaDuplicadaPorClase(int idUsuario, int idSesionNueva) {
        String sql =
                "SELECT COUNT(*) AS total " +
                        "FROM Reserva r " +
                        "INNER JOIN SesionClase sesionExistente ON sesionExistente.idSesion = r.idSesion " +
                        "INNER JOIN SesionClase sesionNueva ON sesionNueva.idSesion = ? " +
                        "WHERE r.idUsuario = ? " +
                        "AND r.activo = 1 " +
                        "AND sesionExistente.activo = 1 " +
                        "AND sesionExistente.horaFin >= NOW() " +
                        "AND sesionExistente.idClase = sesionNueva.idClase";

        try (
                Connection con = DBManager.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, idSesionNueva);
            ps.setInt(2, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getInt("total") > 0) {
                    throw new RuntimeException("El socio ya tiene una reserva activa para esta clase.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void validarCuposDisponibles(int idSesion) {
        String sql =
                "SELECT " +
                        "COALESCE(salon.aforoMaximo, sesion.cuposDisponibles) AS capacidad, " +
                        "COUNT(reserva.idReserva) AS inscritos " +
                        "FROM SesionClase sesion " +
                        "LEFT JOIN Salon salon ON salon.idSalon = sesion.idSalon " +
                        "LEFT JOIN Reserva reserva ON reserva.idSesion = sesion.idSesion AND reserva.activo = 1 " +
                        "WHERE sesion.idSesion = ? " +
                        "GROUP BY sesion.idSesion, salon.aforoMaximo, sesion.cuposDisponibles";

        try (
                Connection con = DBManager.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, idSesion);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int capacidad = rs.getInt("capacidad");
                    int inscritos = rs.getInt("inscritos");

                    if (capacidad > 0 && inscritos >= capacidad) {
                        throw new RuntimeException("No hay cupos disponibles para esta sesión.");
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}