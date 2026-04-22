package pe.edu.pucp.gigachadsys.test;

import pe.edu.pucp.gigachadsys.dao.impl.clases.ClaseGrupalDAOImpl;
import pe.edu.pucp.gigachadsys.model.ClaseGrupal;

public class TestClaseGrupal {
    public static void main(String[] args) {
        System.out.println("=== BATERÍA DE PRUEBAS CRUD (TABLA CLASE GRUPAL) ===");
        ClaseGrupalDAOImpl dao = new ClaseGrupalDAOImpl();

        ClaseGrupal clasePrueba = new ClaseGrupal();
        clasePrueba.setNombreDisciplina("Boxeo de Sombra");
        clasePrueba.setDescripcion("Clase de prueba para cardio intenso");
        clasePrueba.setDuracionMinutos(45);
        clasePrueba.setNivel("Intermedio");
        clasePrueba.setActivo(true); // Descomenta si lo tienes en tu modelo

        // 1. TEST SAVE
        System.out.println("\n[TEST 1] Insertando Clase en AWS...");
        dao.save(clasePrueba);
        int idGenerado = clasePrueba.getIdClase();

        if(idGenerado >= 0) {
            // 2. TEST LOAD
            System.out.println("\n[TEST 2] Leyendo registro de AWS...");
            ClaseGrupal leida = dao.load(idGenerado);
            if(leida != null) {
                System.out.println("Encontrado: " + leida.getNombreDisciplina() + " (" + leida.getDuracionMinutos() + " min)");

                // 3. TEST UPDATE
                System.out.println("\n[TEST 3] Actualizando duración...");
                leida.setDuracionMinutos(60);
                dao.update(leida);

                // 4. TEST REMOVE
                System.out.println("\n[TEST 4] Eliminando registro de prueba (Limpiando BD)...");
                dao.remove(clasePrueba);

                ClaseGrupal comprobacion = dao.load(idGenerado);
                if(comprobacion != null) {
                    System.out.println("✅ Prueba Finalizada: Base de datos limpia.");
                } else {
                    System.err.println("❌ Error: La clase no se eliminó correctamente.");
                }
            } else {
                System.err.println("❌ Error: No se pudo leer la clase recién creada.");
            }
        } else {
            System.err.println("❌ Error: No se generó un ID válido al guardar.");
        }
    }
}