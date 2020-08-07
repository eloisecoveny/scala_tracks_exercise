import com.waioeka.sbt.runner.CucumberTestSuite
import cucumber.api.PendingException
import cucumber.api.scala.{EN, ScalaDsl}
import org.scalatest.matchers.should.Matchers

class StepDefinitions extends ScalaDsl with EN with Matchers with CucumberTestSuite {

  val testDataStore = new TestRedisClient()

  Given("""record with ID {string} is added to the store:"""){ (arg1: String, arg0: String) =>
    //// Write code here that turns the phrase above into concrete actions
    throw new PendingException()
  }
  Then("""the response should match json:"""){ (arg0: String) =>
    //// Write code here that turns the phrase above into concrete actions
    throw new PendingException()
  }
  Given("""I add a new test {string}"""){ (arg0: String) =>
    //// Write code here that turns the phrase above into concrete actions
    throw new PendingException()
  }

  When("""I send {string} request to {string}""") { (int: Int) =>
    //Write code here
    throw new PendingException()
  }

  When("""the response code should equal {int}""") { (int: Int) =>
    //Write code here
    throw new PendingException()
  }

  When("""there should be {int} records in the store""") { (int: Int) =>
    //Write code here
    throw new PendingException()
  }

  When("""record with ID {string} should be in the store:""") { (string: String, docString: String) =>
    //Write code here
    throw new PendingException()
  }

//  When("""I send {string} request to {string}""") { (string: String, string2: String, docString: String) =>
//    //Write code here
//    throw new PendingException()
//  }

}
