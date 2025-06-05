package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;


public class SearchResultsPage extends Page {
    private static final Logger logger = LoggerFactory.getLogger(SearchResultsPage.class);
    private final By searchResults = By.cssSelector(".s-main-slot .s-result-item");
    private final By productTitles = By.cssSelector(".s-title-instructions-style .a-text-normal");

    public SearchResultsPage(WebDriver driver) {
        super(driver);
        logger.info("SearchResultsPage inițializat");
    }

    public void selectFirstResult() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(searchResults));
            List<WebElement> products = driver.findElements(productTitles);
            if (!products.isEmpty()) {
                WebElement firstProduct = products.get(0);
                Actions actions = new Actions(driver);
                actions.moveToElement(firstProduct).click().perform();
                logger.info("Primul produs selectat cu succes");
            } else {
                throw new RuntimeException("No product titles found in search results.");
            }
        } catch (Exception e) {
            logger.error("Failed to select the first product result: {}", e.getMessage());
            throw new RuntimeException("Failed to select the first product result: " + e.getMessage());
        }
    }
}