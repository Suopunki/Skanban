package utils.logging

import java.io.{BufferedWriter, FileWriter}
import scala.util.{Try, Using}

class FileLogger(filePath: String):

  def log(message: String): Try[Unit] =
    Using(BufferedWriter(FileWriter(filePath, true)))(writer => writer.write(message + '\n'))
