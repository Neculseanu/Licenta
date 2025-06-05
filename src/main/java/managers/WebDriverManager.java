package managers;

// Importurile rămân la fel
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static managers.PropertiesManager.proprietatiDeTestare;

public class WebDriverManager {
    private static final Logger logger = LoggerFactory.getLogger(WebDriverManager.class);

    // ThreadLocal pentru thread safety
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static String browserType;

    public WebDriverManager(String browserType) {
        WebDriverManager.browserType = browserType;
    }

    public static void initializareDriver() {
        if (browserType == null || browserType.isEmpty()) {
            browserType = proprietatiDeTestare.getProperty("browserType") != null
                    ? proprietatiDeTestare.getProperty("browserType")
                    : "CHROME";
        }

        switch (browserType.toUpperCase()) {
            case "CHROME":
                io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();

                // Verificăm dacă să rulăm în headless mode
                boolean isHeadless = false;

                if (isHeadless) {
                    // Configurări specifice pentru headless mode - strategia invizibilității
                    chromeOptions.addArguments("--headless");
                    chromeOptions.addArguments("--window-size=" + PropertiesManager.getHeadlessWindowSize());
                    logger.info("🔒 Running in HEADLESS mode for stealth automation");
                } else {
                    logger.info("👁️ Running in NORMAL mode for debugging visibility");
                }

                // Configurări comune pentru ambele moduri - optimizare pentru evitarea detectării
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--disable-extensions");
                chromeOptions.addArguments("--disable-plugins");
                chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
                chromeOptions.addArguments("--disable-web-security");

                // Configurări pentru user agent mai natural
                chromeOptions.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");

                driver.set(new ChromeDriver(chromeOptions));
                break;

            case "FIREFOX":
                // Similar logic for Firefox if needed
                io.github.bonigarcia.wdm.WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();

                if (PropertiesManager.isHeadlessMode()) {
                    firefoxOptions.addArguments("--headless");
                    logger.info("🔒 Running Firefox in HEADLESS mode");
                }

                driver.set(new FirefoxDriver(firefoxOptions));
                break;

            default:
                throw new RuntimeException("Browser necunoscut: " + browserType);
        }

        if (!PropertiesManager.isHeadlessMode()) {
            getDriver().manage().window().maximize();
        }
        logger.info("✅ Driver inițializat cu succes pentru {} (headless: {})",
                browserType, PropertiesManager.isHeadlessMode());
    }

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            initializareDriver();
        }
        return driver.get();
    }

    public static void setDriver(WebDriver newDriver) {
        driver.set(newDriver);
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}