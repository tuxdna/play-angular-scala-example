package utils

import play.api.libs.json._
import play.api.libs.functional.syntax._
import java.sql.Timestamp
import model.Customer

object JsonReaders {
  //  implicit val jsonToClient: Reads[Client] = (
  //    (JsPath \ "id").read[String] and
  //    (JsPath \ "username").read[String] and
  //    (JsPath \ "secret").read[String] and
  //    (JsPath \ "description").read[String] and
  //    (JsPath \ "redirect_uri").read[String] and
  //    (JsPath \ "scope").read[String])(Client.apply _)

  //  implicit val jsonToClient: Reads[Customer] = (
  //      (JsPath \ "id").read[Int] and
  //      )

}

