package controller

import model.Task

class TaskController(val task: Task):

  def updateTitle(newTitle: String): Unit =
    task.updateTitle(newTitle)

  def toggleCompletion(): Unit =
    task.toggleCompletion()

  def setCompletion(completed: Boolean): Unit =
    task.setCompletion(completed)
