package services

import java.io.InputStream

import play.api.Configuration

class FibInputStream(val n: Int, val fibSrv: FibonacciService) extends InputStream {
  var i = 0
  var token = 1
  var pos = 0
  var start = true
  var newline = false
  var end = false
  var bytes: Array[Byte] = Array()

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
        if (pos == 0) {
          bytes = fibSrv.fib(i).toString.getBytes
        }
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
