package view

import scalafx.Includes.*
import scalafx.application.Platform
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Node
import scalafx.scene.control.*
import scalafx.scene.input.{DragEvent, MouseEvent}
import scalafx.scene.layout.{HBox, VBox}

import controller.{BoardController, ColumnController}
import model.{Board, Column}

class BoardView(initialBoard: Board, controller: BoardController) extends VBox:
  spacing = 15
  padding = Insets(20)

  // HEADER

  private val headerBox: VBox = new VBox:
    spacing = 10

    private val titleRow: HBox = new HBox:
      spacing = 15
      alignment = Pos.CenterLeft

      children = Seq(
        new Label(initialBoard.title),
        new Button("+ Add Column"):
          onAction = _ => controller.addColumn()
      )

    private val actionsRow: HBox = new HBox:
      spacing = 10

      children = Seq(
        new Button("Sort All by Date"):
          onAction = _ => controller.sortAllByEndDate()
        ,
        new TextField:
          promptText = "Filter all by tag..."
          onAction = _ => controller.filterAllByTag(text.value)
        ,
        new Button("Clear Filters"):
          onAction = _ => controller.removeAllFilters()
      )

    children = Seq(titleRow, actionsRow)

  // COLUMNS

  private val columnsContainer: ScrollPane = new ScrollPane:
    fitToHeight = true

    content = new HBox:
      spacing = 20
      padding = Insets(10)
      children = createColumnViews(initialBoard)

  // METHODS

  def updateBoard(newBoard: Board): Unit =
    Platform.runLater(
      columnsContainer.content.value.asInstanceOf[HBox].children = createColumnViews(newBoard)
    )

  private def createColumnViews(board: Board): Seq[Node] =
    board.columns.map(column =>
      val columnController = new ColumnController(
        column,
        updatedColumn => controller.handleColumnUpdate(column, updatedColumn)
      )
      val columnView = new ColumnView(column, columnController)

      new HBox:
        children = Seq(columnView)
        onDragDetected = event => handleOnDragDetected(event, board, column)
        onDragOver = event => handleOnDragOver(event)
        onDragDropped = event => handleOnDragDropped(event)
    )

  private def handleOnDragDetected(event: MouseEvent, board: Board, column: Column): Unit =
    val db = startDragAndDrop()
    db.setContent:
      val content = new javafx.scene.input.ClipboardContent()
      val dragData = s"COLUMN_DRAG_ID:${board.columns.indexOf(column)}"
      content
    event.consume()

  private def handleOnDragOver(event: DragEvent): Unit =
    val dragData = event.getDragboard.getString
    if event.getGestureSource != this && dragData.split(":").headOption.contains("COLUMN_DRAG_ID")
    then event.acceptTransferModes(javafx.scene.input.TransferMode.MOVE)
    event.consume()

  private def handleOnDragDropped(event: DragEvent): Unit =
    val db = event.getDragboard
    if db.hasString && db.getString.startsWith("COLUMN_DRAG_ID:") then
      val sourceIndex = db.getString.split(":")(1).toInt
      val sourceColumn = controller.getCurrentBoard.columns(sourceIndex)
      val targetIndex =
        columnsContainer.content.value.asInstanceOf[HBox].children.indexOf(this)
      controller.moveColumn(sourceColumn, targetIndex)
      event.setDropCompleted(true)
    else event.setDropCompleted(false)
    event.consume()

  // CHILDREN

  children = Seq(headerBox, columnsContainer)
