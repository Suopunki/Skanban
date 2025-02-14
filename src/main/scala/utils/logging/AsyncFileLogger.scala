package utils.logging

import utils.logging.LogLevel.{Debug, Error, Info, Warn}

import java.time.LocalDateTime
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

class AsyncFileLogger(filePath: String, minLevel: LogLevel = Warn) extends FileLogger(filePath):

  def log(level: LogLevel, message: String): Future[Unit] =
    if shouldLog(level) then
      val formattedMessage = formatMessage(level, message)
      println(formattedMessage)
      Future:
        super.log(message) match
          case Failure(e) => println(s"Failed to log: ${e.getMessage}")
          case Success(_) => // No-op if the level is below the minimum
    else Future.successful(())

  private def shouldLog(level: LogLevel): Boolean =
    val levelOrder = List(Debug, Info, Warn, Error)
    levelOrder.indexOf(level) >= levelOrder.indexOf(minLevel)

  private def formatMessage(level: LogLevel, message: String): String =
    s"${LocalDateTime.now} [$level] - $message"
