package services

import play.api.Logger
import util.Logging

/**
  * Memoizer for Fibonacci numbers. Memoizes up to [[upTo]].
  *
  * @param upTo the last Fibonacci number to memoize
  */
protected class FibMemoizer(val upTo: Int) {
  var fibs: Map[Int, BigInt] = Map(0 -> 0, 1 -> 1)

  if (upTo < 2) throw new IllegalArgumentException("Must memoize up to at least n = 2.")

  Logger.info("Memoizing up to fib(" + upTo + ").")

  while (upTo > fibs.size - 1) {
    fibs += fibs.size -> (fibs(fibs.size - 1) + fibs(fibs.size - 2))
  }
  Logging.logMemoryUsage()

  def fib(n: Int) = fibs(n)
}
