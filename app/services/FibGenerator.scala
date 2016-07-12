package services

/**
  * Generator for Fibonacci numbers. Uses a memoizer to get its start value from: the first call to [[next]]
  * will return the sum of the memoizer's last two values, and subsequent calls will return subsequent values.
  * No previous values are kept beyond n - 1 and n - 2, so the memory requirements for the generator are minimized.
  *
  * @param memoizer the memoizer used to start generating
  */
protected class FibGenerator(memoizer: FibMemoizer) {
  var i1 = memoizer.fib(memoizer.upTo - 2)
  var i2 = memoizer.fib(memoizer.upTo - 1)

  def next = {
    val i3 = i1 + i2
    i1 = i2
    i2 = i3
    i3
  }
}
