package services

import javax.inject._

import play.api.{Configuration, Logger}
import util.MissingPropertyException

trait FibonacciService {
  def memoize(): Unit
  def fib(n: Int): BigInt
  def fibList(n: Int): List[BigInt]
}

@Singleton
class FibonacciServiceImpl @Inject()(config: Configuration) extends FibonacciService {
  var fibs: Map[Int, BigInt] = Map(0 -> 0, 1 -> 1)
  var fibLists: Map[Int, List[BigInt]] = Map(0 -> List(0), 1 -> List(0, 1))

  override def memoize() = {
    Logger.info("Memoizing up to fib(" + maxFibInput + ") and fib_list(" + maxFibListInput + ")")
    fib(maxFibInput)
    fibList(maxFibListInput)
  }

  override def fib(n: Int) = {
    if (n < 0) throw new FibonacciIndexOutOfBoundsException(OverUnder.Under)
    if (n > maxFibInput) throw new FibonacciIndexOutOfBoundsException(OverUnder.Over)

    Logger.debug("size = " + fibs.size)
    while (n > fibs.size - 1) {
      if (fibs.size % 1000 == 0) {
        Logger.debug("Memoized fib(" + fibs.size + ")")
      }
      fibs += fibs.size -> (fibs(fibs.size - 1) + fibs(fibs.size - 2))
    }

    fibs(n)
  }

  override def fibList(n: Int) = {
    if (n < 0) throw new FibonacciIndexOutOfBoundsException(OverUnder.Under)
    if (n > maxFibListInput) throw new FibonacciIndexOutOfBoundsException(OverUnder.Over)

    Logger.debug("size = " + fibLists.size)
    while (n > fibLists.size - 1) {
      if (fibs.size % 1000 == 0) {
        Logger.debug("Memoized fib_list(" + fibs.size + ")")
      }
      fibLists += fibLists.size -> (fibLists(fibLists.size - 1) :+ fib(fibLists.size))
    }

    fibLists(n)
  }

  private def maxFibInput = config.getInt("fibonacci.max_fib_input").getOrElse(
    throw new MissingPropertyException("fibonacci.max_fib_input"))
  private def maxFibListInput = config.getInt("fibonacci.max_fib_list_input").getOrElse(
    throw new MissingPropertyException("fibonacci.max_fib_list_input"))
}
