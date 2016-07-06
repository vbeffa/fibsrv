package controllers

import javax.inject._

import play.api.http.{ContentTypeOf, Writeable}
import play.api.mvc._
import play.mvc.Http
import services.FibSrv

@Singleton
class FibController @Inject() (fibSrv: FibSrv) extends Controller {

  def index = Action {
    Ok(views.html.fib("Welcome to the Fibonacci Server."))
  }

  def fib(n: Int) = Action {
    Ok(fibSrv.fib(n).toString)
  }

  def fib_list(n: Int) = Action {
    Ok("[" + fibSrv.fibList(n).mkString(", ") + "]")
  }

  def cum_fib_list(n: Int) = Action {
    Ok("[\n" +
      fibSrv.cumFibList(n).map(inner => "  [" + inner.mkString(", ") + "]").mkString(",\n") + "\n]")
  }

}
