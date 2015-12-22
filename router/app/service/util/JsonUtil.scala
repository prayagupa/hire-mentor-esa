package service.util

import com.mongodb.util.JSON
import org.json.JSONObject
import org.json.XML.toJSONObject

import scala.collection.JavaConversions._

/**
 * Created by prayagupd on 12/17/15.
 * http://stackoverflow.com/a/5114170/432903
 */
object JsonUtil {

  def jsonify(json : Object, stringOnly: Boolean): Object = {
    jsonify(json)
  }

  def jsonify(json: Object) : Object = {
    if(json.isInstanceOf[JSONObject]) {
      val newJson: JSONObject = new JSONObject()
      val jsonObj = json.asInstanceOf[JSONObject]
      jsonObj.keys().toIterator.toList.foreach( key => {
        newJson.accumulate(key, jsonify(jsonObj.get(key)))
      })
      return newJson
    }
    json.toString
  }

  def main(args: Array[String]) {
//    val number = 0001
    val xml = "<?xml version=\"1.0\" ?>" +
      "<data att=\"dreams\">" +
        "<created>" +
            "<date>" + 28101989 + "</date>" +
        "</created>" +
        "" + "<type>" + "ContainerDownloadEvent" + "</type>" +
        "" + "<value>" +  1 + "</value>" +
      "</data>" +
      "<id>" + 1989 + "</id>" +
      ""

    val json : JSONObject = toJSONObject(xml)
    val newJson = jsonify(json, true)
    val json_ = JSON.parse(newJson.toString)
    // { "data" : { "type" : "ContainerDownloadEvent" , "value" : 28}}

    println(json_)
    //println(json_)

//    val xmlSerializer = new XMLSerializer
//    val jsonnn = xmlSerializer.read( xml )
//
//    println(jsonnn)

    val testXML = "<MyXML><ID>123456789e1234</ID></MyXML>"
    val testJsonObject = org.json.XML.toJSONObject(testXML)
    println(testJsonObject.getJSONObject("MyXML").getString("ID"))
  }
}
