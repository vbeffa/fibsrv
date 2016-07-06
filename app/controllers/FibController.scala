package controllers

import javax.inject._

import play.api.mvc._
import services.FibSrv

@Singleton
class FibController @Inject() (fibSrv: FibSrv) extends Controller {

  def fib(n: Int) = Action {
    Ok("fibonacci(" + n + ") = " + fibSrv.fib(n))
  }

  def fib_list(n: Int) = Action {
    Ok("fibonacci(" + n + ") list = [" + fibSrv.fibList(n).mkString(", ") + "]")
  }

  def cum_fib_list(n: Int) = Action {
    Ok("cumulative fibonacci(" + n + ") lists = [\n" +
      fibSrv.cumFibList(n).map(inner => "  [" + inner.mkString(", ") + "]").mkString(",\n") + "\n]")
  }

}
