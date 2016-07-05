package controllers

import javax.inject._
import play.api._
import play.api.mvc._

import services.Counter

@Singleton
class FibController @Inject() () extends Controller {

  def fib(n: Int) = Action {
    Ok("fib(" + n + ") = " + compute_fib(n))
  }

  def fib_list(n: Int) = Action {
    var fib_list =
      for (i <- 0 to n) yield
        compute_fib(i)
    Ok("fib list(" + n + ") = " + fib_list)
  }

  def compute_fib(n: Int): Long = n match {
    case 0 => 0
    case 1 => 1
    case x if x > 0 => compute_fib(x - 1) + compute_fib(x - 2)
  }

}
