import java.time.LocalDate
import logic._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class CardTest extends AnyFlatSpec with Matchers:

  "Card" should "return 0.0 when using checklistProgression and the checklist is empty " in:
    val card = new Card("Card")
    card.checklistProgress() shouldEqual 0.0

  it should "return 0.33 when using checklistProgression and 1/3 of items are completed" in:
    val card = new Card()
    for _ <- (1 to 3) do card.addChecklistItem()
    card.checklist.head.setCompleted(true)
    card.checklistProgress() shouldEqual 1.0 / 3.0

  it should "return 1.0 when using checklistProgression and all items" +
    " are completed" in:
    val card = new Card("Card")
    for _ <- (1 to 3) do
      val item = card.addChecklistItem()
      item.setCompleted(true)
    card.checklistProgress() shouldEqual 1.0

end CardTest
