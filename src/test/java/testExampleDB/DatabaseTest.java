package testExampleDB;

import org.junit.jupiter.api.*;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    @AfterEach
    void restoreDbToOriginalAmount() {
        String countQuery = "SELECT COUNT(*) as total FROM usuarios";
        List<Map<String, Object>> result = DatabaseUtils.executeQuery(countQuery);
        int total = ((Number) result.get(0).get("total")).intValue();

        if (total > 3) {
            String maxIdQuery = "SELECT MAX(id) as maxId FROM usuarios";
            List<Map<String, Object>> maxIdResult = DatabaseUtils.executeQuery(maxIdQuery);
            int maxId = ((Number) maxIdResult.get(0).get("maxId")).intValue();

            String deleteQuery = "DELETE FROM usuarios WHERE id = ?";
            DatabaseUtils.executeUpdate(deleteQuery, maxId);
        }
    }

    @Test
    void testAmountOfNames() {
        String query = "SELECT COUNT(*) as total FROM usuarios";
        List<Map<String, Object>> result = DatabaseUtils.executeQuery(query);
        assertFalse(result.isEmpty(), "Should not be empty");
        int total = ((Number) result.get(0).get("total")).intValue();
        assertEquals(3, total, "Should be 3 names");
    }

    @Test
    void addNameAndValidate() {
        String insert = "INSERT INTO usuarios (Name) VALUES (?)";
        DatabaseUtils.executeUpdate(insert, "Ana Torres");
        // Validation
        String query = "SELECT * FROM usuarios WHERE Name = ?";
        List<Map<String, Object>> result = DatabaseUtils.executeQuery(query, "Ana Torres");
        assertFalse(result.isEmpty(), "DShould exist at least one name: 'Ana Torres'");
        assertEquals("Ana Torres", result.get(0).get("Name"));
    }
}