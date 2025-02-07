package controller

import model.Task
import scalafx.beans.value.ObservableValue

class TaskController(val task: Task):
  def toggleCompletion(): Unit =
    task.toggleCompletion()

  def updateTitle(newTitle: String): Unit =
    task.updateTitle(newTitle)
