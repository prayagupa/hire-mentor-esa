package controllers

import java.util.Date

import play.api.libs.json.JsNull
import play.api.libs.json.Json
import play.api.libs.json.Json.toJson
import play.api.mvc._
import service.TopicService

class RoutingController extends Controller {

//  implicit val jsonWrites = Json.writes[Map[String, Seq[Map[String, String]]]]

  val topicService : TopicService = new TopicService()

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

  def send = Action { request =>
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
    topicService.sendMessageToTopic(request.queryString.getOrElse("name", new Date().getSeconds).toString, "topic_artists")
    Ok(jsonObject)
  }
}
