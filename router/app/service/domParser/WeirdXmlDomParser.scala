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
  def parse(fileName : String) : OrderMessage
}

class OrderMessage {
  var header : mutable.Map[String, String] = mutable.Map[String, String]().empty
  var orders : util.List[Order] = new util.ArrayList[Order]()
}

class Order {
  var header : mutable.Map[String, String] = mutable.Map[String, String]().empty
  var lineItems : util.List[mutable.Map[String, String]] = new util.ArrayList[mutable.Map[String, String]]()
}

class WeirdXmlDomParser extends Parser {

  @throws(classOf[Exception])
  def parse(filename: String): OrderMessage = {
    val document: Document = getDocumentBuilder.parse(filename)
    val users: util.List[mutable.Map[String, String]] = new util.ArrayList[mutable.Map[String, String]]
    val nodeList: NodeList = document.getDocumentElement.getChildNodes

    val orderMessage = new OrderMessage
    var orderHeader: mutable.Map[String, String] = new mutable.HashMap[String, String]()
    var order = new Order

    for(i <- 0 until nodeList.getLength) {
      val firstLevelNode: Node = nodeList.item(i)
          if (firstLevelNode.isInstanceOf[Element]) {

            if ((firstLevelNode.asInstanceOf[Element]).getTagName == "HEADER") {
              orderMessage.header = buildAdHeader(firstLevelNode.asInstanceOf[Element])
            } else if ((firstLevelNode.asInstanceOf[Element]).getTagName == "ORDERHEADER") {
              order.header = buildMobileAdUser(firstLevelNode)
              orderHeader = mutable.Map[String, String]().empty
            } else if(firstLevelNode.asInstanceOf[Element].getTagName == "LINEITEMMSG"){
                val secondLevelChildNodes = firstLevelNode.asInstanceOf[Element].getChildNodes

              var lineItems: mutable.Map[String, String] = mutable.Map[String, String]()
              for (i <- 0 until secondLevelChildNodes.getLength) {
                  if (secondLevelChildNodes.item(i).isInstanceOf[Element]) {
                    lineItems.put(secondLevelChildNodes.item(i).getNodeName, secondLevelChildNodes.item(i).asInstanceOf[Element].getTextContent.trim)
                  }
                }
              order.lineItems.add(lineItems)
            } else if ((firstLevelNode.asInstanceOf[Element]).getTagName == "ORDEREND") {
              orderMessage.orders.add(order)
              order = new Order
            }
          }
      }
    orderMessage
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

  private def buildMobileAdUser(node: Node): mutable.Map[String, String] = {
//    user.id = node.getAttributes.getNamedItem("id").getNodeValue
    val childNodes: NodeList = node.getChildNodes

      val prop : mutable.Map[String, String] = mutable.Map[String, String]().empty
      for (i <- 0 until(childNodes.getLength)) {
      val childNode: Node = childNodes.item(i)
        if (childNode.isInstanceOf[Element]) {
            var content: String = childNode.getTextContent
            if(content ==null) {
              content = ""
            }
            prop.put(childNode.getNodeName,content.trim)
          }
    }
    prop
  }
}

object WeirdXmlDomParser {
  def main(args: Array[String]) {
    new WeirdXmlDomParser().parse("/Users/prayagupd/prayag.data/workspace.programming/java8/hire-artists/router/app/service/domParser/SuperWeird.xml")
  }
}
