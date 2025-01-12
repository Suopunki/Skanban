package model

import scalafx.beans.property.{BooleanProperty, StringProperty}

case class Task(
    var title: StringProperty = StringProperty("New Task"),
    var isCompleted: BooleanProperty = BooleanProperty(false)
):

  def updateTitle(newTitle: String): Unit =
    title.update(newTitle)

  def toggleCompletion(): Unit =
    isCompleted.update(!isCompleted.value)

  def setCompletion(status: Boolean): Unit =
    isCompleted.update(status)
