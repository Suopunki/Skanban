package persistence

import model.Board
import scalafx.beans.property.ObjectProperty
import scenes.Scenes

case class ApplicationState(
    selectedScene: ObjectProperty[Scenes] = ObjectProperty(Scenes.Menu),
    selectedBoard: ObjectProperty[Board] = ObjectProperty(Board())
)
