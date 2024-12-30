package gui.scenes

import gui.components._
import logic._

import java.io.File
import java.time.format.DateTimeFormatter
import java.time.LocalDate
import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.beans.property.ObjectProperty
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{
  Alert,
  Button,
  ButtonType,
  CheckBox,
  ContextMenu,
  DatePicker,
  Label,
  MenuItem,
  ProgressBar,
  SeparatorMenuItem,
  TextInputDialog
}
import scalafx.scene.input.{
  ClipboardContent,
  KeyCode,
  KeyCodeCombination,
  KeyCombination,
  TransferMode
}
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.layout.{BorderPane, HBox, VBox}
import scalafx.stage.FileChooser

class BoardScene(
  mainStage: JFXApp3.PrimaryStage,
  selectedScene: ObjectProperty[Scenes],
  selectedBoard: ObjectProperty[Board]
) extends Scene:

  val board = selectedBoard.value
  val guiColumns = ArrayBuffer[GuiColumn]()
  val guiCards = ArrayBuffer[GuiCard]()
  val checklists = ListBuffer[VBox]()

  def createGuiChecklistItem(checklistItem: ChecklistItem): BorderPane =
    val guiChecklistItem = new BorderPane:
      margin = Insets(0, 10, 0, 10)

    def updateProgressBar(checklist: VBox, card: Card): Unit =
      checklist.children.removeLast()
      val progressBar = new ProgressBar:
        prefWidth <== checklist.width * 3 / 4
        progress = card.checklistProgress()
      checklist.children += progressBar

    val title = new Label(checklistItem.title)
    val checkBox = new CheckBox:
      selected = checklistItem.completed
      onAction = _ =>
        checklistItem.setCompleted(!checklistItem.completed)
        val checklist =
          checklists.find(_.getId == guiChecklistItem.getParent.getId).get
        val guiCard =
          guiCards.find(_.getId == checklist.getParent.getId).get
        updateProgressBar(checklist, guiCard.getCard)

    guiChecklistItem.left = title
    guiChecklistItem.right = checkBox

    def changeTitle(): Unit =
      val dialog = new TextInputDialog(defaultValue = title.getText)
      dialog.setTitle("Change ChecklistItem Title")
      dialog.setHeaderText("Do you want to change the title?")
      dialog.setContentText("New Title:")
      dialog.showAndWait() match
        case Some(input) =>
          title.setText(input)
          checklistItem.setTitle(input)
        case None => println("Checklist item title change canceled")

    def delete(): Unit =
      val guiCardID = guiChecklistItem.getParent.getParent.getId
      val guiCard = guiCards.find(_.getId == guiCardID).get
      guiCard.getCard.removeChecklistItem(checklistItem)
      val checklistID = guiChecklistItem.getParent.getId
      val checklist = checklists.find(_.getId == checklistID).get
      if checklist.children.length == 3 then checklist.children.clear()
      else
        checklist.children -= guiChecklistItem
        updateProgressBar(checklist, guiCard.getCard)

    val contextMenu = new ContextMenu:
      items ++= Seq(
        new MenuItem("Change Title"):
          onAction = _ => changeTitle()
        ,
        new SeparatorMenuItem,
        new MenuItem("Delete"):
          onAction = _ => delete()
      )

    guiChecklistItem.onContextMenuRequested = (event) =>
      contextMenu.show(mainStage, event.getScreenX, event.getScreenY)
      event.consume()

    guiChecklistItem
  end createGuiChecklistItem

  // Function creating GuiCards Based on Cards
  private def createGuiCard(card: Card): GuiCard =
    val guiCard = new GuiCard(card):
      alignment = Pos.Center

    val title = new GuiCardTitle(card.title):
      prefWidth <== guiCard.width

    val tag = new GuiCardTag(card.tag)
    val startDate = new GuiCardStartDate(card.startDate)
    val deadline = new GuiCardDeadline(card.deadline)
    val cardInfo = new BorderPane:
      prefWidth <== guiCard.width
      margin = Insets(5, 10, 5, 10)
      left = tag
      center = startDate
      right = deadline

    val description = new GuiCardDescription(card.description):
      prefWidth <== guiCard.width

    val checklist = new VBox:
      prefWidth <== guiCard.width
      alignment = Pos.TopCenter
      margin = Insets(10)
      spacing = 5

    checklists += checklist

    if card.checklist.nonEmpty then
      val checklistTitle = new Label("Checklist:"):
        prefWidth <== guiCard.width
        alignment = Pos.Center
      checklist.children += checklistTitle
      card.checklist.foreach(checklist.children += createGuiChecklistItem(_))
      val progressBar = new ProgressBar:
        prefWidth <== checklist.width * 3 / 4
        progress = card.checklistProgress()
      checklist.children += progressBar

    guiCard.children ++= Seq(title, cardInfo, description, checklist)

    def changeTitle(): Unit =
      val dialog = new TextInputDialog(defaultValue = title.getText)
      dialog.setTitle("Change Card Title")
      dialog.setHeaderText("Do you want to change the title?")
      dialog.setContentText("New Title:")
      dialog.showAndWait() match
        case Some(input) =>
          title.setText(input)
          card.setTitle(input)
        case None => println("Card title change canceled")

    def changeTag(): Unit =
      val dialog = new TextInputDialog()
      dialog.setTitle("Add or Change Card Tag")
      dialog.setHeaderText("Do you want to change the tag?")
      dialog.setContentText("New Tag:")
      dialog.showAndWait() match
        case Some(input) =>
          tag.setText("Tag: " + input)
          card.setTag(input)
        case None => println("Card tag change canceled")

    def removeTag(): Unit =
      val alert = new Alert(AlertType.Confirmation)
      alert.initOwner(mainStage)
      alert.title = "Remove Card Tag"
      alert.headerText = "Do you want to remove the tag?"
      alert.showAndWait() match
        case Some(ButtonType.OK) =>
          tag.setText("")
          card.removeTag()
        case _ => println("Card tag removal canceled")

    def changeStartDate(): Unit =
      val alert = new Alert(AlertType.Information)
      alert.initOwner(mainStage)
      alert.title = "Add or Change Card Start Date"
      alert.headerText = "Do you want to add or change the start date?"
      alert.contentText = "New Date"
      val datePicker = new DatePicker(LocalDate.now())
      alert.dialogPane().setContent(datePicker)
      alert.showAndWait() match
        case Some(ButtonType.OK) =>
          val date = datePicker.value.value
          val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
          startDate.text = "Start Date: " + date.format(formatter)
          card.setStartDate(date)
        case _ => println("Card start date change canceled")

    def removeStartDate(): Unit =
      val alert = new Alert(AlertType.Confirmation)
      alert.initOwner(mainStage)
      alert.title = "Remove Card Start Date"
      alert.headerText = "Do you want to remove the start date?"
      alert.showAndWait() match
        case Some(ButtonType.OK) =>
          startDate.setText("")
          card.removeStartDate()
        case _ => println("Card start date removal canceled")

    def changeDeadline(): Unit =
      val alert = new Alert(AlertType.Information)
      alert.initOwner(mainStage)
      alert.title = "Add or Change Card Deadline"
      alert.headerText = "Do you want to add or change the deadline?"
      alert.contentText = "New Date"
      val datePicker = new DatePicker(LocalDate.now())
      alert.dialogPane().setContent(datePicker)
      alert.showAndWait() match
        case Some(ButtonType.OK) =>
          val date = datePicker.value.value
          val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
          deadline.text = "Deadline: " + date.format(formatter)
          card.setDeadline(date)
        case _ => println("Card start date change canceled")

    def removeDeadline(): Unit =
      val alert = new Alert(AlertType.Confirmation)
      alert.initOwner(mainStage)
      alert.title = "Remove Card Deadline"
      alert.headerText = "Do you want to remove the deadline?"
      alert.showAndWait() match
        case Some(ButtonType.OK) =>
          deadline.setText("")
          card.removeDeadline()
        case _ => println("Card deadline removal canceled")

    def changeDescription(): Unit =
      val dialog = new TextInputDialog()
      dialog.setTitle("Add or Change Card Description")
      dialog.setHeaderText("Do you want to change the description?")
      dialog.setContentText("New Description:")
      dialog.showAndWait() match
        case Some(input) =>
          description.setText("Description:\n" + input)
          card.setDescription(input)
        case None => println("Card description change canceled")

    def removeDescription(): Unit =
      val alert = new Alert(AlertType.Confirmation)
      alert.initOwner(mainStage)
      alert.title = "Remove Card Description"
      alert.headerText = "Do you want to remove the description?"
      alert.showAndWait() match
        case Some(ButtonType.OK) =>
          description.setText("")
          card.removeDescription()
        case _ => println("Card description removal canceled")

    def addChecklistItem(): Unit =
      val checklistItem = card.addChecklistItem()
      val guiChecklistItem = createGuiChecklistItem(checklistItem)
      if (checklist.children.isEmpty) then
        val title = new Label("Checklist:"):
          prefWidth <== guiCard.width
          alignment = Pos.Center
        val progressBar = new ProgressBar:
          prefWidth <== checklist.width * 3 / 4
          progress = card.checklistProgress()
        checklist.children ++= Seq(title, guiChecklistItem, progressBar)
      else
        checklist.children.removeLast()
        val progressBar = new ProgressBar:
          prefWidth <== checklist.width * 3 / 4
          progress = card.checklistProgress()
        checklist.children ++= Seq(guiChecklistItem, progressBar)

    def archive(): Unit =
      val guiColumn = guiColumns.find(_.getId == guiCard.getParent.getId).get
      guiColumn.children -= guiCard
      guiColumn.getColumn.removeCard(card)
      guiCards -= guiCard
      board.archive += card

    def delete(): Unit =
      val guiColumn = guiColumns.find(_.getId == guiCard.getParent.getId).get
      guiColumn.children -= guiCard
      guiColumn.getColumn.removeCard(card)
      guiCards -= guiCard

    // Context menu for the guiCard
    val contextMenu = new ContextMenu:
      items ++= Seq(
        new MenuItem("Change Title"):
          onAction = _ => changeTitle()
        ,
        new MenuItem("Add or Change Tag"):
          onAction = _ => changeTag()
        ,
        new MenuItem("Add or Change Start Date"):
          onAction = _ => changeStartDate()
        ,
        new MenuItem("Add or Change Deadline"):
          onAction = _ => changeDeadline()
        ,
        new MenuItem("Add or Change Description"):
          onAction = _ => changeDescription()
        ,
        new MenuItem("Add Checklist Item"):
          onAction = _ => addChecklistItem()
        ,
        new SeparatorMenuItem,
        new MenuItem("Remove Tag"):
          onAction = _ => removeTag()
        ,
        new MenuItem("Remove Start Date"):
          onAction = _ => removeStartDate()
        ,
        new MenuItem("Remove Deadline"):
          onAction = _ => removeDeadline()
        ,
        new MenuItem("Remove Description"):
          onAction = _ => removeDescription()
        ,
        new SeparatorMenuItem,
        new MenuItem("Archive"):
          onAction = _ => archive()
        ,
        new MenuItem("Delete Card"):
          onAction = _ => delete()
      )

    // Event handler for the context menu
    guiCard.onContextMenuRequested = (event) =>
      contextMenu.show(mainStage, event.getScreenX, event.getScreenY)
      event.consume()

    // Dragging and dropping
    guiCard.onDragDetected = (event) =>
      val dragboard = guiCard.startDragAndDrop(TransferMode.Move)
      val content = new ClipboardContent()
      content.putString(guiCard.getId)
      dragboard.setContent(content)
      event.consume()

    guiCards += guiCard
    guiCard
  end createGuiCard

  // Function for creating GuiColumns based on Columns
  private def createGuiColumn(column: Column): GuiColumn =

    val guiColumn = new GuiColumn(column)

    val title = new ColumnTitle(column.title):
      prefWidth <== guiColumn.width
    guiColumn.children += title

    for card <- column.cards do
      val guiCard = createGuiCard(card)
      guiCard.prefWidth <== guiColumn.width
      guiColumn.children += guiCard

    def changeTitle(): Unit =
      val dialog = new TextInputDialog(defaultValue = title.text.value)
      dialog.setTitle("Change Column Title")
      dialog.setHeaderText("Do you want to change the title?")
      dialog.setContentText("New Title:")
      dialog.showAndWait() match
        case Some(name) =>
          title.setText(name)
          column.setTitle(name)
        case None => println("Column title change canceled")

    def addCard(): Unit =
      val card = column.addCard()
      val guiCard = createGuiCard(card)
      guiCard.prefWidth <== guiColumn.width
      guiColumn.children += guiCard

    def delete(): Unit =
      if guiColumns.length > 2 then
        if column.cards.isEmpty then
          columnContainer.children -= guiColumn
          guiColumns -= guiColumn
          updateGuiColumnWidths()
          board.columns -= column
        else
          val alert = new Alert(AlertType.Warning)
          alert.initOwner(mainStage)
          alert.title = "Delete Column Alert"
          alert.headerText = "Only Empty Columns Can Be Deleted!"
          alert.contentText =
            "Move or delete all the cards in the column if you want to delete the column"
          alert.showAndWait()
      else
        val alert = new Alert(AlertType.Warning)
        alert.initOwner(mainStage)
        alert.title = "Delete Column Alert"
        alert.headerText = "Must Have At Least Two Columns"
        alert.contentText = "The minimumn number of columns is two"
        alert.showAndWait()

    val contextMenu = new ContextMenu:
      items ++= Seq(
        new MenuItem("Change Title"):
          onAction = _ => changeTitle()
        ,
        new MenuItem("Add a Card"):
          onAction = _ => addCard()
        ,
        new SeparatorMenuItem(),
        new MenuItem("Delete"):
          onAction = _ => delete()
      )
    end contextMenu

    guiColumn.onContextMenuRequested = (event) =>
      contextMenu.show(mainStage, event.getScreenX, event.getScreenY)
      event.consume()

    // Dragging and dropping GuiCards
    guiColumn.onDragOver = (event) =>
      if (event.getGestureSource != guiColumn && event.getDragboard.hasString)
      then event.acceptTransferModes(TransferMode.Move)
      event.consume()

    // Dragging and Dropping GuiCards
    guiColumn.onDragDropped = (event) =>
      val dragboard = event.getDragboard
      var success = false
      if dragboard.hasString then
        val guiCardId = dragboard.getString
        val guiCardToMove = guiCards.find(_.id.value == guiCardId).get
        val guiColumnId = guiCardToMove.getParent.getId
        val sourceGuiColumn = guiColumns.find(_.id.value == guiColumnId).get
        sourceGuiColumn.children -= guiCardToMove
        sourceGuiColumn.getColumn.removeCard(guiCardToMove.getCard)
        guiColumn.children += guiCardToMove
        guiColumn.getColumn.cards += guiCardToMove.getCard
        success = true
      event.setDropCompleted(success)
      event.consume()

    // Dragging and dropping GuiColumns
    guiColumn.onDragDetected = (event) =>
      val dragboard = guiColumn.startDragAndDrop(TransferMode.Move)
      val content = new ClipboardContent()
      content.putString(guiColumn.id.value)
      dragboard.setContent(content)
      event.consume()

    guiColumn
  end createGuiColumn

  // Function for resizing the GuiColumns
  private def updateGuiColumnWidths(): Unit =
    guiColumns.foreach(_.prefWidth <== (mainStage.width / guiColumns.length))

  // Main Container for all the UI elements
  val mainContainer = new MainContainer:
    minWidth <== mainStage.width
    minHeight <== mainStage.height
    prefWidth <== mainStage.width
    prefHeight <== mainStage.height

  // Menu bar
  val menuBar = new BoardMenuBar:
    prefWidth <== mainStage.width

    val fileMenu = new BoardMenu("File"):
      private val saveDir = File("./src/main/resources/saveFiles")
      private var saveFile: Option[File] = None

      val saveKeyBind =
        new KeyCodeCombination(KeyCode.S, KeyCombination.ControlDown)

      val saveAsKeyBind = new KeyCodeCombination(
        KeyCode.S,
        KeyCombination.ControlDown,
        KeyCombination.ShiftDown
      )

      val exitKeyBind = new KeyCodeCombination(KeyCode.Escape)

      def save(): Unit =
        saveFile match
          case Some(file) => App.save(board, file)
          case None =>
            val fileChooser = new FileChooser:
              title = "Save Board"
              initialDirectory = saveDir
              showSaveDialog(mainStage) match
                case null => println("'Save' Cancelled")
                case file =>
                  App.save(board, file)
                  saveFile = Some(file)

      def saveAs(): Unit =
        val fileChooser = new FileChooser:
          title = "Save Board As"
          initialDirectory = saveDir
          showSaveDialog(mainStage) match
            case null => println("'Save as' cancelled")
            case file =>
              App.save(board, file)
              saveFile = Some(file)

      def exit(): Unit =
        selectedScene.value = Scenes.Close

      this.items ++= Seq(
        new MenuItem("Save"):
          accelerator = saveKeyBind
          onAction = _ => save()
        ,
        new MenuItem("Save As"):
          accelerator = saveAsKeyBind
          onAction = _ => saveAs()
        ,
        new SeparatorMenuItem,
        new MenuItem("Exit"):
          accelerator = exitKeyBind
          onAction = _ => exit()
      )
    end fileMenu

    val editMenu = new BoardMenu("Edit"):

      def addCard(): Unit =
        val card = board.addCard()
        val guiCard = createGuiCard(card)
        guiColumns.head.children += guiCard

      def addColumn(): Unit =
        if (guiColumns.length < 5) then
          val column = board.addColumn()
          val guiColumn = createGuiColumn(column)
          guiColumns += guiColumn
          columnContainer.children += guiColumn
          updateGuiColumnWidths()
        else
          val alert = new Alert(AlertType.Warning)
          alert.initOwner(mainStage)
          alert.title = "Maximum Number of Columns"
          alert.headerText = "The maximum number of columns is five"
          alert.showAndWait()

      def updateBoard(): Unit =
        for guiColumn <- guiColumns do
          val title = guiColumn.children.head
          guiColumn.children.clear()
          guiColumn.children += title
          for card <- guiColumn.getColumn.cards do
            guiColumn.children += createGuiCard(card)

      def filterByTag(): Unit =
        val dialog = new TextInputDialog()
        dialog.setTitle("Filter Cards by Tag")
        dialog.setHeaderText("Do you want to filter the cards by a tag?")
        dialog.setContentText("Tag:")
        dialog.showAndWait() match
          case Some(tag) =>
            board.filterCardsByTag(tag)
            updateBoard()
          case None => println("Filtering cards by tag canceled")

      def removeFilter(): Unit =
        board.removeFilter()
        updateBoard()

      def sortByDeadline(): Unit =
        board.sortCardsByDeadline()
        updateBoard()

      this.items ++= Seq(
        new BoardMenuItem("Add a Card"):
          onAction = _ => addCard()
        ,
        new BoardMenuItem("Add a Column"):
          onAction = _ => addColumn()
        ,
        new BoardMenuItem("Filter Cards by Tag"):
          onAction = _ => filterByTag()
        ,
        new BoardMenuItem("Remove Filtering by Tag"):
          onAction = _ => removeFilter()
        ,
        new BoardMenuItem("Sort Cards by Deadline"):
          onAction = _ => sortByDeadline()
      )
    end editMenu

    val archiveMenu = new BoardMenu("Archive"):

      def showArchive(): Unit =

        val archiveStage = new PrimaryStage:
          minWidth = 300
          width = 600
          height = 800
          title = "Kanbafy"

        val archiveContent = new VBox:
          prefWidth <== archiveStage.width - 10
          prefHeight <== archiveStage.height
          style = "-fx-background-color: #232323;"

        val topBar = new BorderPane:
          prefWidth <== archiveStage.width
          center = new Label("Archive:"):
            alignment = Pos.Center
            style = """
                |-fx-text-fill: white;
                |-fx-font-size: 25;
                |-fx-font-weight: bold;
                |""".stripMargin
          right = new Button("Close Archive"):
            onAction = _ =>
              selectedScene.value = Scenes.MainMenu
              selectedScene.value = Scenes.BoardScene

        val cardContainer = new VBox:
          prefWidth <== archiveStage.width
          alignment = Pos.Center
          spacing = 20
          margin = Insets(20, 20, 20, 20)

        // Creating cards and "unarchive" buttons
        for card <- board.archive do
          val cardAndButton = new HBox
          val guiCard = createGuiCard(card)
          guiCard.prefWidth = 300
          guiCard.alignment = Pos.TopCenter
          guiCard.style = "-fx-background-color: yellow;"
          guiCard.children.removeFirst()
          val title = new GuiCardTitle(card.title)
          title.style = """
              |-fx-alignment: center;
              |-fx-font-size: 14;
              |-fx-font-weight: bold;
              |-fx-padding: 0.5em;
              |""".stripMargin
          guiCard.children.insert(0, title)
          val button = new Button("Unarchive"):
            onAction = _ =>
              cardContainer.children -= cardAndButton
              board.archive -= card
              board.columns.head.cards += card
              val boardGuiCard = createGuiCard(card)
              guiColumns.head.children += boardGuiCard
              guiCards += boardGuiCard
          cardAndButton.children ++= Seq(guiCard, button)
          cardContainer.children += cardAndButton

        archiveContent.children ++= Seq(topBar, cardContainer)

        archiveStage.scene = new Scene:
          content = archiveContent

        archiveStage.show()
      end showArchive

      this.items ++= Seq(
        new MenuItem("Show Archive"):
          onAction = _ => showArchive()
      )

    end archiveMenu

    this.menus ++= Seq(fileMenu, editMenu, archiveMenu)
  end menuBar

  // Board title
  val boardTitle = new BoardTitle(board.title):
    prefWidth <== mainStage.width

    def changeBoardTitle(): Unit =
      val dialog = new TextInputDialog(defaultValue = board.title)
      dialog.setTitle("Change Board Title")
      dialog.setHeaderText("Do you want to change the title?")
      dialog.setContentText("New Title:")
      dialog.showAndWait() match
        case Some(input) =>
          this.setText(input)
          board.setTitle(input)
        case None => println("Board title change canceled")

    val changeTitleMenuItem = new MenuItem("Change Board Title"):
      onAction = _ => changeBoardTitle()

    val boardTitleContextMenu = new ContextMenu(changeTitleMenuItem)

    onContextMenuRequested = (event) =>
      boardTitleContextMenu.show(mainStage, event.getScreenX, event.getScreenY)
      event.consume()
  end boardTitle

  // Container for the columns
  val columnContainer = new ColumnContainer:
    prefWidth <== mainStage.width
    prefHeight <== mainStage.height - boardTitle.height

    for column <- board.columns do
      val guiColumn = createGuiColumn(column)
      guiColumns += guiColumn
      this.children += guiColumn
    updateGuiColumnWidths()

    // Dragging and dropping GuiColumns
    onDragOver = (event) =>
      if event.getGestureSource != this && event.getDragboard.hasString then
        event.acceptTransferModes(TransferMode.Move)
      event.consume()

    // Dragging and dropping GuiColumns
    onDragDropped = (event) =>
      val dragboard = event.getDragboard
      var success = false
      if (dragboard.hasString) then
        val guiColumnId = dragboard.getString
        val guiColumn = guiColumns.find(_.id.value == guiColumnId).get
        val currentIndex = this.children.indexOf(guiColumn)
        val deltaX = event.getX - guiColumn.getLayoutX
        var change = (deltaX / guiColumn.getWidth).toInt
        if change > 1 then change -= 1
        val newIndex = (0 max currentIndex + change) min guiColumns.length - 1
        this.children -= guiColumn
        this.children.add(newIndex, guiColumn)
        guiColumns -= guiColumn
        guiColumns.insert(newIndex, guiColumn)
        board.columns -= guiColumn.getColumn
        board.columns.insert(newIndex, guiColumn.getColumn)
        success = true
      event.setDropCompleted(success)
      event.consume()

  end columnContainer

  mainContainer.children ++= Seq(menuBar, boardTitle, columnContainer)

  root = mainContainer

end BoardScene
