package controllers

import javax.inject._

import play.api.mvc._
import services.FibSrv

@Singleton
class FibController @Inject()(fibSrv: FibSrv) extends Controller {

  def index = Action {
    Ok(views.html.fib("Welcome to the Fibonacci Server."))
  }

  def fib(n: Int) = Action {
    try {
      Ok(fibSrv.fib(n).toString)
    } catch {
      case e: IllegalArgumentException => BadRequest("input cannot be negative")
      case _ => InternalServerError
    }
  }

  def fib_list(n: Int) = Action {
    try {
      Ok("[" + fibSrv.fibList(n).mkString(", ") + "]")
    } catch {
      case e: IllegalArgumentException => BadRequest("input cannot be negative")
      case _ => InternalServerError
    }
  }

  def cum_fib_list(n: Int) = Action {
    try {
      Ok("[\n" +
        fibSrv.cumFibList(n).map(inner => "  [" + inner.mkString(", ") + "]").mkString(",\n") + "\n]")
    } catch {
      case e: IllegalArgumentException => BadRequest("input cannot be negative")
      case _ => InternalServerError
    }
  }

}
