package services

import java.time.{Clock, Instant}
import javax.inject._

import play.api._
import play.api.inject._
import util.Logging

import scala.concurrent.Future

@Singleton
class ApplicationTimer @Inject()(clock: Clock,
                                 appLifecycle: ApplicationLifecycle,
                                 app: Application,
                                 fibSrv: FibonacciService,
                                 implicit val config: Configuration) {

  // This code is called when the application starts.
  private val start: Instant = clock.instant
  Logger.info(s"Starting application at $start.")
  Logging.logMemoryUsage()

  // When the application starts, register a stop hook with the
  // ApplicationLifecycle object. The code inside the stop hook will
  // be run when the application stops.
  appLifecycle.addStopHook { () =>
    val stop: Instant = clock.instant
    val runningTime: Long = stop.getEpochSecond - start.getEpochSecond
    Logger.info(s"Stopping application at ${clock.instant} after ${runningTime}s.")
    Future.successful(())
  }

}
