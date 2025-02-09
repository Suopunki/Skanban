import gui.scenes._
import logic._
import scalafx.application.JFXApp3
import scalafx.beans.property.ObjectProperty
import scalafx.scene.Scene

object Main extends JFXApp3:

  def start(): Unit =
    val selectedScene = ObjectProperty(Scenes.MainMenu)
    val selectedBoard = ObjectProperty(new Board())

    val mainStage = new JFXApp3.PrimaryStage:
      title = "Kanbafy"
      width = 1024
      height = 768

    lazy val menuScene = MenuScene(mainStage, selectedScene, selectedBoard)
    lazy val boardScene = BoardScene(mainStage, selectedScene, selectedBoard)

    selectedScene.onChange((_, _, newValue) =>
      newValue match
        case Scenes.MainMenu   => stage.setScene(menuScene)
        case Scenes.BoardScene => stage.setScene(boardScene)
        case Scenes.Close      => stage.close()
    )

    mainStage.scene = menuScene
    stage = mainStage

  end start

end Main
