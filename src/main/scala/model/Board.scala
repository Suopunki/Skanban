package model

import io.circe.syntax.EncoderOps
import io.circe.{Decoder, Encoder, HCursor, Json}
import scalafx.beans.property.StringProperty
import scalafx.collections.ObservableBuffer
import utils.json.scalafx.ObservableBufferCodec.*
import utils.json.scalafx.StringPropertyCodec.*

case class Board(
    title: StringProperty = StringProperty("New Board"),
    columns: ObservableBuffer[Column] = ObservableBuffer(
      Column(StringProperty("To do"), ObservableBuffer(Card())),
      Column(StringProperty("In Progress")),
      Column(StringProperty("Done"))
    ),
    archive: ObservableBuffer[Card] = ObservableBuffer()
):
  def addColumn(title: String = "New Column"): Unit =
    columns += Column(StringProperty(title))

  def moveColumnToIndex(column: Column, index: Int): Unit =
    columns.remove(column)
    columns.insert(index, column)

  def removeColumn(column: Column): Unit =
    columns.remove(column)

  def addCardToColumn(card: Card = Card(), columnIndex: Int = 0): Unit =
    if columns.isDefinedAt(columnIndex) then columns(columnIndex).addCard(card)

  def sortCardsByEndDate(): Unit =
    columns.foreach(_.sortCardsByEndDate())

  def filterCardsByTag(tag: String): Unit =
    columns.foreach(_.filterCardsByTag(tag))

object Board:
  implicit val boardEncoder: Encoder[Board] = Encoder.instance((board: Board) =>
    Json.obj(
      "title" -> board.title.asJson,
      "columns" -> board.columns.asJson,
      "archive" -> board.archive.asJson
    )
  )

  implicit val boardDecoder: Decoder[Board] = Decoder.instance((cursor: HCursor) =>
    for
      title <- cursor.downField("title").as[StringProperty]
      columns <- cursor.downField("columns").as[ObservableBuffer[Column]]
      archive <- cursor.downField("archive").as[ObservableBuffer[Card]]
    yield Board(title, columns, archive)
  )
