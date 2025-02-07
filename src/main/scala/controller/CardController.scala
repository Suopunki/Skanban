package controller

import model.{Card, Task}
import scalafx.collections.ObservableBuffer.Add

import java.time.LocalDate

class CardController(val card: Card):

  // Constructor block for adding listeners to the card's checklist and its tasks to update the progress bar
  {
    // Add listeners to existing tasks
    card.checklist.foreach(addTaskCompletionListener)

    // Add listeners to new tasks, when added, and update checklist progress on all changes
    card.checklist.onChange((_, changes) =>
      changes.foreach {
        case Add(_, addedTasks) => addedTasks.foreach(addTaskCompletionListener)
        case _                  => ()
      }
      updateChecklistProgress()
    )

    def addTaskCompletionListener(task: Task): Unit =
      task.isCompleted.onChange((_, _, _) => updateChecklistProgress())

    def updateChecklistProgress(): Unit =
      card.checklistProgress.update(card.calculateChecklistProgress())
  }

  def updateTitle(newTitle: String): Unit =
    card.updateTitle(newTitle)

  def updateTag(newTag: String): Unit =
    card.updateTag(newTag)

  def removeTag(): Unit =
    card.removeTag()

  def updateStartDate(date: LocalDate): Unit =
    card.updateStartDate(date)

  def removeStartDate(): Unit =
    card.removeStartDate()

  def updateEndDate(date: LocalDate): Unit =
    card.updateEndDate(date)

  def removeEndDate(): Unit =
    card.removeEndDate()

  def updateDescription(description: String): Unit =
    card.updateDescription(description)

  def removeDescription(): Unit =
    card.removeDescription()

  def addNewTask(): Unit =
    card.addNewTask()

  def insertTask(index: Int, task: Task): Unit =
    card.insertTask(index, task)

  def removeTask(task: Task): Unit =
    card.removeTask(task)
