import config.ApplicationConfig
import gui.StageInitializer
import scalafx.application.JFXApp3
import state.ApplicationState

object Skanban extends JFXApp3:
  override def start(): Unit =
    StageInitializer(ApplicationConfig.default)
      .initializeStage(ApplicationState())
