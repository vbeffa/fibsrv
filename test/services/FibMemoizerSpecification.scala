package services

import org.scalacheck.Prop.forAll
import org.scalacheck.{Gen, Properties}

object FibMemoizerSpecification extends Properties("FibMemoizer") {

  property("size") = forAll(Gen.choose(2, 10000)) { (n: Int) =>
    val memoizer = new FibMemoizer(n)
    memoizer.fibs.size == n + 1
  }

  property("fib(n)") = forAll(Gen.choose(2, 10000)) { (n: Int) =>
    val memoizer = new FibMemoizer(10000)
    memoizer.fib(n) == memoizer.fib(n - 1) + memoizer.fib(n - 2)
  }

}
