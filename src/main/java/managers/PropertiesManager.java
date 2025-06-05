package managers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static java.lang.System.getProperty;

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
    public static boolean isHeadlessMode() {
        String headless = getProperty("headless.mode", "true");
        try {
            return Boolean.parseBoolean(headless);
        } catch (Exception e) {
            logger.warn("Invalid headless configuration: {}. Defaulting to true", headless);
            return true; // Default sigur pentru evitarea detectării
        }
    }
    public static String getHeadlessWindowSize() {
        return getProperty("headless.window.size", "1920,1080");
    }
}