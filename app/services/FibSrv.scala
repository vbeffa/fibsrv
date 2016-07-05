package services

import java.util.concurrent.atomic.AtomicInteger
import javax.inject._

trait FibSrv {
  def computeFib(n: Int, indent: Int = 0): BigInt
}

@Singleton
class AtomicFibSrv extends FibSrv {
  override def computeFib(n: Int, indent: Int = 0): BigInt = {
    println((" " * indent) + "[" + n + "] computing fib (" + n + ")")
    if (n < 0) throw new IllegalArgumentException("n cannot be negative")

    while (n > fibs.size - 1) {
      fibs += fibs.size -> (fibs(fibs.size - 1) + fibs(fibs.size - 2))
      println(fibs)
    }

    if (!fibs.contains(n)) {
      println((" " * indent) + "[" + n + "] fib (" + n + ") not memoized, calculating...")
      fibs += n -> (computeFib(n - 1, indent + 2) + computeFib(n - 2, indent + 2))
      println((" " * indent) + "[" + n + "] memoized fibs (" + n + ") = " + fibs(n))
      println((" " * indent) + "[" + n + "] size = " + fibs.size)
      println((" " * indent) + "[" + n + "] fibs = " + fibs)
    } else {
      println((" " * indent) + "[" + n + "] fib (" + n + ") already memoized")
    }
    println((" " * indent) + "[" + n + "] returning fib (" + n + ") = " + fibs(n))
    fibs(n)
  }

  var fibs: Map[Int, BigInt] = Map(0 -> 0, 1 -> 1)
}
