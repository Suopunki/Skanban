package view

import scalafx.scene.control.{CheckBox, TextField}
import scalafx.scene.layout.HBox

import controller.TaskController
import model.Task

class TaskView(initialTask: Task, controller: TaskController) extends HBox:
  spacing = 10

  // TITLE

  private val titleField = new TextField:
    text = initialTask.title
    onAction = _ => controller.updateTaskTitle(text.value)
    focused.onChange((_, _, newValue) => if !newValue then controller.updateTaskTitle(text.value))

  // CHECKBOX

  private val checkbox = new CheckBox:
    selected = initialTask.isCompleted
    onAction = _ => controller.toggleTaskCompletion()

  // CHILDREN

  children = Seq(
    titleField,
    checkbox
  )
