package controller

import java.time.LocalDate

import model.{Card, Task}

class CardController(private var card: Card, private val onCardUpdated: Card => Unit):

  def getCurrentCard: Card = card

  def updateTitle(newTitle: String): Unit =
    card = card.updateTitle(newTitle)
    onCardUpdated(card)

  def updateTag(newTag: Option[String]): Unit =
    card = card.updateTag(newTag)
    onCardUpdated(card)

  def updateStartDate(date: Option[LocalDate]): Unit =
    card = card.updateStartDate(date)
    onCardUpdated(card)

  def updateEndDate(date: Option[LocalDate]): Unit =
    card = card.updateEndDate(date)
    onCardUpdated(card)

  def updateDescription(description: Option[String]): Unit =
    card = card.updateDescription(description)
    onCardUpdated(card)

  def addTask(task: Task = Task()): Unit =
    card = card.addTask(task)
    onCardUpdated(card)

  def removeTask(task: Task): Unit =
    card = card.removeTask(task)
    onCardUpdated(card)

  def updateTask(oldTask: Task, newTask: Task): Unit =
    card = card.updateTask(oldTask, newTask)
    onCardUpdated(card)

  def handleTaskUpdate(oldTask: Task, newTask: Task): Unit =
    updateTask(oldTask, newTask)

