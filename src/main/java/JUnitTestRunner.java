import managers.PropertiesManager;
import managers.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pageobjects.*;

import java.time.Duration;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test Achizitie Amazon")
public class JUnitTestRunner {

    private static final Logger logger = LoggerFactory.getLogger(JUnitTestRunner.class);

    private static final String SEARCH_KEYWORD = "laptop";
    private static final int TEST_TIMEOUT_SECONDS = 60;

    private static WebDriver driver;
    private static HomePage homePage;
    private static SearchResultsPage searchResultsPage;
    private static ProductDetailsPage productDetailsPage;
    private static ShoppingCartPage shoppingCartPage;

    @BeforeAll
    static void setUpClass() {
        logger.info("Începerea configurării testului de Achizitie pe Amazon");

        try {
            driver = WebDriverManager.getDriver();
            logger.info("WebDriver inițializat cu succes: {}", driver.getClass().getSimpleName());

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            initializePageObjects();

            logger.info("Test suite configuration completed successfully");

        } catch (Exception e) {
            logger.error("Eroare critică în configurarea test suite: {}", e.getMessage());
            throw new RuntimeException("Failed to initialize test environment", e);
        }
    }

    private static void initializePageObjects() {
        homePage = new HomePage(driver);
        searchResultsPage = new SearchResultsPage(driver);
        productDetailsPage = new ProductDetailsPage(driver);
        shoppingCartPage = new ShoppingCartPage(driver);
        logger.info("Page Objects inițializate: HomePage, SearchResultsPage, ProductDetailsPage, ShoppingCartPage");
    }

    @BeforeEach
    void setUp() {
        logger.info("Pregătirea testului - navigare la Amazon homepage");

        try {
            String baseUrl = PropertiesManager.getBaseUrl();
            driver.get(baseUrl);
            logger.info("📍 Navigat cu succes la: {}", baseUrl);
            String currentUrl = driver.getCurrentUrl();
            String pageTitle = driver.getTitle();

           Assertions.assertTrue(
                    currentUrl.toLowerCase().contains("amazon"),
                    "URL-ul curent nu conține 'amazon': " + currentUrl
            );

            logger.info("Verificare homepage completă - URL: {}, Title: {}",
                    currentUrl.substring(0, Math.min(currentUrl.length(), 50)) + "...",
                    pageTitle.substring(0, Math.min(pageTitle.length(), 50)) + "...");

        } catch (Exception e) {
            logger.error("Eroare în navigarea la homepage: {}", e.getMessage());
            throw new RuntimeException("Failed to navigate to Amazon homepage", e);
        }
    }

    @Test
    @Order(1)
    @DisplayName("Complete Purchase Journey - Search, Select, Add to Cart")
    @Timeout(value = TEST_TIMEOUT_SECONDS)
    void testCompletePurchaseJourney() {
        logger.info("Începerea testului complet de purchase journey");

        executeProductSearch();
        selectFirstProduct();
        addProductToCart();
        navigateToShoppingCart();
        verifyProductInCart();
        logger.info("Test complet finalizat cu succes!");
    }

    @Test
    @Order(2)
    @DisplayName("Search Functionality Test")
    void testSearchFunctionality() {
        logger.info("Testarea funcționalității de căutare");

        executeProductSearch();

        String currentUrl = driver.getCurrentUrl();
        Assertions.assertTrue(
                currentUrl.contains("s?k=") || currentUrl.contains("field-keywords") || currentUrl.contains("search"),
                "URL-ul nu indică o pagină de rezultate căutare: " + currentUrl
        );

        logger.info("Test căutare completat cu succes");
    }

    private void executeProductSearch() {
        logger.info("STEP: Căutare produs pentru '{}'", SEARCH_KEYWORD);

        try {
            homePage.searchFor(SEARCH_KEYWORD);

            String currentUrl = driver.getCurrentUrl();
            boolean searchExecuted = currentUrl.contains("s?k=") ||
                    currentUrl.contains("field-keywords") ||
                    currentUrl.contains("search");

            Assertions.assertTrue(searchExecuted,
                    "Căutarea nu pare să fi fost executată. URL curent: " + currentUrl);

            logger.info("Căutare executată cu succes pentru '{}'", SEARCH_KEYWORD);

        } catch (Exception e) {
            logger.error("Eroare în execuția căutării: {}", e.getMessage());
            throw new AssertionError("Search execution failed for keyword: " + SEARCH_KEYWORD, e);
        }
    }

