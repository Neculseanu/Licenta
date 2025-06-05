import managers.WebDriverManager;
import org.openqa.selenium.WebDriver;
import pageobjects.HomePage;
import pageobjects.ProductDetailsPage;
import pageobjects.SearchResultsPage;
import pageobjects.ShoppingCartPage;

public class TestRunner {
    public static void main(String[] args) {
        WebDriver driver = WebDriverManager.getDriver();

        try {
            HomePage homePage = new HomePage(driver);
            homePage.searchFor("laptop");

            SearchResultsPage resultsPage = new SearchResultsPage(driver);
            resultsPage.selectFirstResult();

            ProductDetailsPage productPage = new ProductDetailsPage(driver);
            productPage.addToCart();
            productPage.viewCart();

            ShoppingCartPage cartPage = new ShoppingCartPage(driver);
            cartPage.verifyProductInCart();

            System.out.println("✅ Test completed successfully.");

        } catch (Exception e) {
            System.err.println("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            WebDriverManager.quitDriver();
        }
    }
}
