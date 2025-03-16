package model

import io.circe.syntax.EncoderOps
import io.circe.{Decoder, Encoder, HCursor, Json}
import scalafx.beans.property.{BooleanProperty, StringProperty}
import utils.json.scalafx.BooleanPropertyCodec.*
import utils.json.scalafx.StringPropertyCodec.*

class Task(
    val isCompleted: BooleanProperty = BooleanProperty(false),
    val title: StringProperty = StringProperty("New Task")
):
  def toggleCompletion(): Boolean =
    isCompleted.update(!isCompleted.value)
    isCompleted.value

  def updateTitle(newTitle: String): Unit =
    title.update(newTitle)

object Task:
  implicit val taskEncoder: Encoder[Task] = Encoder.instance((task: Task) =>
    Json.obj(
      "isCompleted" -> task.isCompleted.asJson,
      "title" -> task.title.asJson
    )
  )

  implicit val taskDecoder: Decoder[Task] = Decoder.instance((cursor: HCursor) =>
    for
      isCompleted <- cursor.downField("isCompleted").as[BooleanProperty]
      title <- cursor.downField("title").as[StringProperty]
    yield Task(isCompleted, title)
  )
