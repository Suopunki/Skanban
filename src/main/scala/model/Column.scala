package model

/** Represents a Column on the Kanban board, which contains multiple cards.
  *
  * @param title The title of the column (default is "new Column").
  * @param cards A vector of cards that belong to this column(defaults to an empty vector).
  * @param _unfilteredCards An internal vector of cards without any filters applied (defaults to an
  *   empty vector). This should not be accessed directly.
  */
case class Column(
    title: String = "new Column",
    cards: Vector[Card] = Vector.empty,
    _unfilteredCards: Vector[Card] = Vector.empty
):

  /** Adds a new card to the column.
    *
    * @return A new instance of `Column` with the added card.
    */
  def addCard(): Column =
    copy(cards = cards :+ Card())

  /** Moves an existing card to a new position in the column.
    *
    * @param card The card to be moved.
    * @param newIndex The new index where the card should be placed.
    * @return A new instance of `Column` with the card moved.
    */
  def moveCard(card: Card, newIndex: Int): Column =
    copy(cards = cards.filterNot(_ == card).patch(newIndex, Seq(card), 0))

  /** Removes a card from the column.
    *
    * @param card The card to be removed.
    * @return A new instance of `Column` with the card removed.
    */
  def removeCard(card: Card): Column =
    copy(cards = cards.filterNot(_ == card))

  /** Sorts the cards in the column by their end date, prioritizing cards with an end date.
    *
    * @return A new instance of `Column` with the cards sorted by their end date.
    */
  def sortCardsByEndDate(): Column =
    val (withEndDates, withoutEndDates) = cards.partition(_.endDate.nonEmpty)
    val sortedWithEndDates = withEndDates.sortBy(_.endDate.get)
    copy(cards = sortedWithEndDates ++ withoutEndDates)

  /** Filters the cards in the column by a specific tag.
    *
    * @param tag The tag by which to filter the cards.
    * @return A new instance of `Column` with the cards filtered by the specified tag.
    */
  def filterCardsByTag(tag: String): Column =
    val unfilteredCards = if _unfilteredCards.isEmpty then cards else _unfilteredCards
    copy(cards = unfilteredCards.filter(_.tag.exists(_ == tag)), _unfilteredCards = unfilteredCards)

  /** Removes any applied filters on the cards.
    *
    * @return A new instance of `Column` with the original (unfiltered) cards.
    */
  def removeCardFilter(): Column =
    copy(cards = _unfilteredCards, _unfilteredCards = Vector.empty)
