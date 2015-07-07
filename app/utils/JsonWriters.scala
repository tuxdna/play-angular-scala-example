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
        "text" -> x.text,
        "csrId" -> x.csrId)
    }
  }

  implicit val csrToJson = new Writes[CustomerServiceRepresentative] {
    def writes(x: CustomerServiceRepresentative): JsValue = {
      Json.obj(
        "id" -> x.id,
        "name" -> x.name)
    }
  }

  implicit val ticketToJson = new Writes[Ticket] {
    def writes(x: Ticket): JsValue = {
      Json.obj(
        "id" -> x.id,
        "customerId" -> x.customerId,
        "assignedTo" -> x.assignedTo,
        "title" -> x.title,
        "area" -> x.area,
        "status" -> x.status)
    }
  }

}