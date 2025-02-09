package logic

import io.circe._
import io.circe.parser._
import io.circe.syntax._
import java.io.{
  BufferedReader,
  File,
  FileNotFoundException,
  FileReader,
  FileWriter,
  IOException
}

object App:

  def save(board: Board, file: File): Unit =
    val saveFilePath =
      if file.getName.contains(".json") then file.getPath
      else file.getPath + ".json"
    val saveFile = File(saveFilePath)

    try
      val fileCreated = saveFile.createNewFile()
      if !fileCreated then
        saveFile.delete()
        saveFile.createNewFile()
    catch case e: IOException => println("Error creating save file:\n" + e)

    val writer = new FileWriter(saveFile)
    try writer.write(board.asJson.toString)
    catch
      case e: IOException => println("Error writing save data to file:\n" + e)
    finally writer.close()

  def load(file: File): Board =
    val stringBuilder = new StringBuilder
    val reader = new BufferedReader(new FileReader(new File(file.getPath)))

    try
      var line = reader.readLine()
      while line != null do
        stringBuilder ++= line
        line = reader.readLine()
    catch
      case e: FileNotFoundException => println("Error finding file:\n" + e)
      case e: IOException           => println("Error reading file:\n" + e)
    finally reader.close()

    val jsonString = stringBuilder.toString
    val decoded: Either[Error, Board] = decode(jsonString)
    decoded match
      case Left(e: Error) => { println("Error decoding file"); throw e }
      case Right(board)   => board

end App
