package model

/** Represents a Kanban Board that contains multiple [[Column]]s.
  *
  * @param title The title of the board (default is "New Board").
  * @param columns A vector of [[Column]]s on the board.
  * @param archive A vector of archived cards.
  */
case class Board(
    title: String = "New Board",
    columns: Vector[Column] = Vector(
      Column("To do", Vector(Card())),
      Column("In Progress"),
      Column("Done")
    ),
    archive: Vector[Card] = Vector.empty
):

  /** Adds a new default [[Column]] to the board.
    *
    * @return A new instance of `Board` with the added column.
    */
  def addColumn(): Board =
    copy(columns = columns :+ Column())

  /** Moves an existing [[Column]] to a new position on the board.
    *
    * @param column The [[Column]] to be moved.
    * @param newIndex The new index for the column.
    * @return A new instance of `Board` with the column moved.
    */
  def moveColumnToIndex(column: Column, newIndex: Int): Board =
    val updatedColumns = columns.filterNot(_ == column).patch(newIndex, Seq(column), 0)
    copy(columns = updatedColumns)

  /** Removes a [[Column]] from the board.
    *
    * @param column The [[Column]] to be removed.
    * @return A new instance of `Board` with the column removed.
    */
  def removeColumn(column: Column): Board =
    copy(columns = columns.filterNot(_ == column))

  /** Adds a new default [[Card]] to the first [[Column]] of the board (usually the "To do" column).
    *
    * @return A new instance of `Board` with the added card.
    */
  def addCard(): Board =
    copy(columns = columns.headOption match
      case Some(c) => columns.updated(0, c.copy(cards = c.cards :+ Card()))
      case None    => columns
    )

  /** Sorts the [[Card]]s in all [[Column]]s by their end date.
    *
    * @return A new instance of `Board` with the cards sorted by end date.
    */
  def sortCardsByEndDate(): Board =
    copy(columns = columns.map(_.sortCardsByEndDate()))

  /** Filters the [[Card]]s in all [[Column]]s by a specific tag.
    *
    * @param tag The tag by which to filter the cards.
    * @return A new instance of `Board` with the cards filtered by the specified tag.
    */
  def filterCardsByTag(tag: String): Board =
    copy(columns = columns.map(_.filterCardsByTag(tag)))

  /** Removes any applied filters on the [[Card]]s in all [[Column]]s.
    *
    * @return A new instance of `Board` with the filters removed.
    */
  def removeCardFilter(): Board =
    copy(columns = columns.map(_.removeCardFilter()))
