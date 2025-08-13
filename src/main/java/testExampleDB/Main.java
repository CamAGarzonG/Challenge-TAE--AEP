package testExampleDB;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Ejemplo: Buscar usuarios por nombre
        String query = "SELECT * FROM usuarios WHERE Name = ?";
        List<Map<String, Object>> users = DatabaseUtils.executeQuery(query, "Juan Pérez");

        for (Map<String, Object> user : users) {
            System.out.println(user);
        }

    }
}