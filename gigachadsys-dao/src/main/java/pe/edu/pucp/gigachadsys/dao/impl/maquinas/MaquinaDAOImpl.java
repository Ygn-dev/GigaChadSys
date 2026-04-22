package pe.edu.pucp.gigachadsys.dao.impl.clases;

import pe.edu.pucp.gigachadsys.dao.MaquinaDAO;
import pe.edu.pucp.gigachadsys.dao.manager.DBManager;
import pe.edu.pucp.gigachadsys.model.Maquina;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaquinaDAOImpl implements MaquinaDAO {

    @Override
    public List<Maquina> listAll() {
        List<Maquina> lista = new ArrayList<>();
        String sql = "SELECT * FROM Maquina WHERE activo = 1";

        try(Connection con = DBManager.getInstance().getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql)) {

            while(rs.next()){
                Maquina m = new Maquina(
                        rs.getInt("idMaquina"),
                        rs.getString("nombre"),
                        rs.getString("marca"),
                        rs.getString("estado"),
                        rs.getDate("fechaUltimoMantenimiento")
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
    public Maquina load(Integer id) {
        String sql = "SELECT * FROM Maquina WHERE idMaquina=?";

        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                Maquina m = new Maquina(
                        rs.getInt("idMaquina"),
                        rs.getString("nombre"),
                        rs.getString("marca"),
                        rs.getString("estado"),
                        rs.getDate("fechaUltimoMantenimiento")
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
    public Maquina save(Maquina m) {
        String sql = "INSERT INTO Maquina(nombre, marca, estado, fechaUltimoMantenimiento, activo) VALUES (?,?,?,?,1)";

        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, m.getNombre());
            ps.setString(2, m.getMarca());
            ps.setString(3, m.getEstado());
            ps.setDate(4, new java.sql.Date(m.getFechaUltimoMantenimiento().getTime()));

            ps.executeUpdate();

        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        return m;
    }

    @Override
    public Maquina update(Maquina m) {
        String sql = "UPDATE Maquina SET nombre=?, marca=?, estado=?, fechaUltimoMantenimiento=? WHERE idMaquina=?";

        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, m.getNombre());
            ps.setString(2, m.getMarca());
            ps.setString(3, m.getEstado());
            ps.setDate(4, new java.sql.Date(m.getFechaUltimoMantenimiento().getTime()));
            ps.setInt(5, m.getIdMaquina());

            ps.executeUpdate();

        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        return m;
    }

    @Override
    public void remove(Maquina m) {
        String sql = "UPDATE Maquina SET activo=0 WHERE idMaquina=?";

        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, m.getIdMaquina());
            ps.executeUpdate();

        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}