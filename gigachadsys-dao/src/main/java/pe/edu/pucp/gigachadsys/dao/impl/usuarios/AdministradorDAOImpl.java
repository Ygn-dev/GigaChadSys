package pe.edu.pucp.gigachadsys.dao.impl.usuarios;

import java.sql.Connection;
import pe.edu.pucp.gigachadsys.model.Administrador;


public class AdministradorDAOImpl implements AdministradorDAO{

    @Override
    public Administrador load(Integer id) {
        //SQL QUERY
        String sql = "SELECT idUsuario, nombres, apellidoPaterno, apellidoMaterno,edad,DNI,email,telefono,contrasenia,rol,sede,sueldo,cargo from Administrador where id = ?";


        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try(ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Area area = new Area();
                    area.setId(rs.getInt(1));
                    area.setName(rs.getString(2));
                    area.setActive(rs.getBoolean(3));
                    return area;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
