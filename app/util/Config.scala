package util

import play.api.Configuration

object Config {
  def memoizeUpTo(implicit config: Configuration) = getInt("fibonacci.memoize_up_to")

  def maxFibInput(implicit config: Configuration) = getInt("fibonacci.browser.max_fib_input")

  def maxFibListInput(implicit config: Configuration) = getInt("fibonacci.browser.max_fib_list_input")

  def getString(prop: String)(implicit config: Configuration) = config.getString(prop).getOrElse(
    throw new MissingPropertyException(prop))

  def getInt(prop: String)(implicit config: Configuration) = config.getInt(prop).getOrElse(
    throw new MissingPropertyException(prop))
}
