package utils.json.scalafx

import io.circe.{Decoder, Encoder}
import scalafx.beans.property.IntegerProperty

object IntegerPropertyCodec:

  implicit val integerPropertyEncoder: Encoder[IntegerProperty] =
    Encoder.encodeInt.contramap[IntegerProperty](_.value)

  implicit val integerPropertyDecoder: Decoder[IntegerProperty] =
    Decoder.decodeInt.map(IntegerProperty(_))
