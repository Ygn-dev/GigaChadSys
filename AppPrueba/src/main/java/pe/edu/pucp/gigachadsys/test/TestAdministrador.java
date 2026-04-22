package pe.edu.pucp.gigachadsys.test;

import pe.edu.pucp.gigachadsys.dao.impl.usuarios.AdministradorDAOImpl;
import pe.edu.pucp.gigachadsys.model.Usuario;
import pe.edu.pucp.gigachadsys.model.Administrador;

public class TestAdministrador {
    public static void main(String[] args) {
        System.out.println("=== BATERÍA DE PRUEBAS CRUD PARA USUARIOS (TABLA ADMINISTRADOR) ===");

        // 2. Instanciamos el DAO correcto
        AdministradorDAOImpl dao = new AdministradorDAOImpl();

        // 3. Creamos un usuario con perfil de Administrador
        Usuario adminPrueba = new Usuario(
                0, "Chris", "Bumstead", "Cbum", 29, 87654321, "cbum@gigachad.com", 999111222, "olympia2024", "ADMINISTRADOR"
        ) {
            @Override
            public void mostrarDatos() { System.out.println("Admin: " + getNombres()); }
        };

        // 1. TEST SAVE
        System.out.println("\n[TEST 1] Insertando Administrador en AWS...");
        dao.save(adminPrueba);
        int idGenerado = adminPrueba.getIdUsuario();

        if(idGenerado > 0) {
            // 2. TEST LOAD
            System.out.println("\n[TEST 2] Leyendo registro de AWS...");
            Usuario leido = dao.load(idGenerado);
            if(leido != null) {
                System.out.println("Encontrado: " + leido.getNombres() + " - " + leido.getEmail());

                // 3. TEST UPDATE
                System.out.println("\n[TEST 3] Actualizando email del administrador...");
                leido.setEmail("admin.cbum@gigachadsys.com");
                dao.update(leido);

                // 4. TEST REMOVE
                System.out.println("\n[TEST 4] Eliminando registro de prueba (Limpiando BD)...");
                dao.remove(idGenerado);

                Usuario comprobacion = dao.load(idGenerado);
                if(comprobacion == null) {
                    System.out.println("✅ Prueba Finalizada: Base de datos limpia.");
                } else {
                    System.err.println("❌ Error: El administrador no se eliminó correctamente.");
                }
            } else {
                System.err.println("❌ Error: No se pudo leer el administrador recién creado.");
            }
        } else {
            System.err.println("❌ Error: No se generó un ID válido al guardar.");
        }
    }
}