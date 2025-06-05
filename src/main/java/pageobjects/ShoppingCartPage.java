package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ShoppingCartPage extends Page {

    private final By cartItems = By.cssSelector(".sc-list-item-content");

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }

    public void verifyProductInCart() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartItems));
        boolean found = driver.findElements(cartItems).stream()
                .anyMatch(el -> el.getText().toLowerCase().contains("laptop"));

        if (!found) {
            throw new AssertionError("Expected product not found in cart.");
        }
    }
}