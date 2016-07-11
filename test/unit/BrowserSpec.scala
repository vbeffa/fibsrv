package unit

import org.scalatestplus.play._
import play.api.test.Helpers._
import play.api.test._

/**
  * Add your spec here.
  * You can mock out a whole application including requests, plugins etc.
  * For more information, consult the wiki.
  */
class BrowserSpec extends PlaySpec with OneAppPerTest {

  "FibController" should {

    "render the index page" in {
      val home = route(app, FakeRequest(GET, "/")).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include("Welcome to the Fibonacci Server")
    }

    "return OK with a message on max input exceeded (Fibonacci numbers)" in {
      val result = route(app, FakeRequest(GET, "/browser/fib/" + Int.MaxValue))
      result.map(status(_)) mustBe Some(OK)
      contentAsString(result.get) mustBe "You are quite the eager beaver! Try using curl instead."
    }

    "return OK with a message on max input exceeded (Fibonacci lists)" in {
      val result = route(app, FakeRequest(GET, "/browser/fib_list/" + Int.MaxValue))
      result.map(status(_)) mustBe Some(OK)
      contentAsString(result.get) mustBe "You are quite the eager beaver! Try using curl instead."
    }

  }

}
