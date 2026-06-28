package pe.edu.pucp.gigachadsys.dao.impl.pagos;

import pe.edu.pucp.gigachadsys.dao.inter.pagos.PagoDAO;
import pe.edu.pucp.gigachadsys.dao.manager.DBManager;
import pe.edu.pucp.gigachadsys.model.pagos.Pago;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PagoDAOImpl implements PagoDAO {

    @Override
    public List<Pago> listAll() {
        List<Pago> lista = new ArrayList<>();

        /*
         * IMPORTANTE:
         * NO filtramos por activo = 1.
         *
         * En este sistema ustedes están usando:
         * activo = 1  -> pago pagado
         * activo = 0  -> pago pendiente
         *
         * Si ponemos WHERE activo = 1, los pagos pendientes nunca aparecerían.
         */
        String sql =
                "SELECT idPago, fechaPago, montoTotal, tipo, idMetodoPago, activo " +
                        "FROM Pago " +
                        "ORDER BY fechaPago DESC, idPago DESC";

        try (
                Connection con = DBManager.getInstance().getConnection();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql)
        ) {
            while (rs.next()) {
                Pago p = new Pago();

                p.setIdPago(rs.getInt("idPago"));
                p.setFechaPago(rs.getDate("fechaPago"));
                p.setMonto(rs.getDouble("montoTotal"));
                p.setTipo(rs.getString("tipo"));
                p.setMetodoPago(rs.getInt("idMetodoPago"));
                p.setActivo(rs.getBoolean("activo"));

                lista.add(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar pagos", e);
        }

        return lista;
    }

    @Override
    public Pago load(Integer id) {
        String sql =
                "SELECT idPago, fechaPago, montoTotal, tipo, idMetodoPago, activo " +
                        "FROM Pago " +
                        "WHERE idPago = ?";

        try (
                Connection con = DBManager.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Pago p = new Pago();

                    p.setIdPago(rs.getInt("idPago"));
                    p.setFechaPago(rs.getDate("fechaPago"));
                    p.setMonto(rs.getDouble("montoTotal"));
                    p.setTipo(rs.getString("tipo"));
                    p.setMetodoPago(rs.getInt("idMetodoPago"));
                    p.setActivo(rs.getBoolean("activo"));

                    return p;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener pago por ID", e);
        }

        return null;
    }

    @Override
    public Pago save(Pago p) {
        /*
         * BUG CORREGIDO:
         * Antes el SQL tenía activo fijo en 1:
         * VALUES (?,?,?,?,1)
         *
         * Eso hacía que TODO pago se registre como pagado.
         *
         * Ahora activo se recibe desde C#:
         * true  -> pagado
         * false -> pendiente
         */
        String sql =
                "INSERT INTO Pago(fechaPago, montoTotal, tipo, idMetodoPago, activo) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection con = DBManager.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            java.util.Date fecha = p.getFechaPago();

            if (fecha != null) {
                ps.setDate(1, new java.sql.Date(fecha.getTime()));
            } else {
                ps.setDate(1, new java.sql.Date(System.currentTimeMillis()));
            }

            ps.setDouble(2, p.getMonto());
            ps.setString(3, p.getTipo());
            ps.setInt(4, p.getMetodoPago());
            ps.setBoolean(5, p.isActivo());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        p.setIdPago(rs.getInt(1));
                    }
                }
            }

            return p;

        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar pago", e);
        }
    }

    @Override
    public Pago update(Pago p) {
        /*
         * BUG CORREGIDO:
         * Antes el SQL no incluía activo = ?,
         * pero abajo se hacía setBoolean(6, ...).
         *
         * Ahora sí actualiza también el estado del pago.
         */
        String sql =
                "UPDATE Pago SET " +
                        "fechaPago = ?, " +
                        "montoTotal = ?, " +
                        "tipo = ?, " +
                        "idMetodoPago = ?, " +
                        "activo = ? " +
                        "WHERE idPago = ?";

        try (
                Connection con = DBManager.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            java.util.Date fecha = p.getFechaPago();

            if (fecha != null) {
                ps.setDate(1, new java.sql.Date(fecha.getTime()));
            } else {
                ps.setDate(1, new java.sql.Date(System.currentTimeMillis()));
            }

            ps.setDouble(2, p.getMonto());
            ps.setString(3, p.getTipo());
            ps.setInt(4, p.getMetodoPago());
            ps.setBoolean(5, p.isActivo());
            ps.setInt(6, p.getIdPago());

            ps.executeUpdate();

            return p;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar pago", e);
        }
    }

    @Override
    public void remove(Pago p) {
        /*
         * OJO:
         * En este sistema activo se está usando como estado del pago:
         * activo = 1 -> pagado
         * activo = 0 -> pendiente
         *
         * Por eso NO conviene usar activo = 0 para eliminar,
         * porque se confundiría con "pendiente".
         *
         * Para eliminar de verdad, hacemos DELETE físico.
         */
        String sql = "DELETE FROM Pago WHERE idPago = ?";

        try (
                Connection con = DBManager.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, p.getIdPago());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar pago", e);
        }
    }
}