package managers;

// Importurile rămân la fel
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
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
                chromeOptions.addArguments("--remote-allow-origins=*");
                driver.set(new ChromeDriver(chromeOptions));
                break;
            case "FIREFOX":
                io.github.bonigarcia.wdm.WebDriverManager.firefoxdriver().setup();
                driver.set(new FirefoxDriver());
                break;
            default:
                throw new RuntimeException("Browser necunoscut: " + browserType);
        }

        getDriver().manage().window().maximize();
        logger.info("Driver inițializat cu succes pentru {}", browserType);
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