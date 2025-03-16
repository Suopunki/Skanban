package model

import io.circe.syntax.EncoderOps
import io.circe.{Decoder, Encoder, HCursor, Json}
import scalafx.beans.property.StringProperty
import scalafx.collections.ObservableBuffer
import utils.json.scalafx.ObservableBufferCodec.*
import utils.json.scalafx.StringPropertyCodec.*

class Column(
    val title: StringProperty = StringProperty("New Column"),
    val cards: ObservableBuffer[Card] = ObservableBuffer[Card]()
):
  def addCard(card: Card = Card()): Unit =
    cards += card

  def moveCardToIndex(card: Card, index: Int): Unit =
    cards.remove(card)
    cards.insert(index, card)

  def removeCard(card: Card): Unit =
    cards.remove(card)

  def sortCardsByStartDate(): Unit =
    val (withStartDates, withoutStartDates) = cards.partition(_.startDate.value.nonEmpty)
    val sortedWithStartDates = withStartDates.sortBy(_.startDate.get)
    cards.clear()
    cards ++= (sortedWithStartDates ++ withoutStartDates)

  def sortCardsByEndDate(): Unit =
    val (withEndDates, withoutEndDates) = cards.partition(_.endDate.value.nonEmpty)
    val sortedWithEndDates = withEndDates.sortBy(_.endDate.get)
    cards.clear()
    cards ++= (sortedWithEndDates ++ withoutEndDates)

  def filterCardsByTag(tag: String): ObservableBuffer[Card] =
    cards.filter(_.tag.value.exists(_ == tag))

object Column:
  implicit val columnEncoder: Encoder[Column] = Encoder.instance((column: Column) =>
    Json.obj(
      "title" -> column.title.asJson,
      "cards" -> column.cards.asJson
    )
  )

  implicit val columnDecoder: Decoder[Column] = Decoder.instance((cursor: HCursor) =>
    for
      title <- cursor.downField("title").as[StringProperty]
      cards <- cursor.downField("cards").as[ObservableBuffer[Card]]
    yield Column(title, cards)
  )
