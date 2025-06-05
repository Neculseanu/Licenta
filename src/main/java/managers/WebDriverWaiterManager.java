package managers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebDriverWaiterManager {

    private static WebDriverWait driverWaiter;

    private static void initializeWebDriverWaiter(WebDriver driver) {
        if (driverWaiter == null) driverWaiter = new WebDriverWait(driver, Duration.ofSeconds(30));
    }


    public static void waitElementToBeVisible(WebElement element, WebDriver driver) {
        initializeWebDriverWaiter(driver);
        driverWaiter.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitElementToBeClickable(WebElement element, WebDriver driver) {
        initializeWebDriverWaiter(driver);
        driverWaiter.until(ExpectedConditions.elementToBeClickable(element));
    }
}
