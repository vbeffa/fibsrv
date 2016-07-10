package unit

import java.security.MessageDigest

import org.scalatest.{MustMatchers, WordSpec}
import play.api.Configuration
import services.{FibInputStream, FibonacciServiceImpl}

class FibSpec extends WordSpec with MustMatchers {
  val config = Configuration(
    "fibonacci.max_fib_input" -> Math.pow(2, 16).toInt,
    "fibonacci.max_fib_input" -> Math.pow(2, 12).toInt)
  val fibonacci: FibonacciServiceImpl = new FibonacciServiceImpl(config)

  "FibonacciService" should  {
    "stream fib_list(0)" in {
      val f = new FibInputStream(0, new FibonacciServiceImpl(config))
      for (c <- "[0]\n") {
        f.read() mustBe c
      }
      f.read() mustBe -1
    }

    "stream fib_list(1)" in {
      val f = new FibInputStream(1, new FibonacciServiceImpl(config))
      for (c <- "[0, 1]\n") {
        f.read() mustBe c
      }
      f.read() mustBe -1
    }

    "stream fib_list(5)" in {
      val f = new FibInputStream(5, new FibonacciServiceImpl(config))
      for (c <- "[0, 1, 1, 2, 3, 5]\n") {
        f.read() mustBe c
      }
      f.read() mustBe -1
    }

    "stream fib_list(10)" in {
      val f = new FibInputStream(10, new FibonacciServiceImpl(config))
      for (c <- "[0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55]\n") {
        f.read() mustBe c
      }
      f.read() mustBe -1
    }
  }
}
