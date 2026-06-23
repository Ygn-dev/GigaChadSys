package pe.edu.pucp.gigachadsys.dao.impl.pagos;

import pe.edu.pucp.gigachadsys.dao.manager.DBManager;
import pe.edu.pucp.gigachadsys.dao.inter.pagos.MetodoPagoDAO;
import pe.edu.pucp.gigachadsys.model.membresias.MembresiaBlack;
import pe.edu.pucp.gigachadsys.model.pagos.MetodoPago;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MetodoPagoDAOImpl implements MetodoPagoDAO {

    @Override
    public List<MetodoPago> listAll() {
        List<MetodoPago> lista = new ArrayList<>();
        String sql = "SELECT idMetodoPago, tipo, detalle, activo " +
                "FROM MetodoPago " +
                "WHERE activo = 1";

        try(Connection con = DBManager.getInstance().getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql)) {

            while(rs.next()){
                MetodoPago m = new MetodoPago(
                        rs.getInt("idMetodoPago"),
                        rs.getString("tipo"),
                        rs.getString("detalle")
                );
                m.setActivo(rs.getBoolean("activo"));
                lista.add(m);
            }

        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        return lista;
    }

    @Override
    public MetodoPago load(Integer id) {
        String sql= "select idMetodoPago, tipo, detalle, activo "
                + "FROM MetodoPago where = idMetodoPago = ?";

        Connection connection = DBManager.getInstance().getConnection();

        try(PreparedStatement pstm = connection.prepareStatement(sql)){
            pstm.setInt(1,id);
            try(ResultSet rs =pstm.executeQuery()){
                if(rs.next()){
                    MetodoPago metodo = new MetodoPago();
                    metodo.setIdMetodoPago(rs.getInt(1));
                    metodo.setTipo(rs.getString(2));
                    metodo.setDetalle(rs.getString(3));
                    metodo.setActivo(rs.getBoolean(4));
                    return metodo;
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public MetodoPago save(MetodoPago m) {
        String sql = "INSERT INTO MetodoPago(tipo, detalle, activo) VALUES (?,?,?)";

        try(Connection connection = DBManager.getInstance().getConnection()){
            PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);


            pstm.setString(1, m.getTipo());
            pstm.setString(2, m.getDetalle());
            pstm.setBoolean(3, m.isActivo());
            pstm.executeUpdate();
            int nuevoId;
            try(ResultSet rs = pstm.getGeneratedKeys()){
                if(rs.next()){
                    nuevoId= pstm.getGeneratedKeys().getInt(1);
                    m.setIdMetodoPago(nuevoId);
                }
            }
            return m;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
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

            ps.setBoolean(1, m.isActivo());
            ps.setInt(1, m.getIdMetodoPago());

            ps.executeUpdate();

        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}