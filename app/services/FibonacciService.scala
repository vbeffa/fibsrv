package services

import java.io.{BufferedWriter, File, FileWriter, InputStream}
import java.util.UUID
import javax.inject._

import play.api.{Configuration, Logger}

trait FibonacciService {
  def memoize(maxFibInput: Int): Unit

  def fib(n: Int): BigInt

  def fibList(n: Int): InputStream
}

@Singleton
class FibonacciServiceImpl @Inject()(config: Configuration) extends FibonacciService {
  var fibs: Map[Int, BigInt] = Map(0 -> 0, 1 -> 1)
  var fibLists: Map[Int, List[BigInt]] = Map(0 -> List(0), 1 -> List(0, 1))

  override def memoize(maxFibInput: Int) = {
    Logger.info("Memoizing up to fib(" + maxFibInput + ").")
    fib(maxFibInput)
  }

  override def fib(n: Int) = {
    if (n < 0) throw new IndexOutOfBoundsException

    Logger.debug("Getting fib(" + n + "). fibs size = " + fibs.size)
    while (n > fibs.size - 1) {
      if (fibs.size % 1000 == 0) {
        Logger.debug("Memoized fib(" + fibs.size + ").")
      }
      fibs += fibs.size -> (fibs(fibs.size - 1) + fibs(fibs.size - 2))
    }

    fibs(n)
  }

  override def fibList(n: Int) = {
    if (n < 0) throw new IndexOutOfBoundsException

    new FibInputStream(n, this)
  }
}
