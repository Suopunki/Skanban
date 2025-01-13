package model

import scalafx.beans.property.{BooleanProperty, StringProperty}

case class Task(
    var isCompleted: BooleanProperty = BooleanProperty(false),
    var title: StringProperty = StringProperty("New Task")
):

  def setCompletion(status: Boolean): Unit =
    isCompleted.update(status)

  def updateTitle(newTitle: String): Unit =
    title.update(newTitle)

  def toggleCompletion(): Unit =
    isCompleted.update(!isCompleted.value)
