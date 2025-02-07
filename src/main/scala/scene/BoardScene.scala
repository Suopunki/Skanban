package scene

import controller.CardController
import model.{Board, Card}
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.beans.property.ObjectProperty
import scalafx.scene.Scene
import scalafx.scene.control.{Menu, MenuBar, MenuItem}
import scalafx.scene.input.{KeyCode, KeyCodeCombination, KeyCombination}
import scalafx.scene.layout.VBox
import scalafx.stage.FileChooser
// import service.BoardStorageService
import view.CardView

import java.io.File

class BoardScene(
    mainStage: PrimaryStage,
    selectedScene: ObjectProperty[AppScene],
    selectedBoard: ObjectProperty[Board]
) extends Scene:

  val menuBar = new MenuBar:
    prefWidth <== mainStage.width

    private var saveFile: Option[File] = None

    private val saveKeyboardShortcut = new KeyCodeCombination(
      KeyCode.S,
      KeyCombination.ControlDown
    )

    private val saveAsKeyboardShortcut = new KeyCodeCombination(
      KeyCode.S,
      KeyCombination.ControlDown,
      KeyCombination.ShiftDown
    )

    private def showSaveDialog(dialogTitle: String): Option[File] =
      val fileChooser = new FileChooser:
        title = dialogTitle
      Option(fileChooser.showSaveDialog(mainStage))

    private def handleSaveToFile(file: File): Unit =
      // BoardStorageService.save(selectedBoard.value, file)
      saveFile = Some(file)

    private def handleSave(): Unit =
      saveFile match
        case Some(file) => handleSaveToFile(file)
        case None       => showSaveDialog("Save Board").foreach(file => handleSaveToFile(file))

    private def handleSaveAs(): Unit =
      showSaveDialog("Save Board As").foreach(file => handleSaveToFile(file))

    private val saveMenuItem = new MenuItem("Save"):
      accelerator = saveKeyboardShortcut
      onAction = _ => handleSave()

    private val saveAsMenuItem = new MenuItem("Save As"):
      accelerator = saveAsKeyboardShortcut
      onAction = _ => handleSaveAs()

    /* ===== Menus ===== */
    val fileMenu = new Menu("File"):
      items = Seq(saveMenuItem, saveAsMenuItem)

    /* ==== Children ==== */
    menus ++= Seq(fileMenu)
  end menuBar

  val cardView = CardView(CardController(Card()))

  val mainContainer = new VBox:
    prefWidth <== mainStage.width
    prefHeight <== mainStage.height
    children = Seq(menuBar, cardView)

  root = mainContainer

end BoardScene
