package utils;

import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonUtils {

    public static String readJsonFile(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler arquivo JSON: " + filePath, e);
        }
    }
}