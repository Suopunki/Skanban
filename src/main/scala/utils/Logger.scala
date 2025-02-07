package utils

import java.io.{BufferedWriter, FileWriter, PrintWriter, StringWriter}
import java.nio.file.{Files, Path, Paths}
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import scala.util.{Failure, Success, Try, Using}

object Logger:
  private val logDirectoryPath = "./logs/"
  private val errorLogPath = logDirectoryPath + "error.log"
  private val infoLogPath = logDirectoryPath + "info.log"
  private val debugLogPath = logDirectoryPath + "debug.log"
  private val warnLogPath = logDirectoryPath + "warn.log"
  private val timeStampFormat = "yyyy/MM/dd HH:mm:ss"

  enum LogLevel:
    case ERROR, INFO, DEBUG, WARN

  def log(logLevel: LogLevel, message: String, printToConsole: Boolean = false): Unit =
    val filePathString = logFilePathString(logLevel)
    val filePath = logFilePath(logLevel)
    val logMessage = formatMessage(logLevel, message)
    ensureLogFileExists(logLevel) match
      case Success(_) => writeLog(filePath.toString, message)
      case Failure(e) => println(s"Failed to create log file: ${e.getMessage}")
    if printToConsole then println(logMessage)

  private def ensureLogFileExists(logLevel: LogLevel): Try[Unit] =
    Try:
      val logFile = logFilePath(logLevel)
      if !Files.exists(logFile) then Files.createDirectories(logFile)

  private def logFilePathString(logLevel: LogLevel): String =
    logLevel match
      case LogLevel.ERROR => errorLogPath
      case LogLevel.INFO  => infoLogPath
      case LogLevel.DEBUG => debugLogPath
      case LogLevel.WARN  => warnLogPath

  private def logFilePath(logLevel: LogLevel): Path =
    Paths.get(logFilePathString(logLevel))

  private def formatMessage(logLevel: LogLevel, message: String): String =
    s"[${createTimeStamp()}] ${logLevel}: $message"

  private def createTimeStamp(): String =
    LocalDateTime.now().format(DateTimeFormatter.ofPattern(timeStampFormat))

  def logExeption(e: Exception): Unit =
    ensureLogFileExists(LogLevel.ERROR) match
      case Success(_) => // error.log file already exists or was created successfully
      case Failure(e) =>
        println(s"Failed to create log file: ${e.getMessage}")
        return
    val timeStamp = createTimeStamp()
    val title = s"[$timeStamp] EXCEPTION: ${e.getMessage}\n"
    val stackTrace = s"Stack Trace: ${getStackTraceString(e)}"
    val logMessage = title + stackTrace
    println(logMessage)
    writeLog(logMessage)

  private def writeLog(filePath: String, message: String): Unit =
    Using(BufferedWriter(FileWriter(filePath, true)))(writer =>
      writer.write(message)
      writer.newLine()
    ) match
      case Success(_) => // Log written successfully
      case Failure(e) => println(s"Failed to write log: ${e.getMessage}")

  private def getStackTraceString(e: Throwable): String =
    val stringWriter = StringWriter()
    e.printStackTrace(PrintWriter(stringWriter))
    stringWriter.toString
