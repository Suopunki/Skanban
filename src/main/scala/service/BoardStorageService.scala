package service

import java.io.{File, FileNotFoundException, IOException}
import java.nio.file.{Files, Path, Paths, StandardOpenOption}

import io.circe.generic.semiauto.{deriveCodec, deriveDecoder, deriveEncoder}
import io.circe.parser.decode
import io.circe.syntax.EncoderOps
import io.circe.{Decoder, Encoder, Error}
import scala.util.{Failure, Success, Try, Using}

import model.{Board, Card, Column, Task}

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
      writer.write(board.asJson.noSpaces)
    }.recover { case e: IOException =>
      println(s"Error writing save data to file: $e")
    }

  private def fileToPath(file: File): Path =
    Paths.get(if file.getName.endsWith(".json") then file.getPath else file.getPath + ".json")

  /** Load a board from a JSON file.
    * @param file the file to load the board from
    * @return Success(Board) if loading is successful, Failure otherwise
    */
  def load(file: File): Try[Board] =
    Using(Files.newBufferedReader(Paths.get(file.getPath))) { reader =>
      decode[Board](reader.lines().toArray.mkString)
    }.flatMap {
      case Right(board) => Success(board)
      case Left(error)  => Failure(new Exception(s"Error decoding JSON: $error"))
    }.recover {
      case e: FileNotFoundException => println(s"File not found: $e"); throw e
      case e: IOException           => println(s"Error reading file: $e"); throw e
    }

  // JSON codecs for the models
  private given Encoder[Board] = deriveEncoder
  private given Decoder[Board] = deriveDecoder
  private given Encoder[Column] = deriveEncoder
  private given Decoder[Column] = deriveDecoder
  private given Encoder[Card] = deriveEncoder
  private given Decoder[Card] = deriveDecoder
  private given Encoder[Task] = deriveEncoder
  private given Decoder[Task] = deriveDecoder
