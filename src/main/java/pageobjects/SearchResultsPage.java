package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SearchResultsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By searchResults = By.cssSelector(".s-main-slot .s-result-item");
    private final By productTitles = By.cssSelector(".s-title-instructions-style .a-text-normal");

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void selectFirstResult() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(searchResults));
            List<WebElement> products = driver.findElements(productTitles);
            if (!products.isEmpty()) {
                WebElement firstProduct = products.get(0);

                Actions actions = new Actions(driver);
                actions.moveToElement(firstProduct).pause(Duration.ofMillis((long) (Math.random() * 500 + 500))).click().perform();
            } else {
                throw new RuntimeException("No product titles found in search results.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to select the first product result: " + e.getMessage());
        }
    }
}
