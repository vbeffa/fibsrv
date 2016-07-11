package services

class FibGenerator(memoizer: FibMemoizer) {
  var i1 = memoizer.fib(memoizer.upTo - 2)
  var i2 = memoizer.fib(memoizer.upTo - 1)

  def next = {
    val i3 = i1 + i2
    i1 = i2
    i2 = i3
    i3
  }
}
