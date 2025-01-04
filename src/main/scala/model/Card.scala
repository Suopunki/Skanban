package model

import java.time.LocalDate

/** Represents a Card within a [[Column]] on the Kanban board.
  *
  * @param title The title of the card (default is "New Card").
  * @param tag An optional tag associated with the card.
  * @param startDate The optional start date for the card.
  * @param endDate The optional end date for the card.
  * @param description The optional description of the card.
  * @param checklist A vector of [[Task]]s associated with the card's checklist (default is an empty
  *   vector).
  */
case class Card(
    title: String = "New Card",
    tag: Option[String] = None,
    startDate: Option[LocalDate] = None,
    endDate: Option[LocalDate] = None,
    description: Option[String] = None,
    checklist: Vector[Task] = Vector.empty
):

  /** Updates the title of the card.
    *
    * @param newTitle The new title for the card.
    * @return A new instance of `Card` with the updated title.
    */
  def updateTitle(newTitle: String): Card =
    copy(title = newTitle)

  /** Updates the tag of the card.
    *
    * @param newTag The new tag for the card (can be `None`).
    * @return A new instance of `Card` with the updated tag.
    */
  def updateTag(newTag: Option[String]): Card =
    copy(tag = newTag)

  /** Updates the start date of the card.
    *
    * @param newStartDate The new start date for the card (can be `None`).
    * @return A new instance of `Card` with the updated start date.
    */
  def updateStartDate(newStartDate: Option[LocalDate]): Card =
    copy(startDate = newStartDate)

  /** Updates the end date of the card.
    *
    * @param newEndDate The new end date for the card (can be `None`).
    * @return A new instance of `Card` with the updated end date.
    */
  def updateEndDate(newEndDate: Option[LocalDate]): Card =
    copy(endDate = newEndDate)

  /** Updates the description of the card.
    *
    * @param newDescription The new description for the card (can be `None`).
    * @return A new instance of `Card` with the updated description.
    */
  def updateDescription(newDescription: Option[String]): Card =
    copy(description = newDescription)

  /** Adds a [[Task]] to the card's checklist.
    *
    * @param task The [[Task]] to be added to the checklist.
    * @return A new instance of `Card` with the updated checklist.
    */
  def addTask(task: Task): Card =
    copy(checklist = checklist :+ task)

  /** Removes a [[Task]] from the card's checklist.
    *
    * @param task The [[Task]] to be removed from the checklist.
    * @return A new instance of `Card` with the updated checklist.
    */
  def removeTask(task: Task): Card =
    copy(checklist = checklist.filterNot(_ == task))

  /** Updates an existing [[Task]] in the checklist.
    *
    * @param oldTask The [[Task]] to be replaced.
    * @param newTask The new [[Task]] to replace the old one.
    * @return A new instance of `Card` with the updated checklist.
    */
  def updateTask(oldTask: Task, newTask: Task): Card =
    copy(checklist = checklist.map(task => if (task == oldTask) newTask else task))

  /** Calculates the progress of the checklist as a ratio of completed tasks.
    *
    * @return The progress as a `Double` (0.0 to 1.0).
    */
  def calculateChecklistProgress(): Double =
    if checklist.isEmpty then 0d
    else checklist.count(_.isCompleted).toDouble / checklist.length
