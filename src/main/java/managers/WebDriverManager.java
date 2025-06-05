package managers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import static managers.PropertiesManager.proprietatiDeTestare;

public class WebDriverManager {

    private static WebDriver driver;
    public static String browserType;

    public WebDriverManager(String browserType) {
        WebDriverManager.browserType = browserType;
    }

    public static void initializareDriver() {
        try {
            Thread.sleep((long) (Math.random() * 1000 + 500)); // delay între 500-1500ms înainte de inițializare
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (browserType == null || browserType.isEmpty()) {
            browserType = proprietatiDeTestare.getProperty("browserType") != null
                    ? proprietatiDeTestare.getProperty("browserType")
                    : "CHROME";
        }

        switch (browserType.toUpperCase()) {
            case "CHROME":
                System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver.exe");
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--remote-allow-origins=*");
                driver = new ChromeDriver(chromeOptions);
                break;
            case "FIREFOX":
                System.setProperty("webdriver.gecko.driver", "src/main/resources/drivers/geckodriver.exe");
                driver = new FirefoxDriver();
                break;
            default:
                throw new RuntimeException("Browser necunoscut: " + browserType);
        }

        try {
            Thread.sleep((long) (Math.random() * 1000 + 1000)); // delay după lansare browser 1000-2000ms
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            initializareDriver();
        }
        return driver;
    }

    public static void setDriver(WebDriver newDriver) {
        driver = newDriver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
