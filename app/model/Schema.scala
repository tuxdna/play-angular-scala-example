package model
import play.api.db.slick.DB
import play.api.Application
import scala.slick.jdbc.meta.MTable
import models._

// import play.api.db.slick.Config.driver.simple._
import scala.slick.driver.MySQLDriver.simple._

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