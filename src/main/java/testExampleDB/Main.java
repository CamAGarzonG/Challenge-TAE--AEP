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

        // Ejemplo con más de un parámetro (si tuvieras más columnas para filtrar)
        // String query2 = "SELECT * FROM usuarios WHERE Name = ? AND id = ?";
        // List<Map<String, Object>> users2 = DatabaseUtils.executeQuery(query2, "Juan Pérez", 1);
        // for (Map<String, Object> user : users2) {
        //     System.out.println(user);
        // }
    }
}