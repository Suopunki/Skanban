package model

import io.circe.{Decoder, Encoder, HCursor, Json}
import scalafx.beans.property.{BooleanProperty, StringProperty}

class Task(
    val isCompleted: BooleanProperty = BooleanProperty(false),
    val title: StringProperty = StringProperty("New Task")
):
  def toggleCompletion(): Unit =
    isCompleted.update(!isCompleted.value)

  def updateTitle(newTitle: String): Unit =
    title.update(newTitle)

object Task:
  implicit val taskEncoder: Encoder[Task] = (task: Task) =>
    Json.obj(
      ("isCompleted", Json.fromBoolean(task.isCompleted.value)),
      ("title", Json.fromString(task.title.value))
    )

  implicit val taskDecoder: Decoder[Task] = (cursor: HCursor) =>
    for
      isCompleted <- cursor.downField("isCompleted").as[Boolean]
      title <- cursor.downField("title").as[String]
    yield Task(
      BooleanProperty(isCompleted),
      StringProperty(title)
    )
