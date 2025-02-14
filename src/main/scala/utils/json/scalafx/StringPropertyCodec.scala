package utils.json.scalafx

import io.circe.{Decoder, Encoder}
import scalafx.beans.property.StringProperty

object StringPropertyCodec:

  implicit val stringPropertyEncoder: Encoder[StringProperty] =
    Encoder.encodeString.contramap[StringProperty](_.value)

  implicit val stringPropertyDecoder: Decoder[StringProperty] =
    Decoder.decodeString.map(StringProperty(_))
