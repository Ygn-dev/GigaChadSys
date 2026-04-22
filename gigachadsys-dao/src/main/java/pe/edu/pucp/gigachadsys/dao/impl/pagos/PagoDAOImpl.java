package pe.edu.pucp.gigachadsys.dao.impl.pagos;

import pe.edu.pucp.gigachadsys.inter.pagos.PagoDAO;
import pe.edu.pucp.gigachadsys.dao.manager.DBManager;
import pe.edu.pucp.gigachadsys.model.Pago;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PagoDAOImpl implements PagoDAO {

    @Override
    public List<Pago> listAll() {
        List<Pago> lista = new ArrayList<>();
        String sql = "SELECT * FROM Pago WHERE activo = 1";

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
        String sql = "SELECT * FROM Pago WHERE idPago=?";

        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                Pago p = new Pago(
                        rs.getInt("idPago"),
                        rs.getDate("fechaPago"),
                        rs.getDouble("montoTotal"),
                        rs.getString("tipo"),
                        rs.getInt("idMetodoPago")
                );
                p.setActive(rs.getBoolean("activo"));
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
            ps.setDouble(2, p.getMontoTotal());
            ps.setString(3, p.getTipo());
            ps.setInt(4, p.getIdMetodoPago());

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
            ps.setString(3, p.getTipo());
            ps.setInt(5, p.getIdPago());

            ps.executeUpdate();

        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        return p;
    }

    @Override
    public void remove(Pago p) {
        String sql = "UPDATE Pago SET activo=0 WHERE idPago=?";

        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, p.getIdPago());
            ps.executeUpdate();

        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}