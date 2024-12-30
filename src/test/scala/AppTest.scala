import logic._
import java.io.File
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class AppTest extends AnyFlatSpec with Matchers:

  "App" should "save to and then load from a save file" in:
    // Save the board
    val file = new File("./src/main/resources/saveFiles/test.json")
    val board = new Board()
    val card = board.columns.head.cards.head
    card.addChecklistItem()
    App.save(board, file)
    // Load the board
    val loadedBoard = App.load(file)
    // Change the ChecklistItem completion as an example
    val loadedCard = loadedBoard.columns.head.cards.head
    loadedCard.checklist.head.setCompleted(true)
    // Delete unnecessary file
    file.delete()
    // Check the cheklist progression status as an example
    loadedCard.checklistProgress() shouldEqual 1.0

end AppTest
