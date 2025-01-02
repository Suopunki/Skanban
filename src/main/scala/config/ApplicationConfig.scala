package config

case class ApplicationConfig(title: String, width: Int, height: Int)

object ApplicationConfig:
  val default = ApplicationConfig(
    title = "Skanban",
    width = 1024,
    height = 768
  )
