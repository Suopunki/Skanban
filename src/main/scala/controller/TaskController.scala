package controller

import model.Task
import scalafx.beans.value.ObservableValue

class TaskController(val task: Task, onCompletionChanged: () => Unit):

  def toggleCompletion(): Unit =
    task.toggleCompletion()

  def setCompletion(completed: Boolean): Unit =
    task.setCompletion(completed)

  def updateTitle(newTitle: String): Unit =
    task.updateTitle(newTitle)
