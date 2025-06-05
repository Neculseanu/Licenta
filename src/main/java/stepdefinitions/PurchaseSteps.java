package stepdefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import managers.WebDriverManager;
import org.openqa.selenium.WebDriver;
import pageobjects.*;

public class PurchaseSteps {

    private WebDriver driver = WebDriverManager.getDriver();
    private HomePage homePage = new HomePage(driver);
    private SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
    private ProductDetailsPage productDetailsPage = new ProductDetailsPage(driver);
    private ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);

    @Given("I am on the Amazon home page")
    public void i_am_on_amazon_home_page() {
        driver.get("https://www.amazon.com");
    }

    @When("I search for {string}")
    public void i_search_for(String keyword) {
        homePage.searchFor(keyword);
    }

    @When("I select the first product from the results")
    public void i_select_first_product() {
        searchResultsPage.selectFirstResult();
    }

    @When("I add the product to the shopping cart")
    public void i_add_product_to_cart() {
        productDetailsPage.addToCart();
    }

    @When("I view the shopping cart")
    public void i_view_shopping_cart() {
        productDetailsPage.viewCart();
    }

    @Then("I should see the product listed in the cart")
    public void i_should_see_product_in_cart() {
        shoppingCartPage.verifyProductInCart();
    }
}
