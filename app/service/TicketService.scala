package service

import play.api.Play.current
import play.api.db.slick.DB

import model._

object TicketService {
  // service methods

  def getTicket(id: Int): Option[Ticket] =
    DB.withSession { implicit session =>
      Tickets.get(id)
    }

  def getComments(ticketId: Int): List[Comment] =
    DB.withSession { implicit session =>
      Comments.getForTicket(ticketId)
    }

  private def canChangeTicket(ticket: Ticket): Boolean = {
    !ticket.status.equals("Closed")
  }

  private def canChangeTicket(id: Int): Boolean = {
    getTicket(id).map(canChangeTicket(_)).getOrElse(false)
  }

  def addComment(ticketId: Int, comment: Comment): Option[Comment] =
    DB.withSession { implicit session =>
      if (canChangeTicket(ticketId)) {
        val id = Comments.insert(comment)
        Comments.get(id)
      } else {
        None
      }
    }

  def allTickets(): List[Ticket] = DB.withSession { implicit session =>
    Tickets.list()
  }

  def addTicket(title: String, area: String, customerId: Int): Option[Ticket] = {
    DB.withSession { implicit session =>
      val customerOpt = Customers.get(customerId)
      customerOpt.flatMap { customer =>
        val ticket = Ticket(None, title, area, "New", customer.id.get, None)
        val id = Tickets.insert(ticket)
        Tickets.get(id)
      }
    }
  }

  val allowedCurrentStatus = Set("New", "Open")
  val allowedChangeStatus = Set("New", "Open", "Closed")

  def reassignTicket(ticketId: Int, csrId: Int) = {
    DB.withSession { implicit session =>
      if (canChangeTicket(ticketId)) {
        Tickets.updateAssignee(ticketId, csrId)
      } else {
        None
      }
    }
  }

  def changeStatus(ticketId: Int, newStatus: String) = DB.withSession {
    implicit session =>
      getTicket(ticketId).flatMap {
        ticket =>
          if (canChangeTicket(ticket)
            && allowedCurrentStatus.contains(ticket.status)
            && allowedChangeStatus.contains(newStatus)) {
            // update the status
            Tickets.updateStatus(ticketId, newStatus);
          } else {
            None
          }
      }
  }

}