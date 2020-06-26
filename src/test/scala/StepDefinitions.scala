import com.waioeka.sbt.runner.CucumberTestSuite
import cucumber.api.PendingException
import cucumber.api.scala.{EN, ScalaDsl}
import org.scalatest.matchers.should.Matchers

class StepDefinitions extends ScalaDsl with EN with Matchers with CucumberTestSuite {
  override def features = List(
    "features/create-track.feature",
    "features/delete-track.feature",
    "features/get-track.feature",
    "features/update-track.feature"
  )

  val testDataStore = new TestRedisClient()

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
