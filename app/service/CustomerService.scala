package service

import play.api.Play.current
import play.api.db.slick.DB

import model._

object CustomerService {

  val customer1 = Customer(Some(1), "John", "2123", "john@mail.com")
  val customer2 = Customer(Some(2), "Sam", "2122", "sam@mail.com")

  val customerList = List(customer1, customer2)
  val customerMap = customerList.map(x => x.id.get -> x).toMap

  // service methods
  //  def get(id: Int): Option[Customer] = { customerMap.get(id) }
  //  def list(): List[Customer] = { customerList }

  def get(id: Int): Option[Customer] = {
    DB.withSession { implicit session => Customers.get(id) }
  }

  def add(customer: Customer): Option[Customer] = {
    DB.withSession { implicit session =>
      val cid = Customers.insert(customer)
      Customers.get(cid)
    }
  }

  def list(): List[Customer] = {
    DB.withSession { implicit session => Customers.list() }
  }

}
