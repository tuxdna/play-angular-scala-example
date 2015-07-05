package utils

import model._
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import play.api.libs.json.Writes

object JsonWriters {

  implicit val customerToJson = new Writes[Customer] {
    def writes(x: Customer): JsValue = {
      Json.obj(
        "id" -> x.id,
        "name" -> x.name,
        "phone" -> x.phone,
        "email" -> x.email)
    }
  }

  implicit val commentToJson = new Writes[Comment] {
    def writes(x: Comment): JsValue = {
      Json.obj(
        "id" -> x.id,
        "ticketId" -> x.ticketId,
        "text" -> x.text)
    }
  }

  implicit val ticketToJson = new Writes[Ticket] {
    def writes(x: Ticket): JsValue = {
      Json.obj(
        "id" -> x.id,
        "customer" -> x.customer,
        "comments" -> x.comments,
        "title" -> x.title,
        "area" -> x.area,
        "status" -> x.status)
    }
  }

}