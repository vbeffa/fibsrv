package services

class FibonacciIndexOutOfBoundsException(val overUnder: OverUnder.Value) extends IndexOutOfBoundsException(
  overUnder.toString)
