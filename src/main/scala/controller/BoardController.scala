package controller

import model.{Board, Card, Column}

class BoardController(private var board: Board):
  def getCurrentBoard: Board = board

  def addColumn(): Unit =
    board = board.addColumn()

  def moveColumn(column: Column, newIndex: Int): Unit =
    board = board.moveColumnToIndex(column, newIndex)

  def removeColumn(column: Column): Unit =
    board = board.removeColumn(column)

  def addCard(): Unit =
    board = board.addCard()

  def sortAllByEndDate(): Unit =
    board = board.sortCardsByEndDate()

  def filterAllByTag(tag: String): Unit =
    board = board.filterCardsByTag(tag)

  def removeAllFilters(): Unit =
    board = board.removeCardFilter()

  def handleColumnUpdate(oldColumn: Column, newColumn: Column): Unit =
    board = board.copy(columns =
      board.columns.map(column => if column == oldColumn then newColumn else column)
    )
