import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(classOf[Cucumber])
@CucumberOptions(
  features = Array(
    "features"),
  glue = Array("StepDefinitions"),
  plugin = Array("pretty")
)
class RunCucumberTest {}
