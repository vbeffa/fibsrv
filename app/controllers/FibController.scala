package controllers

import javax.inject._
import play.api._
import play.api.mvc._

import services.FibSrv

@Singleton
class FibController @Inject() (fibSrv: FibSrv) extends Controller {

  def fib(n: Int) = Action {
    Ok("fib(" + n + ") = " + fibSrv.computeFib(n))
  }

  def fib_list(n: Int) = Action {
    var fibList =
      for (i <- 0 to n) yield
        fibSrv.computeFib(i)
    Ok("fib list(" + n + ") = " + fibList)
  }

}
