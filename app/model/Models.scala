package model

import scala.slick.lifted.Tag
import java.sql.Timestamp

import play.api.db.slick.Config.driver.simple._

// import scala.slick.driver.MySQLDriver.simple._

case class Customer(
  id: Option[Int],
  name: String,
  phone: String,
  email: String)

class Customers(tag: Tag) extends Table[Customer](tag, "customers") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc, O.NotNull)
  def name = column[String]("name", O.NotNull)
  def phone = column[String]("phone", O.NotNull)
  def email = column[String]("email", O.NotNull)
  def * = (id.?,
    name,
    phone,
    email) <> (Customer.tupled, Customer.unapply _)
}

object Customers {
  val customers = TableQuery[Customers]

  def autoInc = customers returning customers.map(_.id)

  def insert(c: Customer)(implicit session: Session) = {
    c.id match {
      case None => autoInc.insert(c)
      case Some(x) => { customers += c; x }
    }
  }

  def get(id: Int)(implicit session: Session): Option[Customer] = {
    customers.where(_.id === id).firstOption
  }

  def list()(implicit session: Session): List[Customer] = {
    customers.list
  }

}

case class CustomerServiceRepresentative(
  id: Option[Int],
  name: String)

class CustomerServiceRepresentatives(tag: Tag) extends Table[CustomerServiceRepresentative](tag, "csrs") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc, O.NotNull)
  def name = column[String]("name", O.NotNull)
  def * = (id.?, name) <> (CustomerServiceRepresentative.tupled, CustomerServiceRepresentative.unapply _)
}

object CustomerServiceRepresentatives {
  val csrs = TableQuery[CustomerServiceRepresentatives]

  def autoInc = csrs returning csrs.map(_.id)

  def insert(c: CustomerServiceRepresentative)(implicit session: Session) = {
    c.id match {
      case None => autoInc.insert(c)
      case Some(x) => { csrs += c; x }
    }
  }

  def get(id: Int)(implicit session: Session): Option[CustomerServiceRepresentative] = {
    csrs.where(_.id === id).firstOption
  }

  def list()(implicit session: Session): List[CustomerServiceRepresentative] = {
    csrs.list
  }

}

case class Comment(
  id: Option[Int],
  ticketId: Int,
  text: String,
  csrId: Int)

class Comments(tag: Tag) extends Table[Comment](tag, "comments") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc, O.NotNull)
  def ticketId = column[Int]("ticket_id", O.NotNull)
  def text = column[String]("text", O.NotNull)
  def csrId = column[Int]("csr_id", O.NotNull)
  def * = (id.?, ticketId, text, csrId) <> (Comment.tupled, Comment.unapply _)
}

object Comments {
  val comments = TableQuery[Comments]

  def autoInc = comments returning comments.map(_.id)

  def insert(c: Comment)(implicit session: Session) = {
    c.id match {
      case None => autoInc.insert(c)
      case Some(x) => { comments += c; x }
    }
  }

  def get(id: Int)(implicit session: Session): Option[Comment] = {
    comments.where(_.id === id).firstOption
  }

  def getForTicket(id: Int)(implicit session: Session): List[Comment] = {
    comments.where(_.ticketId === id).list
  }

  def list()(implicit session: Session): List[Comment] = {
    comments.list
  }
}

case class Ticket(
  id: Option[Int],
  title: String,
  area: String,
  status: String,
  customerId: Int,
  assignedTo: Option[Int])

class Tickets(tag: Tag) extends Table[Ticket](tag, "tickets") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc, O.NotNull)
  def title = column[String]("title", O.NotNull)
  def area = column[String]("area", O.NotNull)
  def status = column[String]("status", O.NotNull)
  def customerId = column[Int]("customer_id", O.NotNull)
  def assignedTo = column[Int]("assigned_to", O.Nullable)
  def * = (id.?, title, area, status, customerId, assignedTo.?) <> (Ticket.tupled, Ticket.unapply _)
}

object Tickets {
  val tickets = TableQuery[Tickets]

  def autoInc = tickets returning tickets.map(_.id)

  def insert(t: Ticket)(implicit session: Session) = {
    t.id match {
      case None => autoInc.insert(t)
      case Some(x) => { tickets += t; x }
    }
  }

  def get(id: Int)(implicit session: Session): Option[Ticket] = {
    tickets.where(_.id === id).firstOption
  }

  def list()(implicit session: Session): List[Ticket] = {
    tickets.list
  }

  def updateAssignee(ticketId: Int, csrId: Int)(implicit session: Session): Option[Ticket] = {
    tickets
      .filter(t => t.id === ticketId)
      .map(_.assignedTo)
      .update(csrId)

    val tk = get(ticketId)
    tk
  }

  def updateStatus(ticketId: Int, status: String)(implicit session: Session): Option[Ticket] = {
    tickets
      .filter(t => t.id === ticketId)
      .map(_.status)
      .update(status)

    val tk = get(ticketId)
    tk
  }

}

import play.api.db.slick.DB
import play.api.Application
//import scala.slick.jdbc.meta.MTable
//
//// import play.api.db.slick.Config.driver.simple._
//import scala.slick.driver.MySQLDriver.simple._

object Schema {
  val tickets = TableQuery[Tickets]
  val customers = TableQuery[Customers]
  val csrs = TableQuery[CustomerServiceRepresentatives]
  val comments = TableQuery[Comments]
  
  val ddl = (customers.ddl ++
    comments.ddl ++
    csrs.ddl ++
    tickets.ddl)

  def generateSchema(implicit app: Application) {
    println("Populate the application schema...")

    DB.withSession { session =>

      println("-- Create tables SQL ...")
      for (sql <- Schema.ddl.createStatements) {
        println(s"${sql};")
      }

      println("-- Drop tables SQL...")
      for (sql <- Schema.ddl.dropStatements.toList.reverse) {
        println(s"${sql};")
      }

    }
  }

  def populateSchema(implicit app: Application) {
    println("Populate the application schema...")

    DB.withSession { session =>

      println("Create tables...")
      for (s <- Schema.ddl.createStatements) {
        val conn = session.conn
        val stmt = conn.createStatement()
        stmt.execute(s)
        stmt.close()
      }

    }
  }

  def hasGoldData(implicit app: Application) = {
    try {
      DB.withSession { implicit session =>
        val tickets = Tickets.list
        tickets.length > 0
      }
    } catch {
      case e: Throwable => false
    }
  }

}