package pe.edu.pucp.gigachadsys.test;

import pe.edu.pucp.gigachadsys.dao.manager.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TestConexionBD {

    public static void main(String[] args) {

        System.out.println("=== PRUEBA DE CONEXIÓN A MYSQL ===");

        try (
                Connection conexion =
                        DBManager.getInstance().getConnection()
        ) {

            if (conexion == null) {
                System.out.println("No se pudo obtener la conexión.");
                System.out.println("Revisa db.properties, Internet y acceso a RDS.");
                return;
            }

            if (conexion.isClosed()) {
                System.out.println("La conexión está cerrada.");
                return;
            }

            System.out.println("Conexión exitosa a MySQL.");
            System.out.println("Base de datos: " + conexion.getCatalog());

            // Ejecutamos una consulta mínima para comprobar
            // que la base de datos realmente responde.
            String sql = "SELECT 1";

            try (
                    PreparedStatement comando =
                            conexion.prepareStatement(sql);

                    ResultSet resultado =
                            comando.executeQuery()
            ) {

                if (resultado.next()) {
                    System.out.println(
                            "MySQL respondió correctamente: "
                                    + resultado.getInt(1)
                    );
                }
            }

        } catch (Exception ex) {
            System.out.println("Error durante la prueba:");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        System.out.println("=== FIN DE LA PRUEBA ===");
    }
}