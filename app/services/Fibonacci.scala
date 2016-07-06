package services

import javax.inject._

trait FibSrv {
  def fib(n: Int): BigInt
  def fibList(n: Int): List[BigInt]
  def cumFibList(n: Int): List[List[BigInt]]
}

@Singleton
class Fibonacci extends FibSrv {
  var fibs: Map[Int, BigInt] = Map(0 -> 0, 1 -> 1)
  var fibLists: Map[Int, List[BigInt]] = Map(0 -> List(0), 1 -> List(0, 1))
  var cumFibLists: Map[Int, List[List[BigInt]]] = Map(0 -> List(List(0)), 1 -> List(List(0), List(0, 1)))

  override def fib(n: Int) = {
    if (n < 0) throw new IllegalArgumentException("n cannot be negative")

    while (n > fibs.size - 1) {
      fibs += fibs.size -> (fibs(fibs.size - 1) + fibs(fibs.size - 2))
    }

    fibs(n)
  }

  override def fibList(n: Int) = {
    if (n < 0) throw new IllegalArgumentException("n cannot be negative")

    while (n > fibLists.size - 1) {
      fibLists += fibLists.size -> (fibLists(fibLists.size - 1) :+ fib(fibLists.size))
    }

    fibLists(n)
  }

  override def cumFibList(n: Int) = {
    if (n < 0) throw new IllegalArgumentException("n cannot be negative")

    while (n > cumFibLists.size - 1) {
      cumFibLists += cumFibLists.size -> (cumFibLists(cumFibLists.size - 1) :+ fibList(cumFibLists.size))
    }

    cumFibLists(n)
  }
}
