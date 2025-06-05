package managers;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesManager {
    public static Properties proprietatiDeTestare = new Properties();
    private static final String pathToProeprties = "src/main/resources/configuration.properties";

    static {
        try {
            Thread.sleep((long) (Math.random() * 1000 + 500)); // random delay între 500-1500ms
            proprietatiDeTestare.load(new FileReader(pathToProeprties));
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new RuntimeException("Eroare la încărcarea configuration.properties", exception);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static String getBrowserType() {
        try {
            Thread.sleep((long) (Math.random() * 300 + 300));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        String browserType = proprietatiDeTestare.getProperty("browserType");
        return (browserType != null && !browserType.isBlank()) ? browserType.trim() : "CHROME";
    }

    public static String getBaseUrl() {
        try {
            Thread.sleep((long) (Math.random() * 300 + 300));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        String baseUrl = proprietatiDeTestare.getProperty("Base_URL");
        return (baseUrl != null && !baseUrl.isBlank()) ? baseUrl.trim() : "https://www.amazon.com";
    }
}
