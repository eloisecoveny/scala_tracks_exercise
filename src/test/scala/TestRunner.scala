import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(classOf[Cucumber])
@CucumberOptions(
  features = Array(
    "src/test/resources/features/create-track.feature",
    "src/test/resources/features/delete-track.feature",
    "src/test/resources/features/get-track.feature",
    "src/test/resources/features/update-track.feature"
  ),
  glue = Array("StepDefinitions")
)
class TestRunner {}
