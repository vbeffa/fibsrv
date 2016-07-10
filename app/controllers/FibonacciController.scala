package controllers

import javax.inject._

import akka.stream.scaladsl.Source
import play.api.libs.iteratee.Enumerator
import play.api.libs.streams.Streams
import play.api.mvc._
import play.api.{Configuration, Logger}
import services.FibonacciService
import util.{ConfigUtils, MissingPropertyException}

import scala.concurrent.ExecutionContext

@Singleton
class FibonacciController @Inject()(fibSrv: FibonacciService,
                                    implicit val config: Configuration,
                                    implicit val ec: ExecutionContext) extends Controller {
  final val NegativeInputMessage = config.getString("fibonacci.negative_input_message").getOrElse(
    throw new MissingPropertyException("fibonacci.negative_input_message"))
  final val MaxFibInputExceededMessage = config.getString("fibonacci.max_fib_input_exceeded_mesage").getOrElse(
    throw new MissingPropertyException("fibonacci.max_fib_input_exceeded_mesage"))
  final val MaxFibListInputExceededMessage = config.getString("fibonacci.max_fib_list_input_exceeded_mesage").getOrElse(
    throw new MissingPropertyException("fibonacci.max_fib_list_input_exceeded_mesage"))

  def index = Action {
    Logger.info("Handling request for index.")
    Ok(views.html.index("Welcome to the Fibonacci Server.", ConfigUtils.maxFibInput, ConfigUtils.maxFibListInput))
  }

  def fib(n: Int) = Action {
    Logger.info("Handling request for fib(" + n + ").")

    if (n > ConfigUtils.maxFibInput) {
      Logger.info("Request exceeded max input " + ConfigUtils.maxFibInput)
      Ok(MaxFibInputExceededMessage)
    } else {
      try {
        val fib = fibSrv.fib(n)
        Logger.info("Returning " + fib + ".")
        Ok(fib.toString)
      } catch {
        case e: IndexOutOfBoundsException => BadRequest(NegativeInputMessage)
        case _: Throwable => InternalServerError
      }
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
}
