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

  var s_pos = 1                     // stream position, 1 = initial '[', 2 = numbers, 3 = final newline, 4 = eos
  var i = 0                         // current number we are streaming
  var token = 1                     // 1 = number, 2 = ',', 3 = ']'
  var bytes: Array[Byte] = Array()  // the bytes in the token when it is a number
  var pos = 0                       // position within bytes

  override def read(): Int = s_pos match {
    case 1 =>
      s_pos = 2
      '['
    case 2 =>
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
            s_pos = 3
            ']'
          } else
            ','
        case 3 =>
          token = 1
          i += 1
          ' '
      }
    case 3 =>
      s_pos = 4
      '\n'
    case 4 =>
      -1
  }
}
