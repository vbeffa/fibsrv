package util

import play.api.Logger

object Logging {

  def logMemoryUsage() = {
    val mb = 1024*1024
    val runtime = Runtime.getRuntime

    Logger.info("** Used Memory: " + (runtime.totalMemory - runtime.freeMemory) / mb + " MB")
    Logger.info("** Free Memory:  " + runtime.freeMemory / mb + " MB")
    Logger.info("** Total Memory: " + runtime.totalMemory / mb + " MB")
    Logger.info("** Max Memory:   " + runtime.maxMemory / mb + " MB")
  }

}
