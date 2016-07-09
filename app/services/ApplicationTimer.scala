package services

import java.time.{Clock, Instant}
import javax.inject._

import play.api._
import play.api.inject._
import util.{ConfigUtils, LoggingUtils}

import scala.concurrent.Future

/**
 * This class demonstrates how to run code when the
 * application starts and stops. It starts a timer when the
 * application starts. When the application stops it prints out how
 * long the application was running for.
 *
 * This class is registered for Guice dependency injection in the
 * [[Module]] class. We want the class to start when the application
 * starts, so it is registered as an "eager singleton". See the code
 * in the [[Module]] class to see how this happens.
 *
 * This class needs to run code when the server stops. It uses the
 * application's [[ApplicationLifecycle]] to register a stop hook.
 */
@Singleton
class ApplicationTimer @Inject() (clock: Clock,
                                  appLifecycle: ApplicationLifecycle,
                                  app: Application,
                                  fibSrv: FibonacciService,
                                  implicit val config: Configuration) {

  // This code is called when the application starts.
  private val start: Instant = clock.instant
  Logger.info(s"Starting application at $start.")
  if (app.mode == Mode.Dev) {
    LoggingUtils.logMemoryUsage()
    fibSrv.memoize(ConfigUtils.maxFibInput, ConfigUtils.maxFibListInput)
    LoggingUtils.logMemoryUsage()
  }

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
