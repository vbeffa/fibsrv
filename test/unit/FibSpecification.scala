package unit

import org.scalacheck.Prop.forAll
import org.scalacheck.{Gen, Properties}
import play.api.Configuration
import services.FibonacciServiceImpl

object FibSpecification extends Properties("Fibonacci") {
  val config = Configuration(
    "fibonacci.max_fib_input" -> Math.pow(2, 16).toInt,
    "fibonacci.max_fib_input" -> Math.pow(2, 12).toInt)
  val fibonacci: FibonacciServiceImpl = new FibonacciServiceImpl(config)

  property("fibonacci") = forAll(Gen.choose(2, config.getInt("fibonacci.max_fib_input").get)) { (n: Int) =>
    fibonacci.fib(n) == fibonacci.fib(n - 1) + fibonacci.fib(n - 2)
  }

}
