package controller

import model.{Card, Column}

class ColumnController(private var column: Column, private val onColumnUpdated: Column => Unit):
  def getCurrentColumn: Column = column

  def addCard(): Unit =
    column = column.addCard()
    onColumnUpdated(column)

  def moveCard(card: Card, newIndex: Int): Unit =
    column = column.moveCard(card, newIndex)
    onColumnUpdated(column)

  def removeCard(card: Card): Unit =
    column = column.removeCard(card)
    onColumnUpdated(column)

  def sortByEndDate(): Unit =
    column = column.sortCardsByEndDate()
    onColumnUpdated(column)

  def filterByTag(tag: String): Unit =
    column = column.filterCardsByTag(tag)
    onColumnUpdated(column)

  def removeFilter(): Unit =
    column = column.removeCardFilter()
    onColumnUpdated(column)

  def handleCardUpdate(oldCard: Card, newCard: Card): Unit =
    column = column.copy(cards = column.cards.map(card => if (card == oldCard) newCard else card))
    onColumnUpdated(column)
