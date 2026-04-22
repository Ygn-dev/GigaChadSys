package pe.edu.pucp.gigachadsys.dao.impl.pagos;

import pe.edu.pucp.gigachadsys.dao.manager.DBManager;
import pe.edu.pucp.gigachadsys.inter.pagos.MetodoPagoDAO;
import pe.edu.pucp.gigachadsys.model.MetodoPago;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MetodoPagoDAOImpl implements MetodoPagoDAO {

    @Override
    public List<MetodoPago> listAll() {
        List<MetodoPago> lista = new ArrayList<>();
        String sql = "SELECT * FROM MetodoPago WHERE activo = 1";

        try(Connection con = DBManager.getInstance().getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql)) {

            while(rs.next()){
                MetodoPago m = new MetodoPago(
                        rs.getInt("idMetodoPago"),
                        rs.getString("tipo"),
                        rs.getString("detalle")
                );
                m.setActive(rs.getBoolean("activo"));
                lista.add(m);
            }

        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        return lista;
    }

    @Override
    public MetodoPago load(Integer id) {
        String sql = "SELECT * FROM MetodoPago WHERE idMetodoPago=?";

        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                MetodoPago m = new MetodoPago(
                        rs.getInt("idMetodoPago"),
                        rs.getString("tipo"),
                        rs.getString("detalle")
                );
                m.setActive(rs.getBoolean("activo"));
                return m;
            }

        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public MetodoPago save(MetodoPago m) {
        String sql = "INSERT INTO MetodoPago(tipo, detalle, activo) VALUES (?,?,1)";

        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, m.getTipo());
            ps.setString(2, m.getDetalle());

            ps.executeUpdate();

        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        return m;
    }

    @Override
    public MetodoPago update(MetodoPago m) {
        String sql = "UPDATE MetodoPago SET tipo=?, detalle=? WHERE idMetodoPago=?";

        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, m.getTipo());
            ps.setString(2, m.getDetalle());
            ps.setInt(3, m.getIdMetodoPago());

            ps.executeUpdate();

        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        return m;
    }

    @Override
    public void remove(MetodoPago m) {
        String sql = "UPDATE MetodoPago SET activo=0 WHERE idMetodoPago=?";

        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, m.getIdMetodoPago());
            ps.executeUpdate();

        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}