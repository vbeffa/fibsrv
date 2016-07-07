package controllers

import javax.inject._

import play.api.mvc._
import services.{FibSrv, FibonacciIndexOutOfBoundsException, Reason}

@Singleton
class FibController @Inject()(fibSrv: FibSrv) extends Controller {

  def index = Action {
    Ok(views.html.fib("Welcome to the Fibonacci Server."))
  }

  def fib(n: Int) = Action {
    try {
      Ok(fibSrv.fib(n).toString)
    } catch {
      case e: FibonacciIndexOutOfBoundsException =>
        e.reason match {
          case Reason.Under => BadRequest("input cannot be negative")
          case Reason.Over => BadRequest("you are quite the eager beaver!")
        }
      case _: Throwable => InternalServerError
    }
  }

  def fib_list(n: Int) = Action {
    try {
      Ok("[" + fibSrv.fibList(n).mkString(", ") + "]")
    } catch {
      case e: FibonacciIndexOutOfBoundsException =>
        e.reason match {
          case Reason.Under => BadRequest("input cannot be negative")
          case Reason.Over => BadRequest("woah there nelly!")
        }
      case _: Throwable => InternalServerError
    }
  }

}
