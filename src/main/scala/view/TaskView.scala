package view

import controller.TaskController
import scalafx.scene.control.{CheckBox, TextField}
import scalafx.scene.layout.HBox

class TaskView(controller: TaskController) extends HBox:
  val checkbox = new CheckBox:
    selected <== controller.task.isCompleted
    onAction = _ => controller.toggleCompletion()

  val titleField = new TextField:
    text <== controller.task.title
    onAction = _ => controller.updateTitle(text())

  children = Seq(
    checkbox,
    titleField
  )
