package unit.properties

import org.scalacheck.Prop._
import org.scalacheck.{Gen, Properties}
import services.{FibGenerator, FibMemoizer}

object FibGeneratorSpecification extends Properties("FibGenerator") {

  property("first generation") = forAll(Gen.choose(2, 10000)) { (n: Int) =>
    val memoizer = new FibMemoizer(n)
    val generator = new FibGenerator(memoizer)
    generator.next == memoizer.fib(memoizer.upTo - 2) + memoizer.fib(memoizer.upTo - 1)
  }

  property("additional generations") = forAll(Gen.choose(2, 10000)) { (n: Int) =>
    val memoizer = new FibMemoizer(10000)
    val generator = new FibGenerator(memoizer)
    1 to n - 2 foreach { _ =>
      generator.next
    }
    generator.next + generator.next == generator.next
  }

}
