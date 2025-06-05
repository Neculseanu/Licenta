package managers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class WebDriverWaiterManager {
    private static final Logger logger = LoggerFactory.getLogger(WebDriverWaiterManager.class);

    public static void waitElementToBeVisible(WebElement element, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(PropertiesManager.getDefaultTimeout()));
        wait.until(ExpectedConditions.visibilityOf(element));
        logger.debug("Element became visible successfully");
    }

    public static void waitElementToBeClickable(WebElement element, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(PropertiesManager.getDefaultTimeout()));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        logger.debug("Element became clickable successfully");
    }

    public static void waitElementToBeVisible(WebElement element, WebDriver driver, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        wait.until(ExpectedConditions.visibilityOf(element));
        logger.debug("Element became visible successfully with custom timeout: {}s", timeoutSeconds);
    }

    public static void waitElementToBeClickable(WebElement element, WebDriver driver, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        logger.debug("Element became clickable successfully with custom timeout: {}s", timeoutSeconds);
    }
}