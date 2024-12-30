import java.time.LocalDate
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import logic._

class ColumnTest extends AnyFlatSpec with Matchers:

  "Column" should "move a card to another position when using moveCardToIndex" in:
    val column = new Column()
    for i <- 0 to 9 do
      val card = new Card(i.toString)
      column.cards += card
    val cardTwo = column.cards(2)
    column.moveCardToIndex(cardTwo, 7)
    column.cards(7).title shouldEqual "2"

  it should "sort cards by deadline when using sortCardsByDeadline" in:
    val column = new Column()
    for i <- 1 to 9 do
      val card = new Card(i.toString)
      if i % 2 == 1 then card.setDeadline(LocalDate.ofYearDay(2024, i))
      column.cards += card
    column.sortCardsByDeadline()
    column.cards.last.title shouldEqual "8"

  it should "filter cards by tag when using filterCardsByTag" in:
    val column = new Column()
    for i <- 0 to 9 do
      val card = new Card()
      if i % 2 == 0 then card.setTag("even")
      column.cards += card
    column.filterCardsByTag("even")
    column.cards.forall(_.tag == Some("even")) shouldEqual true

  it should "retrieve all cards from before filtering when using removeFilter" in:
    val column = new Column()
    for i <- 0 to 9 do
      val card = new Card()
      if i % 2 == 0 then card.setTag("even")
      column.cards += card
    column.filterCardsByTag("even")
    column.removeFilter()
    column.cards.forall(_.tag == Some("even")) shouldEqual false

end ColumnTest
