import org.scalatestplus.play._
import play.api.test._
import play.api.test.Helpers._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class ApplicationSpec extends PlaySpec with OneAppPerTest {

  "Routes" should {

    "send 404 on a bad request" in  {
      route(app, FakeRequest(GET, "/boum")).map(status(_)) mustBe Some(NOT_FOUND)
    }

  }

  "HomeController" should {

    "render the index page" in {
      val home = route(app, FakeRequest(GET, "/")).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Welcome to the Fibonacci Server")
    }

  }

  "FibController" should {

    "return Fibonacci numbers" in {
      // http://www.maths.surrey.ac.uk/hosted-sites/R.Knott/Fibonacci/fibtable.html
      contentAsString(route(app, FakeRequest(GET, "/fib/5")).get) mustBe "5"
      contentAsString(route(app, FakeRequest(GET, "/fib/15")).get) mustBe "610"
      contentAsString(route(app, FakeRequest(GET, "/fib/25")).get) mustBe "75025"
    }

    "return Fibonacci lists" in {
      contentAsString(route(app, FakeRequest(GET, "/fib_list/5")).get) mustBe "[0, 1, 1, 2, 3, 5]"
      contentAsString(route(app, FakeRequest(GET, "/fib_list/10")).get) mustBe "[0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55]"
    }

    "return cumulative Fibonacci lists" in {
      contentAsString(route(app, FakeRequest(GET, "/cum_fib_list/1")).get) mustBe "[\n  [0],\n  [0, 1]\n]"
      contentAsString(route(app, FakeRequest(GET, "/cum_fib_list/2")).get) mustBe "[\n  [0],\n  [0, 1],\n  [0, 1, 1]\n]"
      contentAsString(route(app, FakeRequest(GET, "/cum_fib_list/3")).get) mustBe "[\n  [0],\n  [0, 1],\n  [0, 1, 1],\n  [0, 1, 1, 2]\n]"
    }

  }

  "CountController" should {

    "return an increasing count" in {
      contentAsString(route(app, FakeRequest(GET, "/count")).get) mustBe "0"
      contentAsString(route(app, FakeRequest(GET, "/count")).get) mustBe "1"
      contentAsString(route(app, FakeRequest(GET, "/count")).get) mustBe "2"
    }

  }

}
