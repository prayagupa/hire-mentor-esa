package service.domParser

import java.util
import javax.xml.parsers.{DocumentBuilder, DocumentBuilderFactory, ParserConfigurationException}
import scala.collection._
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
  var header : scala.collection.mutable.Map[String, String] = scala.collection.mutable.Map[String, String]().empty
  var users : util.List[MobileAdUser] = new util.ArrayList[MobileAdUser]()
}

class WeirdXmlDomParser extends Parser {

  @throws(classOf[Exception])
  def parse(filename: String): AdUserMessage = {
    val document: Document = getDocumentBuilder.parse(filename)
    val adMessage = new AdUserMessage
    val users: util.List[MobileAdUser] = new util.ArrayList[MobileAdUser]
    val nodeList: NodeList = document.getDocumentElement.getChildNodes

    var user: MobileAdUser = null
    for(i <- 0 until nodeList.getLength) {
          val firstLevelNode: Node = nodeList.item(i)
          if (firstLevelNode.isInstanceOf[Element]) {

            if ((firstLevelNode.asInstanceOf[Element]).getTagName == "header") {
              adMessage.header = buildAdHeader(firstLevelNode.asInstanceOf[Element])
            } else if ((firstLevelNode.asInstanceOf[Element]).getTagName == "userHeader") {
              user = buildMobileAdUser(firstLevelNode)
            } else if(firstLevelNode.asInstanceOf[Element].getTagName == "game"){
                val secondLevelChildNodes = firstLevelNode.asInstanceOf[Element].getChildNodes
                for (i <- 0 until secondLevelChildNodes.getLength) {
                  if (secondLevelChildNodes.item(i).isInstanceOf[Element]) {
                    user.games += secondLevelChildNodes.item(i).asInstanceOf[Element].getTextContent.trim
                  }
                }
            } else if ((firstLevelNode.asInstanceOf[Element]).getTagName == "userEnd") {
              users.add(user)
              user = null
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

  private def buildAdHeader(node: Element): mutable.Map[String, String] = {
    val firstLevelChildNodes: NodeList = node.getChildNodes
    var map = mutable.Map[String, String]().empty

    for (i <- 0 until(firstLevelChildNodes.getLength)) {
      val firstLevelChildNode: Node = firstLevelChildNodes.item(i)
      if (firstLevelChildNode.isInstanceOf[Element]) {
        val secondLevelChildNodes = firstLevelChildNode.asInstanceOf[Element].getChildNodes

        for (i <- 0 until(secondLevelChildNodes.getLength)) {
          val secondLevelNode = secondLevelChildNodes.item(i)

          if(secondLevelNode.isInstanceOf[Element]) {
            val content: String = secondLevelNode.getLastChild.getTextContent.trim
              map+= secondLevelNode.getNodeName -> content
          }
        }
      }
    }
    map
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

object WeirdXmlDomParser {
  def main(args: Array[String]) {
    new WeirdXmlDomParser().parse("/Users/prayagupd/prayag.data/workspace.programming/java8/hire-artists/router/app/service/domParser/SuperWeird.xml")
  }
}
