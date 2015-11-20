package service

import java.io.{IOException, File}
import javax.xml.parsers.{ParserConfigurationException, SAXParser, SAXParserFactory}

import jdk.internal.org.xml.sax.SAXException
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler

import scala.collection.mutable.{ListBuffer, ArrayBuffer}

/**
 * Created by prayagupd
 * on 11/19/15.
 */

class Artist(var name : String = "UNKNOWN",
             var artistType : String = "UNKNOWN",
             var address: String = "UNKNOWN",
             var members : Int = 0) {

}

class ArtistXmlParser extends DefaultHandler {
  var artists : ListBuffer[Artist] =  new ListBuffer[Artist]()

  var name_tag = "CLOSED"
  var type_tag = "CLOSED"
  var members_tag : String  = "CLOSED"
  var address_tag : String  = "CLOSED"

  var artist : Artist = new Artist()
  override def startElement(uri: String, localName: String, qName: String, attributes: Attributes): Unit = {
    if (qName.eq("Artist")) {

    } else if (qName.equalsIgnoreCase("name")) {
      name_tag = "OPEN"
    } else if (qName.equalsIgnoreCase("type")) {
      type_tag = "OPEN"
    } else if (qName.equalsIgnoreCase("members")) {
      members_tag = "OPEN"
    } else if (qName.equalsIgnoreCase("address")) {
      address_tag = "OPEN"
    }
  }

  override
  def characters(ch: Array[Char], start: Int, length: Int): Unit = {
    if (name_tag.equalsIgnoreCase("CLOSED")) {
      artist.name = ch.mkString.substring(start, start+length)
      name_tag = "OPEN"
    } else if (type_tag.equalsIgnoreCase("CLOSED")) {
      artist.artistType = ch.mkString.substring(start, start+length)
      type_tag = "OPEN"
    } else if (members_tag.equalsIgnoreCase("CLOSED")) {
      println(ch.mkString)
//      artist.members = ch.mkString.substring(start, start+length)
      members_tag = "OPEN"
    } else if (address_tag.equalsIgnoreCase("CLOSED")) {
      artist.address = ch.mkString.substring(start, start+length)
      address_tag = "OPEN"
    }
  }

  override def endElement(uri: String, localName: String, qName: String): Unit = {
    if ("Artist".eq(qName)) {
      artists+= artist
    }
  }
}


object Main {
  def main (args: Array[String]){
    println("Parsing")

    val saxParserFactory = SAXParserFactory.newInstance()
    try {
      val saxParser : SAXParser = saxParserFactory.newSAXParser()
      val parser = new ArtistXmlParser
      saxParser.parse(new File("/Users/prayagupd/prayag.data/workspace.programming/java8/hire-artists/router/app/service/Artist.xml"), parser)
      val artists_ : ListBuffer[Artist] = parser.artists

      println("After parsing")

      artists_.foreach( artist =>
        println(s"name => ${artist.name}")
      )
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }
}