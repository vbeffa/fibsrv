package unit.properties

import org.scalacheck.Prop.forAll
import org.scalacheck.{Gen, Properties}
import play.api.Configuration
import services.FibonacciServiceImpl

object FibonacciServiceSpecification extends Properties("FibonacciService") {
  implicit val config = Configuration(
    "fibonacci.memoize_up_to" -> Math.pow(2, 16).toInt,
    "fibonacci.browser.max_fib_list_input" -> Math.pow(2, 12).toInt)
  val fibSrv = new FibonacciServiceImpl

  property("fibonacci") = forAll(Gen.choose(2, config.getInt("fibonacci.memoize_up_to").get)) { (n: Int) =>
    fibSrv.fib(n) == fibSrv.fib(n - 1) + fibSrv.fib(n - 2)
  }

}
