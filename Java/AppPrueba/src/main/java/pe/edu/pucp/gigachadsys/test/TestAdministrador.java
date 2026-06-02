package pe.edu.pucp.gigachadsys.test;

import pe.edu.pucp.gigachadsys.dao.impl.usuarios.AdministradorDAOImpl;
import pe.edu.pucp.gigachadsys.dao.inter.usuarios.AdministradorDAO;
import pe.edu.pucp.gigachadsys.model.personas.Administrador;

import java.util.List;

public class TestAdministrador {
    public static void main(String[] args) {
        AdministradorDAO administradorDAO = new AdministradorDAOImpl();

        // Crear nuevo administrador
        Administrador administrador = new Administrador();

        administrador.setNombres("Lucia");
        administrador.setApellidoPaterno("Ramirez");
        administrador.setApellidoMaterno("Torres");
        administrador.setEdad(28);
        administrador.setDni(45871236);
        administrador.setEmail("lucia.ramirez@gmail.com");
        administrador.setTelefono(912345678);
        administrador.setContrasenia("admin2026");
        administrador.setRol("Supervisor");
        administrador.setSede("Arequipa");
        administrador.setSueldo(4200.50);
        administrador.setCargo("Coordinadora");

        // Guardar
        administradorDAO.save(administrador);

        System.out.println("=== ADMINISTRADORES REGISTRADOS ===");

        List<Administrador> administradores = administradorDAO.listAll();

        for (Administrador admin : administradores) {

            System.out.println(
                    admin.getIdUsuario() + " | " +
                            admin.getNombres() + " " +
                            admin.getApellidoPaterno() + " | " +
                            admin.getEmail()
            );
        }

        // Probar load()
        System.out.println("\n=== BUSCAR ADMINISTRADOR ===");

        Administrador encontrado =
                administradorDAO.load(administrador.getIdUsuario());

        if (encontrado != null) {

            System.out.println("Administrador encontrado:");
            System.out.println(encontrado.getNombres());
        }

        // Probar update()
        System.out.println("\n=== ACTUALIZAR ADMINISTRADOR ===");

        administrador.setSueldo(5000.00);
        administrador.setCargo("Director");

        administradorDAO.update(administrador);

        Administrador actualizado =
                administradorDAO.load(administrador.getIdUsuario());

        System.out.println(
                "Nuevo sueldo: " + actualizado.getSueldo()
        );

        // Probar remove()
        System.out.println("\n=== ELIMINAR ADMINISTRADOR ===");

        administradorDAO.remove(administrador);

        System.out.println("Lista después de eliminar:");

        administradores = administradorDAO.listAll();

        for (Administrador admin : administradores) {

            System.out.println(
                    admin.getIdUsuario() + " | " +
                            admin.getNombres()
            );
        }
    }
}