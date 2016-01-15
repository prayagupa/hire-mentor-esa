package service.util

import java.io.{StringWriter, StringReader}

import com.mongodb.util.JSON
import org.codehaus.plexus.util.xml.XmlUtil
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
    var xml = "<?xml version=\"1.0\" ?>" +
      "<data att=\"dreams\">" +
        "<created>\n\n\n" +
            "<date>" + 28101989 + "</date>" +
        "</created>" +
        "" + "<type>" + "ContainerDownloadEvent" + "</type>" +
        "" + "<value attValue=\"valueAtt\">" +  1 + "</value>" +
      "</data>"

    val xmlAwesome =
      """
        |<root>
        |<items>
        |<item>
        |<name>blah</name>
        |</item>
        |<item>
        |<name>nhlah</name>
        |</item>
        |</items>
        |</root>
      """.stripMargin.replaceAll(">\\s+<", "><")


    val json : JSONObject = toJSONObject(xml)
    val newJson = jsonify(json, true)
    val json_ = JSON.parse(newJson.toString)
    // { "data" : { "type" : "ContainerDownloadEvent" , "value" : 28}}

    println("json \n" + json_)
    //println(json_)
    val reader = new StringReader(xml.trim());
    val writer = new StringWriter();
    val x = XmlUtil.prettyFormat(reader, writer);
    println(xml.replaceAll(">\\s+<", ""))
//    val xmlSerializer = new XMLSerializer
//    val jsonnn = xmlSerializer.read( xml )
//
//    println(jsonnn)

//    test
  }

  def test: Unit = {
    val testXML = "<MyXML><ID>123456789e1234</ID></MyXML>"
    val testJsonObject = org.json.XML.toJSONObject(testXML)
    println(testJsonObject.getJSONObject("MyXML").getString("ID"))
  }
}
