package pageobjects;

import managers.PropertiesManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
        logger.info("Începem căutarea umanizată pentru: '{}'", keyword);
        try {
            navigateWithHumanDelay();
            simulatePageReadingTime();
            handlePopupsHumanLike();
            performHumanizedSearch(keyword);
            logger.info("Căutarea umanizată completă pentru '{}'", keyword);
        } catch (Exception e) {
            logger.error("Eroare în căutarea umanizată: {}", e.getMessage());
            throw new RuntimeException("Căutarea umanizată a eșuat: " + e.getMessage(), e);
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

        // Un utilizator real petrece între 3-8 secunde observând pagina înainte să acționeze
        int readingTime = 3000 + random.nextInt(5000);
        waitWithHumanVariation(readingTime, 500);

        // Simulează scroll ușor pentru a "citi" pagina
        simulateNaturalScrolling();
    }
    private void simulateNaturalScrolling() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Scroll ușor în jos (ca și cum citești)
            js.executeScript("window.scrollBy(0, " + (100 + random.nextInt(200)) + ");");
            waitWithHumanVariation(800, 300);

            // Apoi înapoi sus (ca și cum te-ai răzgândit)
            js.executeScript("window.scrollBy(0, -" + (50 + random.nextInt(100)) + ");");
            waitWithHumanVariation(500, 200);

        } catch (Exception e) {
            logger.debug("Nu s-a putut simula scroll-ul natural: {}", e.getMessage());
        }
    }
    private void handlePopupsHumanLike() {
        logger.debug("Gestionez popup-urile cu comportament uman");

        try {
            // Așteaptă puțin să "observe" popup-ul dacă apare
            waitWithHumanVariation(1500, 500);

            // Încearcă să găsească și să închidă cookie consent
            WebElement acceptCookies = wait.until(
                    org.openqa.selenium.support.ui.ExpectedConditions
                            .elementToBeClickable(By.id("sp-cc-accept"))
            );

            // Simulează ezitarea înainte de a accepta cookies
            simulateMouseHesitation(acceptCookies);

            // Click cu mișcare naturală către element
            clickWithNaturalMovement(acceptCookies);

            logger.debug("Cookie consent acceptat cu comportament uman");

            // Pauză după click pentru a simula procesarea informației
            waitWithHumanVariation(1000, 300);

        } catch (Exception e) {
            logger.debug("Nu s-a găsit cookie consent popup sau nu era necesar");
        }
    }

    /**
     * Efectuează căutarea cu mișcări și timing naturale.
     */
    private void performHumanizedSearch(String keyword) {
        logger.debug("Efectuez căutarea cu mișcări naturale");

        try {
            // Găsește search box-ul cu așteptare graduală
            WebElement searchBoxElement = wait.until(
                    org.openqa.selenium.support.ui.ExpectedConditions
                            .visibilityOfElementLocated(searchBox)
            );

            // Mișcă mouse-ul natural către search box
            moveMouseNaturallyToElement(searchBoxElement);

            // Simulează ezitarea înainte de a începe să tastezi
            waitWithHumanVariation(800, 300);

            // Click în search box cu comportament natural
            clickWithNaturalMovement(searchBoxElement);

            // Tapează textul cu viteze umane variabile
            typeWithHumanSpeed(searchBoxElement, keyword);

            // Simulează o mică pauză de gândire înainte de search
            waitWithHumanVariation(1200, 400);

            // Efectuează search-ul
            triggerSearchHumanLike();

        } catch (Exception e) {
            logger.error("Eroare în căutarea humanizată: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Mișcă mouse-ul natural către un element, cu curbe și viteze umane.
     */
    private void moveMouseNaturallyToElement(WebElement element) {
        try {
            Actions actions = new Actions(driver);

            // În loc de mișcare directă, folosește mișcare în curbe
            actions.moveToElement(element, random.nextInt(10) - 5, random.nextInt(10) - 5);
            actions.perform();

            // Pauză mică după mișcarea mouse-ului
            waitWithHumanVariation(300, 100);

        } catch (Exception e) {
            logger.debug("Nu s-a putut mișca mouse-ul natural: {}", e.getMessage());
        }
    }

    /**
     * Simulează ezitarea mouse-ului pe care o fac oamenii înainte să facă click.
     */
    private void simulateMouseHesitation(WebElement element) {
        try {
            Actions actions = new Actions(driver);

            // Mișcări mici de "ezitare" în jurul elementului
            for (int i = 0; i < 2 + random.nextInt(3); i++) {
                int offsetX = random.nextInt(20) - 10;
                int offsetY = random.nextInt(20) - 10;
                actions.moveToElement(element, offsetX, offsetY);
                actions.perform();
                waitWithHumanVariation(200, 100);
            }

            // În final, poziționează exact pe element
            actions.moveToElement(element);
            actions.perform();

        } catch (Exception e) {
            logger.debug("Nu s-a putut simula ezitarea mouse-ului: {}", e.getMessage());
        }
    }

    /**
     * Efectuează click cu mișcare naturală și timing uman.
     */
    private void clickWithNaturalMovement(WebElement element) {
        try {
            Actions actions = new Actions(driver);

            // Mișcare finală către element
            actions.moveToElement(element);

            // Pauză foarte scurtă înainte de click (reflexul uman)
            waitWithHumanVariation(150, 50);

            // Click efectiv
            actions.click();
            actions.perform();

        } catch (Exception e) {
            logger.debug("Click standard ca fallback: {}", e.getMessage());
            element.click();
        }
    }

    /**
     * Tapează text cu viteze umane variabile, inclusiv corecții și ezitări.
     */
    private void typeWithHumanSpeed(WebElement element, String text) {
        logger.debug("Tapez '{}' cu viteze umane", text);

        try {
            // Curăță câmpul mai întâi
            element.clear();
            waitWithHumanVariation(300, 100);

            // Tapează fiecare caracter cu întârzieri variabile
            for (char c : text.toCharArray()) {
                element.sendKeys(String.valueOf(c));

                // Viteza de tastare umană: între 100-300ms per caracter
                int typingDelay = 100 + random.nextInt(200);
                waitWithHumanVariation(typingDelay, 50);

                // Ocazional, simulează o corecție (backspace și retastare)
                if (random.nextInt(100) < 5) { // 5% șansă de "greșeală"
                    simulateTypingCorrection(element, c);
                }
            }

        } catch (Exception e) {
            logger.debug("Fallback la tastare standard: {}", e.getMessage());
            element.clear();
            element.sendKeys(text);
        }
    }

    /**
     * Simulează o corecție de tastare (greșeală urmată de corecție).
     */
    private void simulateTypingCorrection(WebElement element, char correctChar) {
        try {
            // Simulează apăsarea unei taste greșite
            element.sendKeys(org.openqa.selenium.Keys.BACK_SPACE);
            waitWithHumanVariation(200, 100);

            // Retapează caracterul corect
            element.sendKeys(String.valueOf(correctChar));
            waitWithHumanVariation(150, 50);

        } catch (Exception e) {
            logger.debug("Nu s-a putut simula corecția de tastare");
        }
    }

    /**
     * Declanșează căutarea cu comportament uman (încearcă Enter, apoi butonul).
     */
    private void triggerSearchHumanLike() {
        try {
            // Majoritatea utilizatorilor apasă Enter în loc să caute butonul
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

    /**
     * Așteaptă ca pagina să se stabilizeze, verificând progresiv starea.
     */
    private void waitForPageToStabilize() {
        try {
            // Așteaptă ca JavaScript-ul să se încarce
            wait.until(driver -> ((JavascriptExecutor) driver)
                    .executeScript("return document.readyState").equals("complete"));

            // Pauză suplimentară pentru stabilizarea elementelor dinamice
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