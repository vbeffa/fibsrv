package services

import java.io.InputStream
import javax.inject._

import play.api.Configuration
import util.Config

trait FibonacciService {
  def fib(n: Int): BigInt

  def fibList(n: Int): InputStream
}

@Singleton
class FibonacciServiceImpl @Inject()(implicit config: Configuration) extends FibonacciService {
  val memoizer = new FibMemoizer(Config.memoizeUpTo)
  var fibLists: Map[Int, List[BigInt]] = Map(0 -> List(0), 1 -> List(0, 1))

  override def fib(n: Int) = {
    if (n < 0) throw new IndexOutOfBoundsException

    if (n < memoizer.upTo)
      memoizer.fib(n)
    else {
      val generator = new FibGenerator(memoizer)
      memoizer.upTo until n foreach { _ =>
        generator.next
      }
      generator.next
    }
  }

  override def fibList(n: Int) = new FibInputStream(n, memoizer)
}
