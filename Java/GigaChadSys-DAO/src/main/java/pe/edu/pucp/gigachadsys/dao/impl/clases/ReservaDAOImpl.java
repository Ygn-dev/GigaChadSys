package pe.edu.pucp.gigachadsys.dao.impl.clases;

import pe.edu.pucp.gigachadsys.dao.manager.DBManager;
import pe.edu.pucp.gigachadsys.dao.inter.clases.ReservaDAO;
import pe.edu.pucp.gigachadsys.model.clases.Reserva;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import pe.edu.pucp.gigachadsys.dao.inter.clases.SesionClaseDAO;
import pe.edu.pucp.gigachadsys.dao.impl.clases.SesionClaseDAOImpl;

public class ReservaDAOImpl implements ReservaDAO {

    @Override
    public List<Reserva> listAll() {
        List<Reserva> lista = new ArrayList<>();
        String sql = "SELECT * FROM Reserva WHERE activo=1";
        SesionClaseDAO sesionDAO = new SesionClaseDAOImpl();
        java.util.Map<Integer, pe.edu.pucp.gigachadsys.model.clases.SesionClase> sesionMap = new java.util.HashMap<>();
        for(pe.edu.pucp.gigachadsys.model.clases.SesionClase s : sesionDAO.listAll()) sesionMap.put(s.getIdSesion(), s);

        try(Connection con = DBManager.getInstance().getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql)) {

            while(rs.next()){
                Reserva r = new Reserva(
                        rs.getInt("idReserva"),
                        rs.getTimestamp("fechaHoraReserva"),
                        rs.getBoolean("asistio"),
                        rs.getInt("idSesion"),
                        rs.getInt("idUsuario")
                );
                r.setSesionClase(sesionMap.get(rs.getInt("idSesion")));
                lista.add(r);
            }

        } catch(SQLException e){ throw new RuntimeException(e); }

        return lista;
    }

    @Override
    public Reserva load(Integer id) {
        String sql = "SELECT * FROM Reserva WHERE idReserva=?";
        SesionClaseDAO sesionDAO = new SesionClaseDAOImpl();

        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                Reserva r = new Reserva(
                        rs.getInt("idReserva"),
                        rs.getTimestamp("fechaHoraReserva"),
                        rs.getBoolean("asistio"),
                        rs.getInt("idSesion"),
                        rs.getInt("idUsuario")
                );
                r.setSesionClase(sesionDAO.load(rs.getInt("idSesion")));
                return r;
            }

        } catch(SQLException e){ throw new RuntimeException(e); }

        return null;
    }

    @Override
    public Reserva save(Reserva r) {
        String sql = "INSERT INTO Reserva(fechaHoraReserva, asistio, idSesion, idUsuario, activo) VALUES (?,?,?,?,1)";

        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setTimestamp(1, r.getFechaHoraReserva());
            ps.setBoolean(2, r.isAsistio());
            ps.setInt(3, r.getSesionClase() != null ? r.getSesionClase().getIdSesion() : 0);
            ps.setInt(4, r.getIdUsuario());

            ps.executeUpdate();

        } catch(SQLException e){ throw new RuntimeException(e); }

        return r;
    }

    @Override
    public Reserva update(Reserva r) {
        String sql = "UPDATE Reserva SET fechaHoraReserva=?, asistio=?, idSesion=?, idUsuario=? WHERE idReserva=?";

        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setTimestamp(1, r.getFechaHoraReserva());
            ps.setBoolean(2, r.isAsistio());
            ps.setInt(3, r.getSesionClase() != null ? r.getSesionClase().getIdSesion() : 0);
            ps.setInt(4, r.getIdUsuario());
            ps.setInt(5, r.getIdReserva());

            ps.executeUpdate();

        } catch(SQLException e){ throw new RuntimeException(e); }

        return r;
    }

    @Override
    public void remove(Reserva r) {
        String sql = "UPDATE Reserva SET activo=0 WHERE idReserva=?";

        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, r.getIdReserva());
            ps.executeUpdate();

        } catch(SQLException e){ throw new RuntimeException(e); }
    }
}