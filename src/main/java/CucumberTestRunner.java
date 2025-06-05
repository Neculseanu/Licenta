import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/main/java/features",
        glue = {"stepdefinitions"},

        plugin = {
                "pretty",
                "html:target/cucumber-html-reports",
                "json:target/cucumber-json-reports/report.json",
                "junit:target/cucumber-junit-reports/report.xml",
                "timeline:target/cucumber-timeline-reports"
        },
        monochrome = true,
        dryRun = false,
        tags = "@purchase or @smoke"
)
public class CucumberTestRunner {

}
