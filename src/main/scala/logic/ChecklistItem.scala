package logic

import io.circe._
import io.circe.generic.semiauto._

case class ChecklistItem(
    var title: String = "New Task",
    var completed: Boolean = false
):

  def setTitle(title: String): Unit = this.title = title

  def setCompleted(completed: Boolean): Unit = this.completed = completed

end ChecklistItem

// JSON:
implicit val checklistItemEncoder: Encoder[ChecklistItem] = deriveEncoder
implicit val checklistItemDecoder: Decoder[ChecklistItem] = deriveDecoder
