package model

import java.time.LocalDate

import munit.FunSuite

class BoardTest extends FunSuite:

  test("Board should have default columns and title"):
    val board = Board()
    assertEquals(board.title, "New Board")
    assertEquals(board.columns.size, 3) // To do, In Progress, Done

  test("Board should add a new column"):
    val board = Board().addColumn()
    assertEquals(board.columns.size, 4)

  test("Board should move a column to a new index"):
    val col1 = Column("Column 1")
    val col2 = Column("Column 2")
    val board = Board(columns = Vector(col1, col2)).moveColumnToIndex(col1, 1)
    assertEquals(board.columns(0).title, "Column 2")
    assertEquals(board.columns(1).title, "Column 1")

  test("Board should remove a column"):
    val col = Column("Column 1")
    val board = Board(columns = Vector(col)).removeColumn(col)
    assertEquals(board.columns.isEmpty, true)

  test("Board should add a card to the first column"):
    val board = Board().addCard()
    assertEquals(board.columns.head.cards.size, 2)

  test("Board should sort cards by end date in all columns"):
    val card1 = Card("Card 1", endDate = Some(LocalDate.now().plusDays(1)))
    val card2 = Card("Card 2", endDate = Some(LocalDate.now().plusDays(2)))
    val board = Board(columns = Vector(Column(cards = Vector(card2, card1)))).sortCardsByEndDate()
    assertEquals(board.columns.head.cards(0).title, "Card 1")
    assertEquals(board.columns.head.cards(1).title, "Card 2")

  test("Board should filter cards by tag in all columns"):
    val card1 = Card("Card 1", tag = Some("urgent"))
    val card2 = Card("Card 2", tag = Some("low"))
    val board = Board(columns = Vector(Column(cards = Vector(card1, card2))))
      .filterCardsByTag("urgent")
    assertEquals(board.columns.head.cards.size, 1)
    assertEquals(board.columns.head.cards.head.title, "Card 1")

  test("Board should remove card filter in all columns"):
    val card1 = Card("Card 1", tag = Some("urgent"))
    val card2 = Card("Card 2", tag = Some("low"))
    val board = Board(columns = Vector(Column(cards = Vector(card1, card2))))
      .filterCardsByTag("urgent")
      .removeCardFilter()
    assertEquals(board.columns.head.cards.size, 2)

  test("Board should filter by multiple tags and restore the original unfiltered cards"):
    val card1 = Card("Card 1", tag = Some("urgent"))
    val card2 = Card("Card 2", tag = Some("low"))
    val card3 = Card("Card 3", tag = Some("medium"))
    val column1 = Column(cards = Vector(card1, card2))
    val column2 = Column(cards = Vector(card3))
    val board = Board(columns = Vector(column1, column2))

    val filteredByUrgent = board.filterCardsByTag("urgent")
    assertEquals(filteredByUrgent.columns(0).cards.size, 1)
    assertEquals(filteredByUrgent.columns(0).cards.head.title, "Card 1")
    assertEquals(filteredByUrgent.columns(1).cards.isEmpty, true)

    val filteredByLow = filteredByUrgent.filterCardsByTag("low")
    assertEquals(filteredByLow.columns(0).cards.size, 1)
    assertEquals(filteredByLow.columns(0).cards.head.title, "Card 2")
    assertEquals(filteredByLow.columns(1).cards.isEmpty, true)

    val originalBoard = filteredByLow.removeCardFilter()
    assertEquals(originalBoard.columns(0).cards.size, 2)
    assertEquals(originalBoard.columns(1).cards.size, 1)
    assertEquals(originalBoard.columns(0).cards.map(_.title), Vector("Card 1", "Card 2"))
    assertEquals(originalBoard.columns(1).cards.map(_.title), Vector("Card 3"))
