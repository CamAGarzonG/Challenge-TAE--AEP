package testExampleDB;

import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    @AfterEach
    void limpiarSiHayMasDeTres() {
        String countQuery = "SELECT COUNT(*) as total FROM usuarios";
        List<Map<String, Object>> result = DatabaseUtils.executeQuery(countQuery);
        int total = ((Number) result.get(0).get("total")).intValue();

        if (total > 3) {
            // Borrar el usuario con el id más alto (el último insertado)
            String maxIdQuery = "SELECT MAX(id) as maxId FROM usuarios";
            List<Map<String, Object>> maxIdResult = DatabaseUtils.executeQuery(maxIdQuery);
            int maxId = ((Number) maxIdResult.get(0).get("maxId")).intValue();

            String deleteQuery = "DELETE FROM usuarios WHERE id = ?";
            DatabaseUtils.executeUpdate(deleteQuery, maxId);
        }
    }

    @Test
    void testNumeroDeNombres() {
        String query = "SELECT COUNT(*) as total FROM usuarios";
        List<Map<String, Object>> result = DatabaseUtils.executeQuery(query);
        assertFalse(result.isEmpty(), "El resultado no debe estar vacío");
        int total = ((Number) result.get(0).get("total")).intValue();
        // Cambia el número esperado según los datos iniciales de tu tabla
        assertEquals(3, total, "Debe haber 3 nombres inicialmente");
    }

    @Test
    void testAgregarNombreYValidar() {
        // Agregar un nuevo nombre
        String insert = "INSERT INTO usuarios (Name) VALUES (?)";
        DatabaseUtils.executeUpdate(insert, "Ana Torres");

        // Validar que el nombre fue agregado
        String query = "SELECT * FROM usuarios WHERE Name = ?";
        List<Map<String, Object>> result = DatabaseUtils.executeQuery(query, "Ana Torres");
        assertFalse(result.isEmpty(), "Debe existir al menos un usuario con el nombre 'Ana Torres'");
        assertEquals("Ana Torres", result.get(0).get("Name"));
    }
}