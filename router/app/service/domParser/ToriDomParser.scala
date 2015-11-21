package service.domParser

import java.util
import javax.xml.parsers.{DocumentBuilder, DocumentBuilderFactory, ParserConfigurationException}

import domain.MobileAdUser
import org.w3c.dom.{Document, Element, Node, NodeList}

/**
 * Created by prayagupd
 * on 11/20/15.
 */

trait Parser {
  def parse(fileName : String) : AdUserMessage
}

class AdUserMessage {
  var header : String = ""
  var users : util.List[MobileAdUser] = new util.ArrayList[MobileAdUser]()
}

class ToriDomParser extends Parser {

  @throws(classOf[Exception])
  def parse(filename: String): AdUserMessage = {
    val document: Document = getDocumentBuilder.parse(filename)
    val adMessage = new AdUserMessage
    val users: util.List[MobileAdUser] = new util.ArrayList[MobileAdUser]
    val nodeList: NodeList = document.getDocumentElement.getChildNodes

    var user: MobileAdUser = null
    for(i <- 0 until nodeList.getLength) {
          val node: Node = nodeList.item(i)
          if (node.isInstanceOf[Element]) {

            if ((node.asInstanceOf[Element]).getTagName == "header") {
              adMessage.header = buildAdHeader(node)
            }
            if ((node.asInstanceOf[Element]).getTagName == "userStart") {
              user = buildMobileAdUser(node)
            }
            if ((node.asInstanceOf[Element]).getTagName == "userEnd") {
              users.add(user)
            }
          }
      }
    adMessage.users = users
    adMessage
  }

  @throws(classOf[ParserConfigurationException])
  private def getDocumentBuilder: DocumentBuilder = {
    val factory: DocumentBuilderFactory = DocumentBuilderFactory.newInstance
    factory.newDocumentBuilder
  }

  private def buildAdHeader(node: Node): String = {
    val childNodes: NodeList = node.getChildNodes
    var header = ""
    for (i <- 0 until(childNodes.getLength)) {
      val childNode: Node = childNodes.item(i)
      if (childNode.isInstanceOf[Element]) {
        val content: String = childNode.getLastChild.getTextContent.trim
        childNode.getNodeName match {
          case "source" =>
            header = content
        }
      }
    }
    header
  }

  private def buildMobileAdUser(node: Node): MobileAdUser = {
    val user: MobileAdUser = new MobileAdUser
    user.id = node.getAttributes.getNamedItem("id").getNodeValue
    val childNodes: NodeList = node.getChildNodes

    for (i <- 0 until(childNodes.getLength)) {
          val childNode: Node = childNodes.item(i)
          if (childNode.isInstanceOf[Element]) {
            val content: String = childNode.getLastChild.getTextContent.trim
            childNode.getNodeName match {
              case "firstName" =>
                user.firstName = content
              case "lastName" =>
                user.lastName = content
              case "location" =>
                user.location = content
            }
          }
    }
    user
  }
}

object ToriDomParser {
  def main(args: Array[String]) {

    new ToriDomParser().parse("/Users/prayagupd/prayag.data/workspace.programming/java8/hire-artists/router/app/service/documentParser/SuperWeird.xml")

  }
}
