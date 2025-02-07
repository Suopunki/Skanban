package scene

import model.Board
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.beans.property.ObjectProperty
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.VBox
import scalafx.stage.FileChooser
import service.BoardStorageService

class MenuScene(
    mainStage: PrimaryStage,
    selectedScene: ObjectProperty[AppScene],
    selectedBoard: ObjectProperty[Board]
) extends Scene:
  stylesheets = Seq("styles/menu.css")

  val titleLabel = new Label("Skanban"):
    styleClass = Seq("title")
    prefWidth <== mainStage.width

  val newButton = new Button("New"):
    styleClass = Seq("button")
    prefWidth <== mainStage.width / 2
    onAction = event => selectedScene.value = AppScene.Board

  val openButton = new Button("Open"):
    styleClass = Seq("button")
    prefWidth <== mainStage.width / 2
    onAction = event =>
      val fileChooser = new FileChooser:
        title = "Open"
      val selectedFile = fileChooser.showOpenDialog(mainStage)
      if (selectedFile != null) then
        val board = BoardStorageService.load(selectedFile)
        selectedBoard.value = board.get
        selectedScene.value = AppScene.Board

  val exitButton = new Button("Exit"):
    styleClass = Seq("button", "button-exit")
    prefWidth <== mainStage.width / 2
    onAction = event => selectedScene.value = AppScene.Close

  val container = new VBox:
    styleClass = Seq("container")
    prefHeight <== mainStage.height
    prefWidth <== mainStage.width

    children = Seq(titleLabel, newButton, openButton, exitButton)

  root = container
