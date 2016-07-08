package controllers

import javax.inject._

import play.api.Logger
import play.api.mvc._
import services.{FibonacciIndexOutOfBoundsException, FibonacciService, OverUnder}

@Singleton
class FibonacciController @Inject()(fibSrv: FibonacciService) extends Controller {

  def index = Action {
    Logger.info("Handling request for index")
    Ok(views.html.index("Welcome to the Fibonacci Server."))
  }

  def fib(n: Int) = Action {
    Logger.info("Handling request for fib(" + n + ")")
    try {
      Ok(fibSrv.fib(n).toString)
    } catch {
      case e: FibonacciIndexOutOfBoundsException =>
        e.overUnder match {
          case OverUnder.Under => BadRequest("input cannot be negative")
          case OverUnder.Over => Ok("you are quite the eager beaver!")
        }
      case _: Throwable => InternalServerError
    }
  }

  def fib_list(n: Int) = Action {
    Logger.info("Handling request for fib_list(" + n + ")")
    try {
      Ok("[" + fibSrv.fibList(n).mkString(", ") + "]")
    } catch {
      case e: FibonacciIndexOutOfBoundsException =>
        e.overUnder match {
          case OverUnder.Under => BadRequest("input cannot be negative")
          case OverUnder.Over => Ok("woah there nelly!")
        }
      case _: Throwable => InternalServerError
    }
  }

}
