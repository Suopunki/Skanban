package view

import controller.{CardController, TaskController}
import model.{Card, Task}
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.*
import scalafx.scene.layout.{HBox, VBox}

class CardView(controller: CardController) extends VBox:

  val startDatePicker: DatePicker = new DatePicker:
    value = controller.card.startDate().orNull
    promptText = "Start Date"
    onAction = _ => controller.updateStartDate(value())

  val endDatePicker: DatePicker = new DatePicker:
    value = controller.card.endDate().orNull
    promptText = "End Date"
    onAction = _ => controller.updateEndDate(value())

  val dateContainer = new HBox:
    children = Seq(startDatePicker, endDatePicker)

  val titleField = new TextField:
    text <== controller.card.title
    onAction = _ => controller.updateTitle(text())

  val tagLabel = new Label("Tag:")

  val tagField = new ComboBox[String]():
    editable = true
    value = controller.card.tag().getOrElse("")
    onAction = _ => controller.updateTag(value())

  val tagContainer: HBox = new HBox:
    children = Seq(tagLabel, tagField)

  val descriptionLabel = new Label("Description:")

  val descriptionArea = new TextArea:
    text = controller.card.description().getOrElse("")
    wrapText = true
    prefRowCount = 3
    promptText = "Add description..."
    focused.onChange((_, _, newValue) =>
      if (!newValue) controller.updateDescription(text()))

  val descriptionContainer = new HBox:
    children = Seq(descriptionLabel, descriptionArea)

  val progressBarLabel = new Label("Progress:")

  val progressBar = new ProgressBar:
    progress <== controller.card.checklistProgress

  val progressBarContainer = new HBox:
    children = Seq(progressBarLabel, progressBar)

  val checklistLabel = new Label("Checklist:")

  val checklistBox = new VBox:
    children = createTaskViews(controller.card.checklist)

  val addTaskButton = new Button("Add Task"):
    onAction = _ => controller.addNewTask()

  val checklistContainer = new VBox:
    children = Seq(checklistLabel, checklistBox, addTaskButton)

  children = Seq(
    titleField,
    tagContainer,
    dateContainer,
    descriptionContainer,
    progressBarContainer,
    checklistContainer
  )

  private def createTaskViews(tasks: ObservableBuffer[Task]): Seq[TaskView] =
    tasks.map(task => createTaskView(task)).toSeq

  private def createTaskView(task: Task): TaskView =
    TaskView(TaskController(task))
