package managers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesManager {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesManager.class);

    public static Properties proprietatiDeTestare = new Properties();
    private static final String pathToProeprties = "src/main/resources/configuration.properties";

    static {
        try {
            proprietatiDeTestare.load(new FileReader(pathToProeprties));
            logger.info("Proprietăți încărcate cu succes din: {}", pathToProeprties);
        } catch (IOException exception) {
            logger.error("Eroare la încărcarea configuration.properties: {}", exception.getMessage());
            exception.printStackTrace();
            throw new RuntimeException("Eroare la încărcarea configuration.properties", exception);
        }
    }

    public static String getBrowserType() {
        String browserType = proprietatiDeTestare.getProperty("browserType");
        return (browserType != null && !browserType.isBlank()) ? browserType.trim() : "CHROME";
    }

    public static String getBaseUrl() {
        String baseUrl = proprietatiDeTestare.getProperty("Base_URL");
        return (baseUrl != null && !baseUrl.isBlank()) ? baseUrl.trim() : "https://www.amazon.com";
    }

    public static int getDefaultTimeout() {
        String timeout = proprietatiDeTestare.getProperty("default.timeout", "30");
        try {
            return Integer.parseInt(timeout);
        } catch (NumberFormatException e) {
            logger.warn("Timeout invalid: {}. Folosesc 30 secunde", timeout);
            return 30;
        }
    }
}