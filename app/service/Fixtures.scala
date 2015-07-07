package service

import model._

object Fixtures {

  val customer1 = Customer(Some(1), "John", "2123", "john@mail.com")
  val customer2 = Customer(Some(2), "Sam", "2122", "sam@mail.com")

  val customerList = List(customer1, customer2)
  val customerMap = customerList.map(x => x.id -> x).toMap

  val csr1 = CustomerServiceRepresentative(Some(1), "Michael")
  val csr2 = CustomerServiceRepresentative(Some(2), "Enrique")

  val csrList = List(csr1, csr2)
  val csrMap = csrList.map(x => x.id -> x).toMap

  val commentsList = List(
    Comment(Some(1), 1, "Comment 1 for Ticket 1", 1),
    Comment(Some(2), 1, "Comment 2 for Ticket 1", 1),
    Comment(Some(3), 2, "Comment 3 for Ticket 2", 1),
    Comment(Some(4), 3, "Comment 4 for Ticket 3", 2),
    Comment(Some(5), 3, "Comment 5 for Ticket 3", 2) //
    )

  val commentsMap: Map[Int, List[Comment]] = commentsList.groupBy(_.ticketId)

  private def getCommentsForTicket(ticketId: Int): List[Comment] = {
    commentsMap.getOrElse(ticketId, Nil)
  }

  val ticket1 = Ticket(Some(1), "Phone not working", "networking", "New", customer1.id.get, None)
  val ticket2 = Ticket(Some(2), "Payment stuck", "payment", "Closed", customer1.id.get, None)
  val ticket3 = Ticket(Some(3), "Phone not working", "networking", "Open", customer2.id.get, None)
  val ticketsList = List(ticket1, ticket2, ticket3)
  val ticketsMap: Map[Int, Ticket] = ticketsList.map(t => t.id.get -> t).toMap

  import play.api.Play.current
  import play.api.db.slick.DB

  def loadFixtures() = DB.withSession { implicit session =>
    customerList.foreach(c => Customers.insert(c))
    csrList.foreach(c => CustomerServiceRepresentatives.insert(c))
    ticketsList.foreach(t => Tickets.insert(t))
    commentsList.foreach(c => Comments.insert(c))
  }

}