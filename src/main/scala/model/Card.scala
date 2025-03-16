package model

import io.circe.syntax.EncoderOps
import io.circe.{Decoder, Encoder, HCursor, Json}
import scalafx.beans.property.{DoubleProperty, ObjectProperty, StringProperty}
import scalafx.collections.ObservableBuffer
import utils.json.scalafx.ObjectPropertyCodec.*
import utils.json.scalafx.ObservableBufferCodec.*
import utils.json.scalafx.StringPropertyCodec.*

import java.time.LocalDate

class Card(
    val title: StringProperty = StringProperty("New Card"),
    val tag: ObjectProperty[Option[String]] = ObjectProperty(None),
    val startDate: ObjectProperty[Option[LocalDate]] = ObjectProperty(None),
    val endDate: ObjectProperty[Option[LocalDate]] = ObjectProperty(None),
    val description: ObjectProperty[Option[String]] = ObjectProperty(None),
    val checklist: ObservableBuffer[Task] = ObservableBuffer[Task]()
):
  val checklistProgress: DoubleProperty = DoubleProperty(calculateChecklistProgress())

  def updateTitle(newTitle: String): Unit =
    title.set(newTitle)

  def updateTag(newTag: Option[String]): Unit =
    tag.set(newTag)

  def updateStartDate(newStartDate: Option[LocalDate]): Unit =
    startDate.set(newStartDate)

  def updateEndDate(newEndDate: Option[LocalDate]): Unit =
    endDate.set(newEndDate)

  def updateDescription(newDescription: Option[String]): Unit =
    description.update(newDescription)

  def addTask(task: Task = Task()): Unit =
    checklist += task

  def moveTaskToIndex(task: Task, index: Int): Unit =
    checklist.remove(task)
    checklist.insert(index, task)

  def removeTask(task: Task): Unit =
    checklist.remove(task)

  def calculateChecklistProgress(): Double =
    if (checklist.isEmpty) 0d
    else checklist.count(_.isCompleted.value).toDouble / checklist.length

object Card:
  implicit val cardEncoder: Encoder[Card] = Encoder.instance((card: Card) =>
    Json.obj(
      "title" -> card.title.asJson,
      "tag" -> card.tag.asJson,
      "startDate" -> card.startDate.asJson,
      "endDate" -> card.endDate.asJson,
      "description" -> card.description.asJson,
      "checklist" -> card.checklist.asJson
    )
  )

  implicit val cardDecoder: Decoder[Card] = Decoder.instance((cursor: HCursor) =>
    for
      title <- cursor.downField("title").as[StringProperty]
      tag <- cursor.downField("tag").as[ObjectProperty[Option[String]]]
      startDate <- cursor.downField("startDate").as[ObjectProperty[Option[LocalDate]]]
      endDate <- cursor.downField("endDate").as[ObjectProperty[Option[LocalDate]]]
      description <- cursor.downField("description").as[ObjectProperty[Option[String]]]
      checklist <- cursor.downField("checklist").as[ObservableBuffer[Task]]
    yield Card(title, tag, startDate, endDate, description, checklist)
  )
