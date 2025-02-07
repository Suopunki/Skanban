package view

import scalafx.application.JFXApp3.{PrimaryStage, Stage}
import scalafx.beans.property.ObjectProperty
import scalafx.scene.Scene

import config.ApplicationConfig
import scene.{AppScene, BoardScene, MenuScene}
import persistence.ApplicationState

class StageInitializer(config: ApplicationConfig):

  def initializeStage(state: ApplicationState): Unit =
    val mainStage = createPrimaryStage()
    val scenes = initializeScenes(mainStage, state)

    setupSceneTransitions(mainStage, scenes, state.selectedScene)
    mainStage.scene = scenes.menuScene
    Stage = mainStage

  private def createPrimaryStage(): PrimaryStage =
    new PrimaryStage:
      title = config.title
      width = config.width
      height = config.height

  private def initializeScenes(
      stage: PrimaryStage,
      state: ApplicationState
  ): ApplicationScenes =
    ApplicationScenes(
      menuScene = MenuScene(stage, state.selectedScene, state.selectedBoard),
      boardScene = BoardScene(stage, state.selectedScene, state.selectedBoard)
    )

  private def setupSceneTransitions(
      stage: PrimaryStage,
      scenes: ApplicationScenes,
      selectedScene: ObjectProperty[AppScene]
  ): Unit =
    selectedScene.onChange((_, _, newValue) =>
      newValue match
        case AppScene.Menu  => stage.scene = scenes.menuScene
        case AppScene.Board => stage.scene = scenes.boardScene
        case AppScene.Close => stage.close()
    )

private case class ApplicationScenes(menuScene: Scene, boardScene: Scene)
