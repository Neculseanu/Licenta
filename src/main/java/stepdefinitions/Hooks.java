package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.TestContext;

public class Hooks {
    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);

    private TestContext testContext;

    public Hooks(TestContext context) {
        testContext = context;
    }

    @Before
    public void setUpBeforeEachTest() {
        testContext.getWebDriverManager().getDriver();
        logger.info("Test setup completed");
    }

    @After
    public void tearDownActionsAfterEachTest(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                takeScreenshot(scenario);
            }
            testContext.getWebDriverManager().getDriver().quit();

            logger.info("Test cleanup completed for scenario: {}", scenario.getName());
        } catch (Exception e) {
            logger.error("Error during test cleanup: {}", e.getMessage());
        }
    }

    private void takeScreenshot(Scenario scenario) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) testContext.getWebDriverManager().getDriver();
            byte[] screenshotBytes = screenshot.getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshotBytes, "image/png", "Screenshot");
            logger.info("Screenshot attached for failed scenario: {}", scenario.getName());
        } catch (Exception e) {
            logger.error("Could not take screenshot: {}", e.getMessage());
        }
    }
}