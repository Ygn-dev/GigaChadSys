package pe.edu.pucp.gigachadsys.dao.impl.clases;

import pe.edu.pucp.gigachadsys.dao.manager.DBManager;
import pe.edu.pucp.gigachadsys.dao.inter.clases.SesionClaseDAO;
import pe.edu.pucp.gigachadsys.model.clases.SesionClase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import pe.edu.pucp.gigachadsys.dao.inter.clases.SalonDAO;
import pe.edu.pucp.gigachadsys.dao.impl.clases.SalonDAOImpl;
import pe.edu.pucp.gigachadsys.dao.inter.usuarios.EntrenadorDAO;
import pe.edu.pucp.gigachadsys.dao.impl.usuarios.EntrenadorDAOImpl;
import pe.edu.pucp.gigachadsys.dao.inter.clases.ClaseGrupalDAO;
import pe.edu.pucp.gigachadsys.dao.impl.clases.ClaseGrupalDAOImpl;

public class SesionClaseDAOImpl implements SesionClaseDAO {

    @Override
    public List<SesionClase> listAll() {
        List<SesionClase> lista = new ArrayList<>();
        String sql = "SELECT * FROM SesionClase WHERE activo=1";
        SalonDAO salonDAO = new SalonDAOImpl();
        EntrenadorDAO entrenadorDAO = new EntrenadorDAOImpl();
        ClaseGrupalDAO claseDAO = new ClaseGrupalDAOImpl();
        
        java.util.Map<Integer, pe.edu.pucp.gigachadsys.model.clases.Salon> salonMap = new java.util.HashMap<>();
        for(pe.edu.pucp.gigachadsys.model.clases.Salon s : salonDAO.listAll()) salonMap.put(s.getIdSalon(), s);
        
        java.util.Map<Integer, pe.edu.pucp.gigachadsys.model.personas.Entrenador> entrenadorMap = new java.util.HashMap<>();
        for(pe.edu.pucp.gigachadsys.model.personas.Entrenador e : entrenadorDAO.listAll()) entrenadorMap.put(e.getIdUsuario(), e);
        
        java.util.Map<Integer, pe.edu.pucp.gigachadsys.model.clases.ClaseGrupal> claseMap = new java.util.HashMap<>();
        for(pe.edu.pucp.gigachadsys.model.clases.ClaseGrupal c : claseDAO.listAll()) claseMap.put(c.getIdClase(), c);

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
                s.setSalon(salonMap.get(rs.getInt("idSalon")));
                s.setEntrenador(entrenadorMap.get(rs.getInt("idEntrenador")));
                s.setClaseGrupal(claseMap.get(rs.getInt("idClase")));
                lista.add(s);
            }

        } catch(SQLException e){ throw new RuntimeException(e); }

        return lista;
    }

    @Override
    public SesionClase load(Integer id) {
        String sql = "SELECT * FROM SesionClase WHERE idSesion=?";
        SalonDAO salonDAO = new SalonDAOImpl();
        EntrenadorDAO entrenadorDAO = new EntrenadorDAOImpl();
        ClaseGrupalDAO claseDAO = new ClaseGrupalDAOImpl();

        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
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
                s.setSalon(salonDAO.load(rs.getInt("idSalon")));
                s.setEntrenador(entrenadorDAO.load(rs.getInt("idEntrenador")));
                s.setClaseGrupal(claseDAO.load(rs.getInt("idClase")));
                return s;
            }

        } catch(SQLException e){ throw new RuntimeException(e); }

        return null;
    }

    @Override
    public SesionClase save(SesionClase s) {
        String sql = "INSERT INTO SesionClase(fechaSesion, horaInicio, horaFin, cuposDisponibles, idSalon, idEntrenador, idClase, activo) VALUES (?,?,?,?,?,?,?,1)";

        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, new java.sql.Date(s.getFechaSesion().getTime()));
            ps.setTimestamp(2, s.getHoraInicio());
            ps.setTimestamp(3, s.getHoraFin());
            ps.setInt(4, s.getCuposDisponibles());
            
            ps.setInt(5, s.getSalon() != null ? s.getSalon().getIdSalon() : 0);
            ps.setInt(6, s.getEntrenador() != null ? s.getEntrenador().getIdUsuario() : 0);
            ps.setInt(7, s.getClaseGrupal() != null ? s.getClaseGrupal().getIdClase() : 0);

            int affectedRows = ps.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int newId = generatedKeys.getInt(1);
                        s.setIdSesion(newId);
                    }
                }
            }

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
            
            ps.setInt(5, s.getSalon() != null ? s.getSalon().getIdSalon() : 0);
            ps.setInt(6, s.getEntrenador() != null ? s.getEntrenador().getIdUsuario() : 0);
            ps.setInt(7, s.getClaseGrupal() != null ? s.getClaseGrupal().getIdClase() : 0);
            
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