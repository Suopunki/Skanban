package logic

import io.circe._
import io.circe.generic.semiauto._
import java.time.LocalDate as Date
import scala.collection.mutable.ArrayBuffer

case class Card(
  var title: String = "New Card",
  var tag: Option[String] = None,
  var startDate: Option[Date] = None,
  var deadline: Option[Date] = None,
  var description: Option[String] = None,
  var checklist: ArrayBuffer[ChecklistItem] = ArrayBuffer[ChecklistItem]()
):

  def setTitle(title: String): Unit =
    this.title = title

  def setTag(tag: String): Unit =
    this.tag = Some(tag)

  def removeTag(): Unit =
    this.tag = None

  def setStartDate(date: Date): Unit =
    this.startDate = Some(date)

  def removeStartDate(): Unit =
    this.startDate = None

  def setDeadline(date: Date): Unit =
    this.deadline = Some(date)

  def removeDeadline(): Unit =
    this.deadline = None

  def setDescription(description: String): Unit =
    this.description = Some(description)

  def removeDescription(): Unit =
    this.description = None

  def addChecklistItem(): ChecklistItem =
    val checklistItem = new ChecklistItem()
    checklist += checklistItem
    checklistItem

  def removeChecklistItem(item: ChecklistItem): Unit =
    checklist -= item

  def checklistProgress(): Double =
    if checklist.isEmpty then 0
    else checklist.count(_.completed).toDouble / this.checklist.length.toDouble

end Card

// JSON:
implicit val cardEncoder: Encoder[Card] = deriveEncoder
implicit val cardDecoder: Decoder[Card] = deriveDecoder
