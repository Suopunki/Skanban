package utils.json.scalafx

import io.circe.{Decoder, Encoder}
import scalafx.beans.property.DoubleProperty

object DoublePropertyCodec:

  implicit val doublePropertyEncoder: Encoder[DoubleProperty] =
    Encoder.encodeDouble.contramap[DoubleProperty](_.value)

  implicit val doublePropertyDecoder: Decoder[DoubleProperty] =
    Decoder.decodeDouble.map(DoubleProperty(_))
