package domain

/**
 * Created by prayagupd
 * on 11/21/15.
 */
class MobileAdUser {
  var id: String = ""
  var firstName: String = ""
  var lastName: String = ""
  var location: String = ""

  override def toString: String = {
    return firstName + " " + lastName + "(" + id + ")" + location
  }
}