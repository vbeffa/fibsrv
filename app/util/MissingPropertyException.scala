package util

class MissingPropertyException(val prop: String) extends RuntimeException(prop)
