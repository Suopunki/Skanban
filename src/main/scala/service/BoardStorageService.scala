package service

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.parser.decode
import io.circe.syntax.EncoderOps
import io.circe.{Decoder, Encoder, Error}
import model.{Board, Card, Column, Task}

import java.io.{File, FileNotFoundException, IOException}
import java.nio.file.{Files, Path, Paths, StandardOpenOption}
import scala.util.{Failure, Success, Try, Using}

object BoardStorageService:
  /** Save the board to a file in JSON format.
    * @param board the board to save
    * @param file the file where the board should be saved
    * @return Success if saving is successful, Failure otherwise
    */
  def save(board: Board, file: File): Try[Unit] =
    Using(
      Files.newBufferedWriter(
        fileToPath(file),
        StandardOpenOption.CREATE,
        StandardOpenOption.TRUNCATE_EXISTING
      )
    ) { writer =>
      writer.write(board.asJson.toString)
    }.recover { case e: IOException =>
      println(s"Error writing save data to file: $e")
    }

  private def fileToPath(file: File): Path =
    Paths.get(
      if file.getName.endsWith(".skanban") then file.getPath
      else file.getPath + ".skanban"
    )

  /** Load a board from a JSON file.
    * @param file the file to load the board from
    * @return Success(Board) if loading is successful, Failure otherwise
    */
  def load(file: File): Try[Board] =
    Using(Files.newBufferedReader(Paths.get(file.getPath))) { reader =>
      decode[Board](reader.lines().toArray.mkString)
    }.flatMap {
      case Right(board) => Success(board)
      case Left(error)  => Failure(new Exception(s"Error decoding save file: $error"))
    }.recover {
      case e: FileNotFoundException => println(s"File not found: $e"); throw e
      case e: IOException           => println(s"Error reading file: $e"); throw e
    }
