import org.scalatestplus.play._
import play.api.test.FakeRequest
import play.api.test.Helpers._

class ApplicationSpec extends PlaySpec with OneAppPerTest {

  "Routes" should {

    "send 404 on a bad request" in {
      route(app, FakeRequest(GET, "/boum")).map(status(_)) mustBe Some(NOT_FOUND)
    }

  }

  "FibController" should {

    "return Fibonacci numbers" in {
      contentAsString(route(app, FakeRequest(GET, "/fib/5")).get) mustBe "5"
      contentAsString(route(app, FakeRequest(GET, "/fib/15")).get) mustBe "610"
      contentAsString(route(app, FakeRequest(GET, "/fib/25")).get) mustBe "75025"
    }

    "return Fibonacci lists" is pending // throws java.lang.UnsupportedOperationException: No materializer was provided

    "return 400 on negative input (Fibonacci numbers)" in {
      val result = route(app, FakeRequest(GET, "/fib/-5"))
      result.map(status(_)) mustBe Some(BAD_REQUEST)
      contentAsString(result.get) mustBe "Input cannot be negative."
    }

    "return 400 on negative input (Fibonacci lists)" in {
      val result = route(app, FakeRequest(GET, "/fib_list/-5"))
      result.map(status(_)) mustBe Some(BAD_REQUEST)
      contentAsString(result.get) mustBe "Input cannot be negative."
    }

  }

}