    private void selectFirstProduct() {
        logger.info("STEP: Selectare primul produs din rezultate");

        try {
            searchResultsPage.selectFirstResult();

            String currentUrl = driver.getCurrentUrl();
            boolean onProductPage = currentUrl.contains("/dp/") ||
                    currentUrl.contains("/gp/product/") ||
                    (!currentUrl.contains("s?k=") && !currentUrl.contains("search"));

            Assertions.assertTrue(onProductPage,
                    "Nu pare că am ajuns pe o pagină de produs. URL: " + currentUrl);

            logger.info("Produs selectat cu succes - URL: {}",
                    currentUrl.substring(0, Math.min(currentUrl.length(), 80)) + "...");

        } catch (Exception e) {
            logger.error("Eroare în selectarea produsului: {}", e.getMessage());
            throw new AssertionError("Failed to select first product from search results", e);
        }
    }

    private void addProductToCart() {
        logger.info("STEP: Adăugare produs în coș");

        try {
            productDetailsPage.addToCart();
            Thread.sleep(2000);

            logger.info("Produs adăugat în coș cu succes");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Întrerupere în timpul adăugării în coș");
            throw new RuntimeException("Thread interrupted during add to cart", e);
        } catch (Exception e) {
            logger.error("Eroare în adăugarea în coș: {}", e.getMessage());
            throw new AssertionError("Failed to add product to cart", e);
        }
    }
    private void navigateToShoppingCart() {
        logger.info("STEP: Navigare la coșul de cumpărături");

        try {
            productDetailsPage.viewCart();

            String currentUrl = driver.getCurrentUrl();
            boolean onCartPage = currentUrl.contains("cart") ||
                    currentUrl.contains("gp/cart") ||
                    currentUrl.contains("sw/cart");

            Assertions.assertTrue(onCartPage,
                    "Nu pare că am ajuns pe pagina coșului. URL: " + currentUrl);

            logger.info("Navigare la coș realizată cu succes - URL: {}", currentUrl);

        } catch (Exception e) {
            logger.error("Eroare în navigarea la coș: {}", e.getMessage());
            throw new AssertionError("Failed to navigate to shopping cart", e);
        }
    }
    private void verifyProductInCart() {
        logger.info("STEP: Verificare finală - produs în coș");

        try {
            shoppingCartPage.verifyProductInCart();

            logger.info("Verificare completă: Produsul este prezent în coșul de cumpărături!");

        } catch (AssertionError e) {
            logger.error("Produsul nu a fost găsit în coș: {}", e.getMessage());

            try {
                String currentUrl = driver.getCurrentUrl();
                String pageTitle = driver.getTitle();
                logger.error("Debug info - URL: {}, Title: {}", currentUrl, pageTitle);
            } catch (Exception debugException) {
                logger.error("Nu s-au putut obține informații de debugging");
            }

            throw new AssertionError("Product verification in cart failed", e);
        } catch (Exception e) {
            logger.error("Eroare neașteptată în verificarea coșului: {}", e.getMessage());
            throw new AssertionError("Unexpected error during cart verification", e);
        }
    }
    @AfterEach
    void tearDown() {
        logger.info("Curățare post-test");

        try {
            String currentUrl = driver.getCurrentUrl();
            logger.info("Test finalizat la URL: {}", currentUrl);

        } catch (Exception e) {
            logger.warn("Avertisment în curățarea post-test: {}", e.getMessage());
        }
    }
    @AfterAll
    static void tearDownClass() {
        logger.info("Curățare finală test suite");

        try {
            if (driver != null) {
                WebDriverManager.quitDriver();
                logger.info("WebDriver închis cu succes");
            }
        } catch (Exception e) {
            logger.error("Eroare în închiderea WebDriver: {}", e.getMessage());
        }

        logger.info("Test Achizitionare Amazon finalizat complet");
    }
}