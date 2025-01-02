package state

import gui.scenes.Scenes
import logic.Board
import scalafx.beans.property.ObjectProperty

case class ApplicationState(
    selectedScene: ObjectProperty[Scenes] = ObjectProperty(Scenes.Menu),
    selectedBoard: ObjectProperty[Board] = ObjectProperty(Board())
)
