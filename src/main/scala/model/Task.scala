package model

/** Represents a Task within a Card's checklist.
  *
  * @param title The title of the task (default is "New Task").
  * @param isCompleted A flag indicating whether the task is completed (default is false).
  */
case class Task(title: String = "New Task", isCompleted: Boolean = false):

  /** Updates the title of the task.
    *
    * @param newTitle The new title for the task.
    * @return A new instance of `Task` with the updated title.
    */
  def updateTitle(newTitle: String): Task =
    copy(title = newTitle)

  /** Toggles the completion state of the task.
    *
    * This method switches the task's `isCompleted` state between `true` and `false`. If the task is
    * currently marked as completed, it will be marked as incomplete, and vice versa.
    *
    * @return A new instance of `Task` with the updated completion state.
    */
  def toggleCompletion(): Task =
    copy(isCompleted = !isCompleted)

  /** Sets the completion state of the task to a specific value.
    *
    * This method allows you to explicitly set the task's `isCompleted` state to either `true` or
    * `false`, depending on the value provided by the `completed` parameter.
    *
    * @param completed A boolean indicating whether the task is completed (`true`) or not (`false`).
    * @return A new instance of `Task` with the specified completion state.
    */
  def setCompletion(completed: Boolean): Task =
    copy(isCompleted = completed)
