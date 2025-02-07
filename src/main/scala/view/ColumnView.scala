package view

import scalafx.Includes.*
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, Label, ScrollPane, TextField}
import scalafx.scene.input.{DragEvent, MouseEvent}
import scalafx.scene.layout.{HBox, VBox}

import controller.{CardController, ColumnController}
import model.{Card, Column}

class ColumnView(initialColumn: Column, controller: ColumnController) extends VBox:
  spacing = 10
  padding = Insets(10)
  prefWidth = 300

  // HEADER

  private val titleLabel: Label = new Label(initialColumn.title)

  private val sortButton: Button = new Button("Sort by Date"):
    onAction = _ => controller.sortByEndDate()

  private val filterField = new TextField:
    promptText = "Filter by tag..."
    onAction = _ => controller.filterByTag(text.value)

  private val clearFilterButton = new Button("Clear Filter"):
    onAction = _ => controller.removeFilter()

  private val filterBox: HBox = new HBox:
    spacing = 5
    children = Seq(filterField, clearFilterButton)

  private val headerContainer: HBox = new HBox:
    spacing = 10
    alignment = Pos.CenterLeft
    children = Seq(titleLabel, sortButton, filterBox)

  // CARDS

  private val cardsBox: VBox = new VBox:
    spacing = 10
    children = createCardViews(initialColumn)

  private val scrollPane: ScrollPane = new ScrollPane:
    content = cardsBox
    fitToWidth = true

  private val addCardButton: Button = new Button("+ Add Card"):
    onAction = _ => controller.addCard()
    maxWidth = Double.MaxValue

  private val cardsContainer = new VBox:
    children = Seq(scrollPane, addCardButton)

  // METHODS

  def updateColumn(newColumn: Column): Unit =
    cardsBox.children = createCardViews(newColumn)

  private def createCardViews(column: Column) =
    column.cards.map(card =>
      val cardController = CardController(card)
      val cardView = CardView(cardController)

      // Wrap cardView for dragging and dropping
      new VBox:
        children = Seq(cardView)
        onDragDetected = event => handleOnDragDetected(event, card)
        onDragOver = event => handleOnDragOver(event)
        onDragDropped = event => handleOnDragDropped(event)
    )

  private def handleOnDragDetected(event: MouseEvent, card: Card): Unit =
    val dragboard = startDragAndDrop()
    dragboard.setContent:
      val content = new scalafx.scene.input.ClipboardContent()
      content.putString(card.toString)
      content
    event.consume()

  private def handleOnDragOver(event: DragEvent): Unit =
    if (event.getGestureSource != this) then
      event.acceptTransferModes(scalafx.scene.input.TransferMode.Move)
    event.consume()

  private def handleOnDragDropped(event: DragEvent): Unit =
    val dragboard = event.getDragboard
    if dragboard.hasString then
      val sourceIndex = dragboard.getString.toInt
      val sourceCard = controller.getCurrentColumn.cards(sourceIndex)
      val targetIndex = cardsBox.children.indexOf(this)
      controller.moveCard(sourceCard, targetIndex)
      event.setDropCompleted(true)
    else event.setDropCompleted(false)
    event.consume()

  // CHILDREN

  children = Seq(
    headerContainer,
    cardsContainer
  )
