package pe.edu.pucp.gigachadsys.test;

import pe.edu.pucp.gigachadsys.dao.impl.clases.SalonDAOImpl; // Usa Alt+Enter si la ruta es diferente
import pe.edu.pucp.gigachadsys.model.Salon;

public class TestSalon {
    public static void main(String[] args) {
        System.out.println("=== BATERÍA DE PRUEBAS CRUD (TABLA SALON) ===");
        SalonDAOImpl dao = new SalonDAOImpl();

        // Creamos el objeto vacío y usamos setters (más seguro)
        Salon salonPrueba = new Salon();
        salonPrueba.setNombreSalon("Zona Crossfit Alpha");
        salonPrueba.setAforoMaximo(25);
        salonPrueba.setActivo(true);

        // 1. TEST SAVE
        System.out.println("\n[TEST 1] Insertando Salón en AWS...");
        dao.save(salonPrueba);
        int idGenerado = salonPrueba.getIdSalon();

        if(idGenerado > 0) {
            // 2. TEST LOAD
            System.out.println("\n[TEST 2] Leyendo registro de AWS...");
            Salon leido = dao.load(idGenerado);
            if(leido != null) {
                System.out.println("Encontrado: " + leido.getNombreSalon() + " - Aforo: " + leido.getAforoMaximo());

                // 3. TEST UPDATE
                System.out.println("\n[TEST 3] Actualizando aforo del salón...");
                leido.setAforoMaximo(40);
                dao.update(leido);

                // 4. TEST REMOVE
                System.out.println("\n[TEST 4] Eliminando registro de prueba (Limpiando BD)...");
                dao.remove(salonPrueba);

                Salon comprobacion = dao.load(idGenerado);
                if(comprobacion == null) {
                    System.out.println("✅ Prueba Finalizada: Base de datos limpia.");
                } else {
                    System.err.println("❌ Error: El salón no se eliminó correctamente.");
                }
            } else {
                System.err.println("❌ Error: No se pudo leer el salón recién creado.");
            }
        } else {
            System.err.println("❌ Error: No se generó un ID válido al guardar.");
        }
    }
}