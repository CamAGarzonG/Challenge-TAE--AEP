package testExampleWeb.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {
    public static String readFileContent(Path filePath) throws IOException {
        return Files.readString(filePath);
    }
}
