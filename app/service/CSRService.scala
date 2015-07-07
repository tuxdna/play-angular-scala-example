package service

import play.api.Play.current
import play.api.db.slick.DB

import model._

object CSRService {

  def get(id: Int): Option[CustomerServiceRepresentative] =
    DB.withSession { implicit session =>
      CustomerServiceRepresentatives.get(id)
    }

  def list(): List[CustomerServiceRepresentative] =
    DB.withSession { implicit session =>
      CustomerServiceRepresentatives.list()
    }

}