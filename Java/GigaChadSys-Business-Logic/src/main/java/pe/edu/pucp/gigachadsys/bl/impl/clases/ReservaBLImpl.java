package pe.edu.pucp.gigachadsys.bl.impl.clases;

import pe.edu.pucp.gigachadsys.bl.inter.clases.ReservaBL;
import pe.edu.pucp.gigachadsys.dao.impl.clases.ReservaDAOImpl;
import pe.edu.pucp.gigachadsys.dao.inter.clases.ReservaDAO;
import pe.edu.pucp.gigachadsys.dao.manager.DBManager;
import pe.edu.pucp.gigachadsys.model.clases.Reserva;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ReservaBLImpl implements ReservaBL {

    ReservaDAO reservaDAO = new ReservaDAOImpl();
    @Override
    public List<Reserva> listAll() {

        return reservaDAO.listAll();
    }
}