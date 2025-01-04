package model

import java.time.LocalDate

import munit.FunSuite

class CardTest extends FunSuite:

  test("Card should have default values"):
    val card = Card()
    assertEquals(card.title, "New Card")
    assertEquals(card.tag, None)
    assertEquals(card.checklist.isEmpty, true)

  test("Card should update its title"):
    val card = Card().updateTitle("Updated Card")
    assertEquals(card.title, "Updated Card")

  test("Card should update its tag"):
    val card = Card().updateTag(Some("urgent"))
    assertEquals(card.tag, Some("urgent"))

  test("Card should update its start and end dates"):
    val startDate = Some(LocalDate.now())
    val endDate = Some(LocalDate.now().plusDays(1))
    val card = Card().updateStartDate(startDate).updateEndDate(endDate)
    assertEquals(card.startDate, startDate)
    assertEquals(card.endDate, endDate)

  test("Card should add a task to its checklist"):
    val task = Task("Task 1")
    val card = Card().addTask(task)
    assertEquals(card.checklist.size, 1)
    assertEquals(card.checklist.head.title, "Task 1")

  test("Card should remove a task from its checklist"):
    val task = Task("Task 1")
    val card = Card().addTask(task).removeTask(task)
    assertEquals(card.checklist.isEmpty, true)

  test("Card should update an existing task"):
    val task = Task("Task 1")
    val updatedTask = Task("Updated Task")
    val card = Card().addTask(task).updateTask(task, updatedTask)
    assertEquals(card.checklist.size, 1)
    assertEquals(card.checklist.head.title, "Updated Task")

  test("Card should calculate checklist progress"):
    val task1 = Task("Task 1", isCompleted = true)
    val task2 = Task("Task 2", isCompleted = false)
    val card = Card().addTask(task1).addTask(task2)
    assertEquals(card.calculateChecklistProgress(), 0.5)
