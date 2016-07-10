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
      contentAsString(route(app, FakeRequest(GET, "/fib/5")).get) mustBe "5"
      contentAsString(route(app, FakeRequest(GET, "/fib/15")).get) mustBe "610"
      contentAsString(route(app, FakeRequest(GET, "/fib/25")).get) mustBe "75025"
    }

    "return 400 on negative input (Fibonacci numbers)" in {
      val result = route(app, FakeRequest(GET, "/fib/-5"))
      result.map(status(_)) mustBe Some(BAD_REQUEST)
      contentAsString(result.get) mustBe "Input cannot be negative."
    }

    "return OK with a message on max input exceeded (Fibonacci numbers)" in {
      val result = route(app, FakeRequest(GET, "/fib/" + Int.MaxValue))
      result.map(status(_)) mustBe Some(OK)
      contentAsString(result.get) mustBe "You are quite the eager beaver!"
    }

    "return 400 on negative input (Fibonacci lists)" in {
      val result = route(app, FakeRequest(GET, "/fib_list/-5"))
      result.map(status(_)) mustBe Some(BAD_REQUEST)
      contentAsString(result.get) mustBe "Input cannot be negative."
    }

  }

}
