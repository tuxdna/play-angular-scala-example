package model

case class Customer(
  id: Int,
  name: String,
  phone: String,
  email: String)

case class CustomerServiceRepresentative(
  id: Int,
  name: String)

case class Comment(
  id: Int,
  ticketId: Int,
  text: String)

case class Ticket(
  id: Int,
  customer: Customer,
  comments: List[Comment],
  title: String,
  area: String,
  status: String)
  
  


