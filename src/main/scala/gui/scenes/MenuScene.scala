package gui.scenes

import gui.components._
import gui.scenes._
import java.io.File
import logic.{App, Board}
import scalafx.application.JFXApp3
import scalafx.beans.property.ObjectProperty
import scalafx.scene.Scene
import scalafx.stage.FileChooser

class MenuScene(
  mainStage: JFXApp3.PrimaryStage,
  selectedScene: ObjectProperty[Scenes],
  selectedBoard: ObjectProperty[Board]
) extends Scene {

  val mainContainer = new MainContainer:
    minWidth <== mainStage.width
    prefWidth <== mainStage.width
    maxWidth <== mainStage.width
    minHeight <== mainStage.height
    prefHeight <== mainStage.height
    maxHeight <== mainStage.height

  val menuContainer = new MenuContainer:
    minHeight <== mainStage.height
    minWidth <== mainStage.width
    prefHeight <== mainStage.height
    prefWidth <== mainStage.width
    children ++= Seq(
      new KanbafyTitle("Kanbafy"):
        minWidth <== mainStage.width
        prefWidth <== mainStage.width
      ,
      new MenuButton("New Board"):
        prefWidth <== mainStage.width * 2 / 3
        onAction = (event) => selectedScene.value = Scenes.BoardScene
      ,
      new MenuButton("Open Board"):
        prefWidth <== mainStage.width * 2 / 3
        onAction = (event) =>
          val fileChooser = new FileChooser:
            title = "Open Board"
            initialDirectory = File("./src/main/resources/saveFiles")
          val selectedFile = fileChooser.showOpenDialog(mainStage)
          if (selectedFile != null) then
            val board = App.load(selectedFile)
            selectedBoard.value = board
            selectedScene.value = Scenes.BoardScene
      ,
      new QuitButton("Quit"):
        prefWidth <== mainStage.width * 2 / 3
        onAction = (event) => selectedScene.value = Scenes.Close
    )

  mainContainer.children += menuContainer

  root = mainContainer

}
