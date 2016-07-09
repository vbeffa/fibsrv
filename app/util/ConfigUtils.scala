package util

import play.api.Configuration

object ConfigUtils {
  def maxFibInput(implicit config: Configuration) = config.getInt("fibonacci.max_fib_input").getOrElse(
    throw new MissingPropertyException("fibonacci.max_fib_input"))

  def maxFibListInput(implicit config: Configuration) = config.getInt("fibonacci.max_fib_list_input").getOrElse(
    throw new MissingPropertyException("fibonacci.max_fib_list_input"))
}
