package view.components

import model.{Card, Column}

import java.time.format.DateTimeFormatter
import java.time.LocalDate
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, Label, Menu, MenuBar, MenuItem}
import scalafx.scene.layout.{HBox, VBox}

private val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

class MainContainer extends VBox:
  stylesheets = Seq("styles/styles.css")
  styleClass = Seq("main-container")

/* ---------- MenuScene: ---------- */

class MenuContainer extends VBox:
  alignment = Pos.Center
  spacing = 8

class KanbafyTitle(text: String) extends Label(text):
  styleClass = Seq("kanbafy-title")

private class StyledButton(text: String) extends Button(text):
  styleClass = Seq("styled-button")
  minWidth = 200

class MenuButton(text: String) extends StyledButton(text):
  styleClass += "menu-button"

class QuitButton(text: String) extends StyledButton(text):
  styleClass += "quit-button"

/* ---------- BoardScene: ---------- */

class BoardMenuBar extends MenuBar:
  styleClass = Seq("board-menu-bar")

class BoardMenu(label: String) extends Menu(label)

class BoardMenuItem(text: String) extends MenuItem(text)

class BoardTitle(text: String) extends Label(text):
  styleClass = Seq("board-title")

class ColumnContainer extends HBox:
  alignment = Pos.Center
  margin = Insets(0, 15, 10, 15)
  spacing = 15

class GuiColumn(column: Column) extends VBox:
  styleClass = Seq("column")
  id = java.util.UUID.randomUUID.toString

  def getColumn: Column = column

class ColumnTitle(text: String) extends Label(text):
  styleClass = Seq("column-title")

class GuiCard(card: Card) extends VBox:
  id = java.util.UUID.randomUUID.toString
  styleClass = Seq("gui-card")
  alignmentInParent = Pos.TopCenter
  margin = Insets(5)

  def getCard: Card = card

class GuiCardTitle(text: String) extends Label(text):
  styleClass = Seq("gui-card-title")

class GuiCardTag(tag: Option[String]) extends Label:
  text = tag match
    case Some(tag) => "Tag: " + tag
    case None      => ""

class GuiCardStartDate(sd: Option[LocalDate]) extends Label:
  text = sd match
    case Some(startDate) => "Start Date: " + startDate.format(dateFormatter)
    case None            => ""

class GuiCardDeadline(dl: Option[LocalDate]) extends Label:
  text = dl match
    case Some(deadline) => "Deadline: " + deadline.format(dateFormatter)
    case None           => ""

class GuiCardDescription(description: Option[String]) extends Label:
  text = description match
    case Some(description) => "Description:\n" + description
    case None              => ""
  alignment = Pos.Center
  margin = Insets(5, 5, 5, 5)
  wrapText = true
