package controllers

import play.api.mvc._
import play.api.libs.json.Json
import play.api.libs.json.JsValue
import utils.JsonWriters._
import utils.JsonReaders._
import service.TicketService
import service.CustomerService
import model.Customer

object CustomerController extends Controller {

  def list() = Action {
    val customers = CustomerService.list
    Ok(Json.toJson(customers))
  }

  def get(id: Int) = Action {
    val customer = CustomerService.get(id)
    Ok(Json.toJson(customer))
  }

  def add() = Action { request =>
    val data = request.body.asJson
    data.map { json =>
      val customer = json.as[Customer]

      CustomerService.add(customer) match {
        case Some(c) =>
          Ok(Json.toJson(c))
        case None =>
          NotFound("")
      }
    }.getOrElse {
      NotFound("")
    }
  }

  def update(id: Int) = Action {
    Ok("")
  }
}
