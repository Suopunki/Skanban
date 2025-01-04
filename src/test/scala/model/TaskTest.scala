package model

import munit.FunSuite

class TaskTest extends FunSuite:

  test("Task should have default values"):
    val task = Task()
    assertEquals(task.title, "New Task")
    assertEquals(task.isCompleted, false)

  test("Task should update its title"):
    val task = Task().updateTitle("Updated Task")
    assertEquals(task.title, "Updated Task")

  test("Task should toggle its completion state"):
    val task = Task(isCompleted = false)
    val toggledTask = task.toggleCompletion()
    assertEquals(toggledTask.isCompleted, true)

    val revertedTask = toggledTask.toggleCompletion()
    assertEquals(revertedTask.isCompleted, false)

  test("Task should set its completion state explicitly"):
    val task = Task().setCompletion(true)
    assertEquals(task.isCompleted, true)

    val revertedTask = task.setCompletion(false)
    assertEquals(revertedTask.isCompleted, false)
