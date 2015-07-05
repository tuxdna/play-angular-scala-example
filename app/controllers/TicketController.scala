package controllers

import play.api.mvc._
import play.api.libs.json.Json
import play.api.libs.json.JsValue
import utils.JsonWriters._
import service.TicketService

object TicketController extends Controller {

  def list() = Action {
    val tickets = TicketService.allTickets
    Ok(Json.toJson(tickets))
  }

  def get(id: Int) = Action {
    val ticket = TicketService.getTicket(id)
    Ok(Json.toJson(ticket))
  }

  def add() = Action {
    Ok("")
  }

  def addComment(id: Int) = Action {
    Ok("")
  }

  def reassign(id: Int, csrId: Int) = Action {
    Ok("")
  }

}
