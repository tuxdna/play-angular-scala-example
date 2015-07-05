package model

case class Customer(id: Int)
case class Ticket(id: Int, customer: Customer)


