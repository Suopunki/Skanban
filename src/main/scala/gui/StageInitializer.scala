package gui

import config.ApplicationConfig
import gui.scenes.{BoardScene, MenuScene, Scenes}
import scalafx.application.JFXApp3
import scalafx.beans.property.ObjectProperty
import scalafx.scene.Scene
import state.ApplicationState

class StageInitializer(config: ApplicationConfig):
  def initializeStage(state: ApplicationState): Unit =
    val mainStage = createPrimaryStage()
    val scenes = initializeScenes(mainStage, state)

    setupSceneTransitions(mainStage, scenes, state.selectedScene)
    mainStage.scene = scenes.menuScene
    JFXApp3.Stage = mainStage

  private def createPrimaryStage(): JFXApp3.PrimaryStage =
    new JFXApp3.PrimaryStage:
      title = config.title
      width = config.width
      height = config.height

  private def initializeScenes(
      stage: JFXApp3.PrimaryStage,
      state: ApplicationState
  ): ApplicationScenes =
    ApplicationScenes(
      menuScene = MenuScene(stage, state.selectedScene, state.selectedBoard),
      boardScene = BoardScene(stage, state.selectedScene, state.selectedBoard)
    )

  private def setupSceneTransitions(
      stage: JFXApp3.PrimaryStage,
      scenes: ApplicationScenes,
      selectedScene: ObjectProperty[Scenes]
  ): Unit =
    selectedScene.onChange((_, _, newValue) =>
      newValue match
        case Scenes.Menu  => stage.scene = scenes.menuScene
        case Scenes.Board => stage.scene = scenes.boardScene
        case Scenes.Close => stage.close()
    )

private case class ApplicationScenes(
    menuScene: Scene,
    boardScene: Scene
)
