package logic

import io.circe._
import io.circe.generic.semiauto._
import scala.collection.mutable.ArrayBuffer

case class Column(
  var title: String = "new Column",
  var cards: ArrayBuffer[Card] = ArrayBuffer[Card]()
):

  private var unfilteredCards = cards

  def setTitle(title: String): Unit =
    this.title = title

  def addCard(): Card =
    val card = new Card()
    cards += card
    unfilteredCards = cards
    card

  def moveCardToIndex(card: Card, index: Int): Unit =
    cards -= card
    cards.insert(index, card)
    unfilteredCards = cards

  def removeCard(card: Card): Unit =
    cards -= card
    unfilteredCards = cards

  def sortCardsByDeadline(): Unit =
    val (withDeadlines, withoutDeadlines) = cards.partition(_.deadline.nonEmpty)
    val sortedWithDeadlines = withDeadlines.sortBy(_.deadline.get)
    cards = sortedWithDeadlines ++ withoutDeadlines
    unfilteredCards = cards

  def filterCardsByTag(tag: String): Unit =
    removeFilter()
    unfilteredCards = cards
    cards = cards.filter(_.tag.nonEmpty).filter(_.tag.get == tag)

  def removeFilter(): Unit =
    cards = unfilteredCards

end Column

// JSON:
implicit val columnEncoder: Encoder[Column] = deriveEncoder
implicit val columnDecoder: Decoder[Column] = deriveDecoder
