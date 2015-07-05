package service

import model._

object TicketService {

  val customer1 = Customer(1, "John", "2123", "john@mail.com")
  val customer2 = Customer(2, "Sam", "2122", "sam@mail.com")
  val csr1 = CustomerServiceRepresentative(1, "Michael")
  val csr2 = CustomerServiceRepresentative(2, "Enrique")

  val commentsList = List(
    Comment(1, 1, "Comment 1 for Ticket 1"),
    Comment(2, 1, "Comment 2 for Ticket 1"),
    Comment(3, 2, "Comment 3 for Ticket 2"),
    Comment(4, 3, "Comment 4 for Ticket 3"),
    Comment(5, 3, "Comment 5 for Ticket 3") //
    )

  val commentsMap: Map[Int, List[Comment]] = commentsList.groupBy(_.ticketId)

  private def getCommentsForTicket(ticketId: Int): List[Comment] = {
    commentsMap.getOrElse(ticketId, Nil)
  }

  val ticket1 = Ticket(1, customer1, getCommentsForTicket(1), "Phone not working", "networking", "New")
  val ticket2 = Ticket(2, customer1, getCommentsForTicket(2), "Payment stuck", "payment", "Closed")
  val ticket3 = Ticket(3, customer2, getCommentsForTicket(3), "Phone not working", "networking", "Open")
  val ticketsList = List(ticket1, ticket2, ticket3)
  val ticketsMap: Map[Int, Ticket] = ticketsList.map(t => t.id -> t).toMap

  // service methods
  def getTicket(id: Int): Option[Ticket] = { ticketsMap.get(id) }
  def allTickets(): List[Ticket] = { ticketsList }

}