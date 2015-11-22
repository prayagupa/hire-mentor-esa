package controllers

import play.api.libs.json.JsNull
import play.api.libs.json.Json
import play.api.libs.json.Json.toJson
import play.api.mvc._

class RoutingController extends Controller {

//  implicit val jsonWrites = Json.writes[Map[String, Seq[Map[String, String]]]]

  def index = Action {
    val ryan = toJson(
      Map(
        "name" -> toJson("Ryan Newmoon"),
        "age" -> toJson(31),
        "email" -> toJson("newmoon@gmail.com")
      )
    )
    val robert = toJson(
      Map(
        "name" -> toJson("Robert"),
        "age" -> toJson(25),
        "email" -> JsNull
      )
    )
    val jsonObject = Json.toJson(
      Map(
        "users" -> Seq( ryan, robert )
      )
    )
    Ok(ryan)
  }

  def on = Action {
    val jsonObject = toJson(
      Map(
        "users" -> Seq(
          Json.toJson(
            Map(
              "name" -> toJson("Bob"),
              "age" -> toJson(31),
              "email" -> toJson("bob@gmail.com")
            )
          ),
          toJson(
            Map(
              "name" -> toJson("Kiki"),
              "age" -> toJson(25),
              "email" -> JsNull
            )
          )
        )
      )
    )
    Ok(jsonObject)
  }
}
