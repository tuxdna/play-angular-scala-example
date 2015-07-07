package controllers

import play.api.mvc._
import play.api.libs.json.Json
import play.api.libs.json.JsValue
import utils.JsonWriters._
import service.TicketService
import service.CustomerService
import model.CustomerServiceRepresentatives
import service.CSRService

object CSRController extends Controller {

  def list() = Action {
    val csrs = CSRService.list()
    Ok(Json.toJson(csrs))
  }

  def get(id: Int) = Action {
    val customer = CSRService.get(id)
    Ok(Json.toJson(customer))
  }

  def add() = Action {
    Ok("")
  }

  def update(id: Int) = Action {
    Ok("")
  }
}
