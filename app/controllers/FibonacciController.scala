package controllers

import javax.inject._

import akka.stream.scaladsl.Source
import play.api.libs.iteratee.Enumerator
import play.api.libs.streams.Streams
import play.api.mvc._
import play.api.{Configuration, Logger}
import services.FibonacciService
import util.{Config, MissingPropertyException}

import scala.concurrent.ExecutionContext

@Singleton
class FibonacciController @Inject()(fibSrv: FibonacciService,
                                    implicit val config: Configuration,
                                    implicit val ec: ExecutionContext) extends Controller {
  final val NegativeInputMessage = Config.getString("fibonacci.negative_input_message")
  final val MaxFibInputExceededMessage = Config.getString("fibonacci.browser.max_fib_input_exceeded_message")
  final val MaxFibListInputExceededMessage = Config.getString("fibonacci.browser.max_fib_list_input_exceeded_message")

  def index = Action {
    Logger.info("Handling request for index.")
    Ok(views.html.index("Welcome to the Fibonacci Server.", Config.maxFibInput, Config.maxFibListInput))
  }

  def fib(n: Int) = Action {
    Logger.info("Handling request for fib(" + n + ").")

    try {
      val fib = fibSrv.fib(n)
      Logger.info("Returning " + fib + ".")
      Ok(fib.toString)
    } catch {
      case e: IndexOutOfBoundsException => BadRequest(NegativeInputMessage)
      case _: Throwable => InternalServerError
    }
  }

  def fibList(n: Int) = Action {
    Logger.info("Handling request for fib_list(" + n + ").")

    try {
      val data = fibSrv.fibList(n)
      val dataContent: Enumerator[Array[Byte]] = Enumerator.fromStream(data)
      val source = Source.fromPublisher(Streams.enumeratorToPublisher(dataContent))

      Ok.chunked(source).as("text/plain")
    } catch {
      case e: IndexOutOfBoundsException => BadRequest(NegativeInputMessage)
      case _: Throwable => InternalServerError
    }
  }

  def browserFib(n: Int) =
    if (n > Config.maxFibInput)
      Action {
        Logger.info("Request exceeded max input " + Config.maxFibInput)
        Ok(MaxFibInputExceededMessage)
      }
    else
      fib(n)

  def browserFibList(n: Int) =
    if (n > Config.maxFibListInput)
      Action {
        Logger.info("Request exceeded max input " + Config.maxFibListInput)
        Ok(MaxFibListInputExceededMessage)
      }
    else
      fibList(n)
}
