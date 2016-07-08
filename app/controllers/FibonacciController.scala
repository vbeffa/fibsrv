package controllers

import javax.inject._

import play.api.{Configuration, Logger}
import play.api.mvc._
import services.{FibonacciIndexOutOfBoundsException, FibonacciService, OverUnder}
import util.MissingPropertyException

@Singleton
class FibonacciController @Inject()(fibSrv: FibonacciService, config: Configuration) extends Controller {
  final val NegativeInputMessage = config.getString("fibonacci.negative_input_message").getOrElse(
    throw new MissingPropertyException("fibonacci.negative_input_message"))
  final val MaxFibInputExceededMessage = config.getString("fibonacci.max_fib_input_exceeded_mesage").getOrElse(
    throw new MissingPropertyException("fibonacci.max_fib_input_exceeded_mesage"))
  final val MaxFibListInputExceededMessage = config.getString("fibonacci.max_fib_list_input_exceeded_mesage").getOrElse(
    throw new MissingPropertyException("fibonacci.max_fib_list_input_exceeded_mesage"))

  def index = Action {
    Logger.info("Handling request for index.")
    Ok(views.html.index("Welcome to the Fibonacci Server."))
  }

  def fib(n: Int) = Action {
    Logger.info("Handling request for fib(" + n + ").")
    try {
      val fib = fibSrv.fib(n)
      Logger.info("Returning " + fib + ".")
      Ok(fib.toString)
    } catch {
      case e: FibonacciIndexOutOfBoundsException =>
        e.overUnder match {
          case OverUnder.Under => BadRequest(NegativeInputMessage)
          case OverUnder.Over => Ok(MaxFibInputExceededMessage)
        }
      case _: Throwable => InternalServerError
    }
  }

  def fib_list(n: Int) = Action {
    Logger.info("Handling request for fib_list(" + n + ").")
    try {
      val fibList = fibSrv.fibList(n)
      Logger.info("Returning fib_list(" + n + ").")
      Ok("[" + fibList.mkString(", ") + "]")
    } catch {
      case e: FibonacciIndexOutOfBoundsException =>
        e.overUnder match {
          case OverUnder.Under => BadRequest(NegativeInputMessage)
          case OverUnder.Over => Ok(MaxFibListInputExceededMessage)
        }
      case _: Throwable => InternalServerError
    }
  }

}
