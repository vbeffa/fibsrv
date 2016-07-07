package services

import javax.inject._

import play.api.Configuration

trait FibSrv {
  def fib(n: Int): BigInt
  def fibList(n: Int): List[BigInt]
}

@Singleton
class Fibonacci @Inject() (config: Configuration) extends FibSrv {
  var fibs: Map[Int, BigInt] = Map(0 -> 0, 1 -> 1)
  var fibLists: Map[Int, List[BigInt]] = Map(0 -> List(0), 1 -> List(0, 1))

  override def fib(n: Int) = {
    if (n < 0) throw new FibonacciIndexOutOfBoundsException(Reason.Under)
    if (n > config.getInt("fibonacci.max_fib_input").getOrElse(throw new MissingPropertyException))
      throw new FibonacciIndexOutOfBoundsException(Reason.Over)

    while (n > fibs.size - 1) {
      fibs += fibs.size -> (fibs(fibs.size - 1) + fibs(fibs.size - 2))
    }

    fibs(n)
  }

  override def fibList(n: Int) = {
    if (n < 0) throw new FibonacciIndexOutOfBoundsException(Reason.Under)
    if (n > config.getInt("fibonacci.max_fib_list_input").getOrElse(throw new MissingPropertyException))
      throw new FibonacciIndexOutOfBoundsException(Reason.Over)

    while (n > fibLists.size - 1) {
      fibLists += fibLists.size -> (fibLists(fibLists.size - 1) :+ fib(fibLists.size))
    }

    fibLists(n)
  }

}

object Reason extends Enumeration {
  val Under, Over = Value
}

class FibonacciIndexOutOfBoundsException(val reason: Reason.Value) extends IndexOutOfBoundsException {
}

class MissingPropertyException extends RuntimeException
