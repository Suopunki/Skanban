package persistence

import model.Board
import scalafx.beans.property.ObjectProperty
import scene.AppScene

case class ApplicationState(
    selectedScene: ObjectProperty[AppScene] = ObjectProperty(AppScene.Menu),
    selectedBoard: ObjectProperty[Board] = ObjectProperty(Board())
)
