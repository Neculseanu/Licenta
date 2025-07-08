package stepdefinitions;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import managers.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pageobjects.*;

public class PurchaseSteps {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseSteps.class);

    private WebDriver driver = WebDriverManager.getDriver();
    private HomePage homePage = new HomePage(driver);
    private SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
    private ProductDetailsPage productDetailsPage = new ProductDetailsPage(driver);
    private ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);

    @Given("I am on the Amazon home page")
    public void i_am_on_amazon_home_page() {
        driver.get("https://www.amazon.com");  //aici alegem broswerul pe care dorim sa efectuam testarea prin Cucumber.
        logger.info("Navigated to Amazon home page");
    }

    @When("I search for {string}")
    public void i_search_for(String keyword) {
        homePage.searchFor(keyword);
        logger.info("Searched for: {}", keyword);
    }

    @When("I select the first product from the results")
    public void i_select_first_product() {
        searchResultsPage.selectFirstResult();
        logger.info("Selected first product from results");
    }

    @When("I add the product to the shopping cart")
    public void i_add_product_to_cart() {
        productDetailsPage.addToCart();
        logger.info("Added product to cart");
    }

    @When("I view the shopping cart")
    public void i_view_shopping_cart() {
        productDetailsPage.viewCart();
        logger.info("Viewed shopping cart");
    }

    @Then("I should see the product listed in the cart")
    public void i_should_see_product_in_cart() {
        shoppingCartPage.verifyProductInCart();
        logger.info("Verified product is in cart");
    }
}