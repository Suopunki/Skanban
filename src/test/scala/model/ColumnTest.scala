package model

import java.time.LocalDate

import munit.FunSuite

class ColumnTest extends FunSuite:

  test("Column should have default values"):
    val column = Column()
    assertEquals(column.title, "new Column")
    assertEquals(column.cards.isEmpty, true)

  test("Column should add a new card"):
    val column = Column().addCard()
    assertEquals(column.cards.size, 1)
    assertEquals(column.cards.head.title, "New Card")

  test("Column should move a card to a new position"):
    val card1 = Card("Card 1")
    val card2 = Card("Card 2")
    val column = Column(cards = Vector(card1, card2)).moveCard(card1, 1)
    assertEquals(column.cards(0).title, "Card 2")
    assertEquals(column.cards(1).title, "Card 1")

  test("Column should remove a card"):
    val card = Card("Card 1")
    val column = Column(cards = Vector(card)).removeCard(card)
    assertEquals(column.cards.isEmpty, true)

  test("Column should sort cards by end date"):
    val card1 = Card("Card 1", endDate = Some(LocalDate.now().plusDays(1)))
    val card2 = Card("Card 2", endDate = Some(LocalDate.now().plusDays(2)))
    val column = Column(cards = Vector(card2, card1)).sortCardsByEndDate()
    assertEquals(column.cards(0).title, "Card 1")
    assertEquals(column.cards(1).title, "Card 2")

  test("Column should filter cards by tag"):
    val card1 = Card("Card 1", tag = Some("urgent"))
    val card2 = Card("Card 2", tag = Some("low"))
    val column = Column(cards = Vector(card1, card2)).filterCardsByTag("urgent")
    assertEquals(column.cards.size, 1)
    assertEquals(column.cards.head.title, "Card 1")

  test("Column should remove card filter and restore original cards"):
    val card1 = Card("Card 1", tag = Some("urgent"))
    val card2 = Card("Card 2", tag = Some("low"))
    val column = Column(cards = Vector(card1, card2)).filterCardsByTag("urgent").removeCardFilter()
    assertEquals(column.cards.size, 2)

  test("Column should filter by tag and preserve unfiltered cards when filter is removed"):
    val card1 = Card("Card 1", tag = Some("urgent"))
    val card2 = Card("Card 2", tag = Some("low"))
    val card3 = Card("Card 3", tag = Some("medium"))
    val column = Column(cards = Vector(card1, card2, card3))

    val filteredByUrgent = column.filterCardsByTag("urgent")
    assertEquals(filteredByUrgent.cards.size, 1)
    assertEquals(filteredByUrgent.cards.head.title, "Card 1")

    assertEquals(filteredByUrgent._unfilteredCards.size, 3)

    val unfilteredColumn = filteredByUrgent.removeCardFilter()
    assertEquals(unfilteredColumn.cards.size, 3)
    assertEquals(unfilteredColumn.cards.map(_.title), Vector("Card 1", "Card 2", "Card 3"))

  test("Column should filter by different tags and restore original cards when filter is removed"):
    val card1 = Card("Card 1", tag = Some("urgent"))
    val card2 = Card("Card 2", tag = Some("low"))
    val card3 = Card("Card 3", tag = Some("medium"))
    val column = Column(cards = Vector(card1, card2, card3))

    val filteredByUrgent = column.filterCardsByTag("urgent")
    assertEquals(filteredByUrgent.cards.size, 1)
    assertEquals(filteredByUrgent.cards.head.title, "Card 1")

    val filteredByLow = filteredByUrgent.filterCardsByTag("low")
    assertEquals(filteredByLow.cards.size, 1)
    assertEquals(filteredByLow.cards.head.title, "Card 2")

    val unfilteredColumn = filteredByLow.removeCardFilter()
    assertEquals(unfilteredColumn.cards.size, 3)
    assertEquals(unfilteredColumn.cards.map(_.title), Vector("Card 1", "Card 2", "Card 3"))

  test("Column being empty, filtering should do nothing and not affect original card state"):
    val column = Column(cards = Vector.empty)

    val filteredColumn = column.filterCardsByTag("urgent")
    assertEquals(filteredColumn.cards.size, 0)

    val unfilteredColumn = filteredColumn.removeCardFilter()
    assertEquals(unfilteredColumn.cards.size, 0)
