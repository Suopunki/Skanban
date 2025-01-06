package controller

import model.Task

class TaskController(private var task: Task, private val onTaskUpdated: Task => Unit):

  def getCurrentTask: Task = task

  def updateTaskTitle(newTitle: String): Unit =
    task = task.updateTitle(newTitle)
    onTaskUpdated(task)

  def toggleTaskCompletion(): Unit =
    task = task.toggleCompletion()
    onTaskUpdated(task)

  def setTaskCompletion(completed: Boolean): Unit =
    task = task.setCompletion(completed)
    onTaskUpdated(task)
