import org.scalatestplus.play._
import play.api.test.Helpers._
import play.api.test._

/**
  * Add your spec here.
  * You can mock out a whole application including requests, plugins etc.
  * For more information, consult the wiki.
  */
class ApplicationSpec extends PlaySpec with OneAppPerTest {

  "Routes" should {

    "send 404 on a bad request" in {
      route(app, FakeRequest(GET, "/boum")).map(status(_)) mustBe Some(NOT_FOUND)
    }

  }

  "FibController" should {

    "render the index page" in {
      val home = route(app, FakeRequest(GET, "/")).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include("Welcome to the Fibonacci Server")
    }

    "return Fibonacci numbers" in {
      // http://www.maths.surrey.ac.uk/hosted-sites/R.Knott/Fibonacci/fibtable.html
      contentAsString(route(app, FakeRequest(GET, "/fib/5")).get) mustBe "5"
      contentAsString(route(app, FakeRequest(GET, "/fib/15")).get) mustBe "610"
      contentAsString(route(app, FakeRequest(GET, "/fib/25")).get) mustBe "75025"
    }

    "return 400 on negative input (Fibonacci numbers)" in {
      val result = route(app, FakeRequest(GET, "/fib/-5"))
      result.map(status(_)) mustBe Some(BAD_REQUEST)
      contentAsString(result.get) mustBe "input cannot be negative"
    }

    "return Fibonacci lists" in {
      contentAsString(route(app, FakeRequest(GET, "/fib_list/5")).get) mustBe "[0, 1, 1, 2, 3, 5]"
      contentAsString(route(app, FakeRequest(GET, "/fib_list/10")).get) mustBe "[0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55]"
    }

    "return 400 on negative input (Fibonacci lists)" in {
      val result = route(app, FakeRequest(GET, "/fib_list/-5"))
      result.map(status(_)) mustBe Some(BAD_REQUEST)
      contentAsString(result.get) mustBe "input cannot be negative"
    }

  }

}
