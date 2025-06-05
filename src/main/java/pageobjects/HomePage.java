package pageobjects;

import managers.PropertiesManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends Page {

    private final By searchBox = By.id("twotabsearchtextbox");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void searchFor(String keyword) {
        driver.get(PropertiesManager.getBaseUrl());

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 1. Închide consent popup dacă apare
        try {
            WebElement acceptCookies = wait.until(ExpectedConditions.elementToBeClickable(By.id("sp-cc-accept")));
            acceptCookies.click();
        } catch (Exception ignored) {
            // Nu e prezent => continuăm
        }

        // 2. Acum căutarea
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("twotabsearchtextbox")));
        searchBox.sendKeys(keyword);

        WebElement searchButton = driver.findElement(By.id("nav-search-submit-button"));
        searchButton.click();
    }
}