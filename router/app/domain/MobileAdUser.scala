package domain

import net.liftweb.json.DefaultFormats

import scala.collection.mutable.ListBuffer

/**
 * Created by prayagupd
 * on 11/21/15.
 */

class MobileAdUser {
  implicit val formats = DefaultFormats
  var id: String = ""
  var firstName: String = ""
  var lastName: String = ""
  var location: String = ""
  var games : ListBuffer[String] = new ListBuffer[String]

  override def toString: String = {
    firstName + " " + lastName + "(" + id + ")" + location
  }
}