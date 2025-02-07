package model

import scalafx.beans.property.{DoubleProperty, ObjectProperty, StringProperty}
import scalafx.collections.ObservableBuffer

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
    title.update(newTitle)

  def updateTag(newTag: String): Unit =
    tag.update(Some(newTag))

  def removeTag(): Unit =
    tag.update(None)

  def updateStartDate(newStartDate: LocalDate): Unit =
    startDate.update(Some(newStartDate))

  def removeStartDate(): Unit =
    startDate.update(None)

  def updateEndDate(newEndDate: LocalDate): Unit =
    endDate.update(Some(newEndDate))

  def removeEndDate(): Unit =
    endDate.update(None)

  def updateDescription(newDescription: String): Unit =
    description.update(Some(newDescription))

  def removeDescription(): Unit =
    description.update(None)

  def addNewTask(): Unit =
    checklist += Task()

  def insertTask(index: Int, task: Task): Unit =
    checklist.insert(index, task)

  def removeTask(task: Task): Unit =
    checklist.filterInPlace(_ != task)

  def calculateChecklistProgress(): Double =
    if (checklist.isEmpty) 0d
    else checklist.count(_.isCompleted.value).toDouble / checklist.length
