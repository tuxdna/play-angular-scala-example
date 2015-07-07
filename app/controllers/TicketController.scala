package controllers

import play.api.mvc._
import play.api.libs.json.Json
import play.api.libs.json.JsValue
import utils.JsonWriters._
import utils.JsonReaders._
import service.TicketService
import model.Comment

object TicketController extends Controller {

  def list() = Action {
    val tickets = TicketService.allTickets
    Ok(Json.toJson(tickets))
  }

  def get(id: Int) = Action {
    val ticket = TicketService.getTicket(id)
    Ok(Json.toJson(ticket))
  }

  def getComments(id: Int) = Action {
    val comments = TicketService.getComments(id)
    Ok(Json.toJson(comments))
  }

  def add() = Action { request =>
    val data = request.body.asJson
    data.map { json =>
      val m = json.as[Map[String, String]]
      val title = m("title")
      val area = m("area")
      val customerId = m("customerId").toInt

      val ticketOpt = TicketService.addTicket(title, area, customerId)
      ticketOpt.map { t =>
        Ok(Json.toJson(t))
      }.getOrElse {
        NotFound("")
      }
    }.getOrElse {
      NotFound("")
    }
  }

  def addComment(id: Int) = Action { request =>
    val data = request.body.asJson
    data.map { json =>
      val comment = json.as[Comment]
      TicketService.addComment(comment.ticketId, comment) match {
        case Some(c) =>
          Ok(Json.toJson(c))
        case None =>
          NotFound("")
      }
    }.getOrElse {
      NotFound("")
    }
  }

  def reassign(id: Int, csrId: Int) = Action { request =>
    val r = TicketService.reassignTicket(id, csrId)
    r match {
      case Some(ticket) => Ok(Json.toJson(ticket))
      case None => NotFound("")
    }
  }

  def changeStatus(id: Int, status: String) = Action { request =>
    val t = TicketService.changeStatus(id, status)
    t.map { ticket =>
      Ok(Json.toJson(ticket))
    }.getOrElse(NotFound(""))
  }

}
