package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductDetailsPage extends Page {

    private final By addToCartButton = By.id("add-to-cart-button");
    private final By cartIcon = By.id("nav-cart");

    public ProductDetailsPage(WebDriver driver) {
        super(driver);
    }

    public void addToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
    }

    public void viewCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartIcon)).click();
    }
}