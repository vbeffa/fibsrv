package services

import java.io.InputStream

/**
  * [[InputStream]] for (potentially unbounded) Fibonacci numbers. Starts with a [[memoizer]] to get its initial
  * values, then once those are exhausted a [[FibGenerator]] is used for subsequent values up to the [[n]]th value.
  *
  * @param n the last Fibonacci number to stream
  * @param memoizer the memoizer used before generating
  */
protected class FibInputStream(val n: Int, val memoizer: FibMemoizer) extends InputStream {
  if (n < 0) throw new IndexOutOfBoundsException

  val generator = new FibGenerator(memoizer)

  var i = 0
  var token = 1                     // 1 = ith number, 2 = ',', 3 = ']'
  var bytes: Array[Byte] = Array()  // the bytes in the token when it is a number
  var pos = 0                       // position within bytes
  var start = true                  // for initial '['
  var newline = false               // for final newline
  var end = false

  override def read(): Int = {
    if (start) {
      start = false
      return '['
    }
    if (end) return -1
    if (newline) {
      end = true
      return '\n'
    }

    token match {
      case 1 =>
        if (pos == 0)
          bytes = (if (i < memoizer.upTo) memoizer.fib(i) else generator.next).toString.getBytes

        if (pos < bytes.length - 1) {
          pos += 1
          bytes(pos - 1)
        } else {
          token = 2
          pos = 0
          bytes(bytes.length - 1)
        }
      case 2 =>
        token = 3
        if (i == n) {
          newline = true
          ']'
        }
        else ','
      case 3 =>
        token = 1
        i += 1
        ' '
    }
  }
}
