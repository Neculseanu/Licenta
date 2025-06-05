package pageobjects;

import managers.PropertiesManager;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;


public class HomePage extends Page {
    private static final Logger logger = LoggerFactory.getLogger(HomePage.class);

    private final By searchBox = By.id("twotabsearchtextbox");
    private final Random random = new Random();

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void searchFor(String keyword) {
        logger.info("Execut căutare directă pentru: '{}'", keyword);

        try {
            // Abordare simplă și directă
            WebElement searchBoxElement = wait.until(
                    ExpectedConditions.elementToBeClickable(By.id("twotabsearchtextbox"))
            );

            searchBoxElement.clear();
            searchBoxElement.sendKeys(keyword);
            searchBoxElement.sendKeys(Keys.ENTER);

            logger.info("Căutarea executată cu succes");

        } catch (Exception e) {
            logger.error("Eroare în căutare: {}", e.getMessage());
            throw new RuntimeException("Căutarea a eșuat: " + e.getMessage(), e);
        }
    }
    private void navigateWithHumanDelay() {
        logger.debug("Navighez Amazon cu comportament uman");
        driver.get(PropertiesManager.getBaseUrl());
        waitWithHumanVariation(2000, 1000);
        waitForPageToStabilize();
    }
    private void simulatePageReadingTime() {
        logger.debug("Simulez timpul de observare a paginii");

        int readingTime = 3000 + random.nextInt(5000);
        waitWithHumanVariation(readingTime, 500);

        simulateNaturalScrolling();
    }
    private void simulateNaturalScrolling() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;

            js.executeScript("window.scrollBy(0, " + (100 + random.nextInt(200)) + ");");
            waitWithHumanVariation(800, 300);

            js.executeScript("window.scrollBy(0, -" + (50 + random.nextInt(100)) + ");");
            waitWithHumanVariation(500, 200);

        } catch (Exception e) {
            logger.debug("Nu s-a putut simula scroll-ul natural: {}", e.getMessage());
        }
    }
    private void handlePopupsHumanLike() {
        logger.debug("Gestionez popup-urile cu comportament uman");

        try {
            waitWithHumanVariation(1500, 500);

            WebElement acceptCookies = wait.until(
                    org.openqa.selenium.support.ui.ExpectedConditions
                            .elementToBeClickable(By.id("sp-cc-accept"))
            );

            simulateMouseHesitation(acceptCookies);

            clickWithNaturalMovement(acceptCookies);

            logger.debug("Cookie consent acceptat cu comportament uman");

            waitWithHumanVariation(1000, 300);

        } catch (Exception e) {
            logger.debug("Nu s-a găsit cookie consent popup sau nu era necesar");
        }
    }
    private void performHumanizedSearch(String keyword) {
        logger.debug("Efectuez căutarea cu mișcări naturale");

        try {
            WebElement searchBoxElement = wait.until(
                    org.openqa.selenium.support.ui.ExpectedConditions
                            .visibilityOfElementLocated(searchBox)
            );

            moveMouseNaturallyToElement(searchBoxElement);
            waitWithHumanVariation(800, 300);
            clickWithNaturalMovement(searchBoxElement);
            typeWithHumanSpeed(searchBoxElement, keyword);
            waitWithHumanVariation(1200, 400);
            triggerSearchHumanLike();

        } catch (Exception e) {
            logger.error("Eroare în căutarea humanizată: {}", e.getMessage());
            throw e;
        }
    }
    private void moveMouseNaturallyToElement(WebElement element) {
        try {
            Actions actions = new Actions(driver);

            actions.moveToElement(element, random.nextInt(10) - 5, random.nextInt(10) - 5);
            actions.perform();

            waitWithHumanVariation(300, 100);

        } catch (Exception e) {
            logger.debug("Nu s-a putut mișca mouse-ul natural: {}", e.getMessage());
        }
    }

    private void simulateMouseHesitation(WebElement element) {
        try {
            Actions actions = new Actions(driver);
            for (int i = 0; i < 2 + random.nextInt(3); i++) {
                int offsetX = random.nextInt(20) - 10;
                int offsetY = random.nextInt(20) - 10;
                actions.moveToElement(element, offsetX, offsetY);
                actions.perform();
                waitWithHumanVariation(200, 100);
            }

            actions.moveToElement(element);
            actions.perform();

        } catch (Exception e) {
            logger.debug("Nu s-a putut simula ezitarea mouse-ului: {}", e.getMessage());
        }
    }

    private void clickWithNaturalMovement(WebElement element) {
        try {
            Actions actions = new Actions(driver);

            actions.moveToElement(element);
            waitWithHumanVariation(150, 50);
            actions.click();
            actions.perform();

        } catch (Exception e) {
            logger.debug("Click standard ca fallback: {}", e.getMessage());
            element.click();
        }
    }
    private void typeWithHumanSpeed(WebElement element, String text) {
        logger.debug("Tapez '{}' cu viteze umane", text);

        try {
            element.clear();
            waitWithHumanVariation(300, 100);

            for (char c : text.toCharArray()) {
                element.sendKeys(String.valueOf(c));
                int typingDelay = 100 + random.nextInt(200);
                waitWithHumanVariation(typingDelay, 50);

                if (random.nextInt(100) < 5) {
                    simulateTypingCorrection(element, c);
                }
            }

        } catch (Exception e) {
            logger.debug("Fallback la tastare standard: {}", e.getMessage());
            element.clear();
            element.sendKeys(text);
        }
    }

    private void simulateTypingCorrection(WebElement element, char correctChar) {
        try {
            element.sendKeys(org.openqa.selenium.Keys.BACK_SPACE);
            waitWithHumanVariation(200, 100);
            element.sendKeys(String.valueOf(correctChar));
            waitWithHumanVariation(150, 50);

        } catch (Exception e) {
            logger.debug("Nu s-a putut simula corecția de tastare");
        }
    }
    private void triggerSearchHumanLike() {
        try {
            WebElement searchBoxElement = driver.findElement(searchBox);
            searchBoxElement.sendKeys(org.openqa.selenium.Keys.ENTER);

            logger.debug("Căutare declanșată cu Enter (comportament uman tipic)");

        } catch (Exception e) {
            logger.debug("Fallback la butonul de căutare");
            try {
                WebElement searchButton = driver.findElement(By.id("nav-search-submit-button"));
                clickWithNaturalMovement(searchButton);
            } catch (Exception fallbackException) {
                logger.error("Nici o metodă de căutare nu a funcționat");
                throw fallbackException;
            }
        }
    }
    private void waitForPageToStabilize() {
        try {
            wait.until(driver -> ((JavascriptExecutor) driver)
                    .executeScript("return document.readyState").equals("complete"));

            waitWithHumanVariation(1500, 500);

        } catch (Exception e) {
            logger.debug("Nu s-a putut verifica stabilizarea paginii complet");
        }
    }

    /**
     * Implementează pauze cu variații umane naturale.
     *
     * @param baseDelay întârzierea de bază în milisecunde
     * @param variation variația maximă în plus/minus
     */
    private void waitWithHumanVariation(int baseDelay, int variation) {
        try {
            int actualDelay = baseDelay + random.nextInt(variation * 2) - variation;
            actualDelay = Math.max(actualDelay, 100); // Minim 100ms

            Thread.sleep(actualDelay);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("Întrerupere în timpul așteptării umane");
        }
    }
}