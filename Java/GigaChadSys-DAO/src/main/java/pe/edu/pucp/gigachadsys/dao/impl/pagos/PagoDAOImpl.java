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
        String sql = "select idPago, fechaPago, montoTotal, tipo, idMetodoPago, activo FROM Pago WHERE activo = 1";

        try(Connection con = DBManager.getInstance().getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql)) {

            while(rs.next()){
                Pago p = new Pago(
                        rs.getInt("idPago"),
                        rs.getDate("fechaPago"),
                        rs.getDouble("montoTotal"),
                        rs.getString("tipo"),
                        rs.getInt("idMetodoPago")
                );
                p.setActive(rs.getBoolean("activo"));
                lista.add(p);
            }

        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        return lista;
    }

    @Override
    public Pago load(Integer id) {
        String sql= "select idPago, fechaPago, montoTotal, tipo, idMetodoPago, activo "
                + "FROM Pago where = idPago = ?";

        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                Pago p = new Pago(); //agregarle constructor
                p.setIdPago(rs.getInt(1));
                p.setFechaPago(rs.getDate(2));
                p.setMonto(rs.getDouble(3));
                p.setTipo(rs.getString(4));
                p.setMetodoPago(rs.getInt(5));
                p.setActivo(rs.getBoolean(rs.getInt(6)));
                return p;
            }

        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Pago save(Pago p) {
        String sql = "INSERT INTO Pago(fechaPago, montoTotal, tipo, idMetodoPago, activo) VALUES (?,?,?,?,1)";

        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, new java.sql.Date(p.getFechaPago().getTime()));
            ps.setDouble(2, p.getMonto());
            ps.setString(3, p.getTipo());
            ps.setInt(4, p.getIdPago());

            ps.executeUpdate();

        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        return p;
    }

    @Override
    public Pago update(Pago p) {
        String sql = "UPDATE Pago SET fechaPago=?, montoTotal=?, tipo=?, idMetodoPago=? WHERE idPago=?";

        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, new java.sql.Date(p.getFechaPago().getTime()));
            ps.setDouble(2,p.getMonto());
            ps.setString(3, p.getTipo());
            ps.setInt(4, p.getMetodoPago());
            ps.setInt(5, p.getIdPago());
            ps.setBoolean(6,p.isActivo());
            ps.executeUpdate();

        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        return p;
    }

    @Override
    public void remove(Pago p) {
        String sql = "UPDATE Pago SET activo=? WHERE idPago=?";

        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setBoolean(1, p.isActivo());
            pstmt.setInt(2, p.getIdPago());
            pstmt.executeUpdate();

        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}