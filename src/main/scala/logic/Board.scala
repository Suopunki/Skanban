package logic

import io.circe._
import io.circe.generic.semiauto._
import scala.collection.mutable.ArrayBuffer

case class Board(
  var title: String = "New Board",
  var columns: ArrayBuffer[Column] = ArrayBuffer(
    new Column("To-do", cards = ArrayBuffer(new Card())),
    new Column("In Progress"),
    new Column("Done")
  ),
  var archive: ArrayBuffer[Card] = ArrayBuffer[Card]()
):

  def setTitle(title: String): Unit =
    this.title = title

  def addColumn(): Column =
    val column = new Column()
    columns += column
    column

  def moveColumnToIndex(column: Column, index: Int): Unit =
    columns -= column
    columns.insert(index, column)

  def removeColumn(column: Column): Unit =
    columns -= column

  def addCard(): Card =
    val card = new Card()
    columns.head.cards += card
    card

  def sortCardsByDeadline(): Unit =
    columns.foreach(_.sortCardsByDeadline())

  def filterCardsByTag(tag: String): Unit =
    columns.foreach(_.filterCardsByTag(tag))

  def removeFilter(): Unit =
    columns.foreach(_.removeFilter())

end Board

// JSON:
implicit val boardEncoder: Encoder[Board] = deriveEncoder
implicit val boardDecoder: Decoder[Board] = deriveDecoder
