package view

import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.*
import scalafx.scene.layout.{HBox, VBox}

import controller.{CardController, TaskController}
import model.Card

class CardView(initialCard: Card, controller: CardController) extends VBox:
  spacing = 10
  padding = Insets(10)

  // TITLE

  private val titleField = new TextField:
    text = initialCard.title
    onAction = _ => controller.updateTitle(text.value)
    focused.onChange((_, _, newValue) => if (!newValue) controller.updateTitle(text.value))

  // START AND END DATES

  val startDatePicker: DatePicker = new DatePicker:
    value = initialCard.startDate.orNull
    promptText = "Start Date"
    onAction = _ => controller.updateStartDate(Option(value.value))

  val endDatePicker: DatePicker = new DatePicker:
    value = initialCard.endDate.orNull
    promptText = "End Date"
    onAction = _ => controller.updateEndDate(Option(value.value))

  private val dateContainer = new HBox:
    spacing = 10
    children = Seq(startDatePicker, endDatePicker)

  // TAG

  private val tagLabel = new Label("Tag:")

  private val tagField = new ComboBox[String]():
    editable = true
    value = initialCard.tag.getOrElse("")
    onAction = _ => controller.updateTag(Option(value.value).filter(_.nonEmpty))

  private val tagContainer: HBox = new HBox:
    spacing = 10
    children = Seq(tagLabel, tagField)

  // DESCRIPTION

  private val descriptionLabel = new Label("Description:")

  private val descriptionArea = new TextArea:
    text = initialCard.description.getOrElse("")
    wrapText = true
    prefRowCount = 3
    promptText = "Add description..."
    focused.onChange((_, _, newValue) =>
      if (!newValue) controller.updateDescription(Option(text.value).filter(_.nonEmpty))
    )

  private val descriptionContainer = new HBox:
    children = Seq(descriptionLabel, descriptionArea)

  // PROGRESS BAR

  private val progressBarLabel = new Label("Progress:")

  private val progressBar = new ProgressBar:
    progress = initialCard.calculateChecklistProgress()
    prefWidth = 200

  private val progressBarContainer = new HBox:
    spacing = 10
    alignment = Pos.CenterLeft
    children = Seq(progressBarLabel, progressBar)

  // CHECKLIST

  private val checklistLabel = new Label("Checklist:")

  private val checklistBox = new VBox:
    spacing = 5
    children = createTaskViews(initialCard)

  private val addTaskButton = new Button("Add Task"):
    onAction = _ => controller.addTask()

  private val checklistContainer = new VBox:
    children = Seq(checklistLabel, checklistBox, addTaskButton)

  // METHODS

  def updateCard(newCard: Card): Unit = {
    titleField.text = newCard.title
    tagField.value = newCard.tag.getOrElse("")
    descriptionArea.text = newCard.description.getOrElse("")
    progressBar.progress = newCard.calculateChecklistProgress()
    checklistBox.children = createTaskViews(newCard)
  }

  private def createTaskViews(card: Card) =
    card.checklist.map(task =>
      val taskController =
        new TaskController(task, updatedTask => controller.handleTaskUpdate(task, updatedTask))
      val taskView = new TaskView(task, taskController)
      new HBox:
        spacing = 5
        children = Seq(
          taskView,
          new Button("×"):
            onAction = _ => controller.removeTask(task)
        )
    )

  // CHILDREN

  children = Seq(
    titleField,
    tagContainer,
    dateContainer,
    descriptionContainer,
    progressBarContainer,
    checklistContainer
  )
