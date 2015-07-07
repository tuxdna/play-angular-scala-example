package utils

import play.api.libs.json._
import play.api.libs.functional.syntax._
import java.sql.Timestamp
import model.Customer
import model.Comment

object JsonReaders {
  implicit val jsonToClient: Reads[Customer] = (
    (JsPath \ "id").read[Option[Int]].orElse(Reads.pure(None)) and
    (JsPath \ "name").read[String] and
    (JsPath \ "phone").read[String] and
    (JsPath \ "email").read[String])(Customer.apply _)

  implicit val jsonToComment: Reads[Comment] = (
    (JsPath \ "id").read[Option[Int]].orElse(Reads.pure(None)) and
    (JsPath \ "ticketId").read[Int] and
    (JsPath \ "text").read[String] and
    (JsPath \ "csrId").read[Int])(Comment.apply _)
}

