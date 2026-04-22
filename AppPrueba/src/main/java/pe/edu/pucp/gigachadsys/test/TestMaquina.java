package pe.edu.pucp.gigachadsys.test;

import pe.edu.pucp.gigachadsys.dao.impl.maquinas.MaquinaDAOImpl;
import pe.edu.pucp.gigachadsys.model.Maquina;
import java.util.Date;

public class TestMaquina {
    public static void main(String[] args) {
        System.out.println("=== BATERÍA DE PRUEBAS CRUD (TABLA MÁQUINA) ===");
        MaquinaDAOImpl dao = new MaquinaDAOImpl();

        Maquina maquinaPrueba = new Maquina();
        maquinaPrueba.setNombre("Prensa de Piernas 45°");
        maquinaPrueba.setMarca("Hammer Strength");
        maquinaPrueba.setEstado("Operativa");
        maquinaPrueba.setFechaUltimoMantenimiento(new Date()); // Asigna la fecha de hoy
        maquinaPrueba.setActive(true);

        // 1. TEST SAVE
        System.out.println("\n[TEST 1] Insertando Máquina en AWS...");
        dao.save(maquinaPrueba);
        int idGenerado = maquinaPrueba.getIdMaquina();

        if(idGenerado > 0) {
            // 2. TEST LOAD
            System.out.println("\n[TEST 2] Leyendo registro de AWS...");
            Maquina leida = dao.load(idGenerado);
            if(leida != null) {
                System.out.println("Encontrado: " + leida.getNombre() + " - " + leida.getMarca());

                // 3. TEST UPDATE
                System.out.println("\n[TEST 3] Actualizando estado a mantenimiento...");
                leida.setEstado("En Reparación");
                dao.update(leida);

                // 4. TEST REMOVE
                System.out.println("\n[TEST 4] Eliminando registro de prueba (Limpiando BD)...");
                dao.remove(maquinaPrueba);

                Maquina comprobacion = dao.load(idGenerado);
                if(comprobacion == null) {
                    System.out.println("✅ Prueba Finalizada: Base de datos limpia.");
                } else {
                    System.err.println("❌ Error: La máquina no se eliminó correctamente.");
                }
            } else {
                System.err.println("❌ Error: No se pudo leer la máquina recién creada.");
            }
        } else {
            System.err.println("❌ Error: No se generó un ID válido al guardar.");
        }
    }
}