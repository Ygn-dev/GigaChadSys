package pe.edu.pucp.gigachadsys.dao.impl.clases;

import pe.edu.pucp.gigachadsys.dao.SesionClaseDAO;
import pe.edu.pucp.gigachadsys.dao.manager.DBManager;
import pe.edu.pucp.gigachadsys.model.SesionClase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SesionClaseDAOImpl implements SesionClaseDAO {

    @Override
    public List<SesionClase> listAll() {
        List<SesionClase> lista = new ArrayList<>();
        String sql = "SELECT * FROM SesionClase WHERE activo=1";

        try(Connection con = DBManager.getInstance().getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql)) {

            while(rs.next()){
                SesionClase s = new SesionClase(
                        rs.getInt("idSesion"),
                        rs.getDate("fechaSesion"),
                        rs.getTimestamp("horaInicio"),
                        rs.getTimestamp("horaFin"),
                        rs.getInt("cuposDisponibles"),
                        rs.getInt("idSalon"),
                        rs.getInt("idEntrenador"),
                        rs.getInt("idClase")
                );
                lista.add(s);
            }

        } catch(SQLException e){ throw new RuntimeException(e); }

        return lista;
    }

    @Override
    public SesionClase load(Integer id) {
        String sql = "SELECT * FROM SesionClase WHERE idSesion=?";

        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return new SesionClase(
                        rs.getInt("idSesion"),
                        rs.getDate("fechaSesion"),
                        rs.getTimestamp("horaInicio"),
                        rs.getTimestamp("horaFin"),
                        rs.getInt("cuposDisponibles"),
                        rs.getInt("idSalon"),
                        rs.getInt("idEntrenador"),
                        rs.getInt("idClase")
                );
            }

        } catch(SQLException e){ throw new RuntimeException(e); }

        return null;
    }

    @Override
    public SesionClase save(SesionClase s) {
        String sql = "INSERT INTO SesionClase(fechaSesion, horaInicio, horaFin, cuposDisponibles, idSalon, idEntrenador, idClase, activo) VALUES (?,?,?,?,?,?,?,1)";

        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, new java.sql.Date(s.getFechaSesion().getTime()));
            ps.setTimestamp(2, s.getHoraInicio());
            ps.setTimestamp(3, s.getHoraFin());
            ps.setInt(4, s.getCuposDisponibles());
            ps.setInt(5, s.getIdSalon());
            ps.setInt(6, s.getIdEntrenador());
            ps.setInt(7, s.getIdClase());

            ps.executeUpdate();

        } catch(SQLException e){ throw new RuntimeException(e); }

        return s;
    }

    @Override
    public SesionClase update(SesionClase s) {
        String sql = "UPDATE SesionClase SET fechaSesion=?, horaInicio=?, horaFin=?, cuposDisponibles=?, idSalon=?, idEntrenador=?, idClase=? WHERE idSesion=?";

        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, new java.sql.Date(s.getFechaSesion().getTime()));
            ps.setTimestamp(2, s.getHoraInicio());
            ps.setTimestamp(3, s.getHoraFin());
            ps.setInt(4, s.getCuposDisponibles());
            ps.setInt(5, s.getIdSalon());
            ps.setInt(6, s.getIdEntrenador());
            ps.setInt(7, s.getIdClase());
            ps.setInt(8, s.getIdSesion());

            ps.executeUpdate();

        } catch(SQLException e){ throw new RuntimeException(e); }

        return s;
    }

    @Override
    public void remove(SesionClase s) {
        String sql = "UPDATE SesionClase SET activo=0 WHERE idSesion=?";

        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, s.getIdSesion());
            ps.executeUpdate();

        } catch(SQLException e){ throw new RuntimeException(e); }
    }
}