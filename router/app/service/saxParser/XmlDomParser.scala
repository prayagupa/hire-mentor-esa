package service.saxParser

import java.io.File
import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlElement, XmlRootElement}
import javax.xml.bind.{JAXBContext, Marshaller, Unmarshaller}

import scala.collection.mutable.ListBuffer

/**
 * Created by prayagupd
 * on 11/20/15.
 * http://stackoverflow.com/a/8187505/432903
 */

@XmlRootElement(name = "Artists")
@XmlAccessorType(XmlAccessType.FIELD)
class Artists {

  @XmlElement(name = "Artist")
  var artists : ListBuffer[Artist] = new ListBuffer[Artist]()
}

@XmlRootElement(name="Artist")
@XmlAccessorType(XmlAccessType.FIELD)
class Artist {

  @XmlElement(name="name")
  var name : String = ""

  @XmlElement(name="address")
  var address : String = ""

  @XmlElement(name="members")
  var s : Int = 0

  @XmlElement(name="type")
  var artistType : String = ""

//  @XmlElementWrapper(name="Album")
//  @XmlElement(name = "album")
//  var album : ListBuffer[String] = new ListBuffer[String]()

}

//@XmlRootElement(name="Album")
//@XmlAccessorType(XmlAccessType.FIELD)
//class Album {
//
//  @XmlElement(name="name")
//  var name : String = ""
//
////  @XmlElement(name="artists")
////  var artists : ListBuffer[Artist_] = new ListBuffer[Artist_]()
//
//}

object XmlDomParser {
  def main(args: Array[String]) {
    val jc : JAXBContext = JAXBContext.newInstance(classOf[Artists])
    val unmarshaller : Unmarshaller = jc.createUnmarshaller()
    val artists : Artists = unmarshaller.unmarshal(
      new File("/Users/prayagupd/prayag.data/workspace.programming/java8/hire-artists/router/app/service/Artist.xml")).asInstanceOf[Artists]


    val marshaller : Marshaller = jc.createMarshaller()
    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)
    marshaller.marshal(artists, System.out)
  }
}
