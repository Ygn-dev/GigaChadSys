package pe.edu.pucp.gigachadsys.bl.impl.clases;


import pe.edu.pucp.gigachadsys.bl.inter.clases.ClaseGrupalBL;
import pe.edu.pucp.gigachadsys.dao.impl.clases.ClaseGrupalDAOImpl;
import pe.edu.pucp.gigachadsys.dao.inter.clases.ClaseGrupalDAO;
import pe.edu.pucp.gigachadsys.model.clases.ClaseGrupal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClaseGrupalBLImpl implements ClaseGrupalBL {

    ClaseGrupalDAO claseDAO = new ClaseGrupalDAOImpl();
    @Override
    public List<ClaseGrupal> listAll() {
        return claseDAO.listAll();
    }
}