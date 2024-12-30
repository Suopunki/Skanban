import java.time.LocalDate
import logic._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class BoardTest extends AnyFlatSpec with Matchers:
  "Board" should "move column when using moveColumn" in:
    val board = new Board()
    val todoColumn = board.columns.head
    board.moveColumnToIndex(todoColumn, 2)
    board.columns(2).title shouldEqual "To-do"

  it should "remove column when using removeColumn" in:
    val board = new Board()
    val doneColumn = board.columns.last
    board.removeColumn(doneColumn)
    board.columns.length shouldEqual 2

  it should "add a new card to the first column when using addNewCard" in:
    val board = new Board()
    board.addCard()
    board.columns.head.cards.length shouldEqual 2

  it should "sort the cards on the board by deadline when using sortCardsByDeadline" in:
    val board = new Board()
    for column <- board.columns do
      for i <- 1 to 3 do
        val card = new Card():
          setTitle(i.toString)
          setDeadline(LocalDate.ofYearDay(2024, i))
        column.cards += card
      // for the sake of testing let's reverse the order of the cards
      column.cards = column.cards.reverse
    board.sortCardsByDeadline()
    board.columns.last.cards.head.title shouldEqual "1"

  it should "filter the cards on the board by the card's tag when using filterCardsByTag" in:
    val board = new Board()
    for column <- board.columns do
      for i <- 1 to 3 do
        val card = new Card():
          setTag(i.toString)
        column.cards += card
    board.filterCardsByTag("2")
    // If the filtering works, each of the columns has only one card
    board.columns.last.cards.length shouldEqual 1

  it should "remove the card filtering when using removeFilter" in:
    // same as filtering test
    val board = new Board()
    for column <- board.columns do
      for i <- 1 to 3 do
        val card = new Card():
          setTag(i.toString)
        column.cards += card
    board.filterCardsByTag("2")
    // remove filter
    board.removeFilter()
    board.columns.last.cards.length shouldEqual 3
end BoardTest
