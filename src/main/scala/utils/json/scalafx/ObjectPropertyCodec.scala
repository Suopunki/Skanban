package utils.json.scalafx

import io.circe.{Decoder, Encoder}
import scalafx.beans.property.ObjectProperty

object ObjectPropertyCodec:

  implicit def objectPropertyEncoder[T: Encoder]: Encoder[ObjectProperty[T]] =
    Encoder[T].contramap(_.value)

  implicit def objectPropertyDecoder[T: Decoder]: Decoder[ObjectProperty[T]] =
    Decoder[T].map(ObjectProperty(_))
