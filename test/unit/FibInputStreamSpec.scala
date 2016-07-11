package unit

import org.scalatest.{MustMatchers, WordSpec}
import services.{FibInputStream, FibMemoizer}

class FibInputStreamSpec extends WordSpec with MustMatchers {
  val memoizer = new FibMemoizer(1000)

  "FibonacciService" should {
    "stream fib_list(0)" in {
      val f = new FibInputStream(0, memoizer)
      for (c <- "[0]\n") {
        f.read() mustBe c
      }
      f.read() mustBe -1
    }

    "stream fib_list(1)" in {
      val f = new FibInputStream(1, memoizer)
      for (c <- "[0, 1]\n") {
        f.read() mustBe c
      }
      f.read() mustBe -1
    }

    "stream fib_list(5)" in {
      val f = new FibInputStream(5, memoizer)
      for (c <- "[0, 1, 1, 2, 3, 5]\n") {
        f.read() mustBe c
      }
      f.read() mustBe -1
    }

    "stream fib_list(10)" in {
      val f = new FibInputStream(10, memoizer)
      for (c <- "[0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55]\n") {
        f.read() mustBe c
      }
      f.read() mustBe -1
    }
  }
}
