package utils.json.scalafx

import io.circe.{Decoder, Encoder}
import scalafx.beans.property.*
import scalafx.collections.ObservableBuffer

object ObservableBufferCodec:

  implicit def observableBufferEncoder[A: Encoder]: Encoder[ObservableBuffer[A]] =
    Encoder.encodeSeq[A].contramap(_.toSeq)

  implicit def observableBufferDecoder[A: Decoder]: Decoder[ObservableBuffer[A]] =
    Decoder.decodeSeq[A].map(ObservableBuffer(_: _*))
