package utils.json.scalafx

import io.circe.{Decoder, Encoder}
import scalafx.beans.property.BooleanProperty

object BooleanPropertyCodec:

  implicit val booleanPropertyEncoder: Encoder[BooleanProperty] =
    Encoder.encodeBoolean.contramap[BooleanProperty](_.value)

  implicit val booleanPropertyDecoder: Decoder[BooleanProperty] =
    Decoder.decodeBoolean.map(BooleanProperty(_))
