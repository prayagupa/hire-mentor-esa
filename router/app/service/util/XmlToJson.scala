package service.util

import java.io.StringReader
import java.util
import javax.xml.parsers.{DocumentBuilder, DocumentBuilderFactory, ParserConfigurationException}

import com.codahale.jerkson.Json
import org.w3c.dom.{Document, Node, NodeList}
import org.xml.sax.InputSource

/**
 * Created by prayagupd
 * on 11/20/15.
 */

trait XmlParser {
  def toJson(xmlString : String) : Object
}

final class XmlToJson extends XmlParser {

  @throws(classOf[Exception])
  def toJson(xml: String): String = {
      val doc = document(xml, false)
      val map : util.Map[String, Object] = buildMap("\t", doc.getChildNodes)
      Json.generate(map)
  }

  @throws(classOf[ParserConfigurationException])
  private def document(xml : String, ignoreValidation: Boolean): Document = {
    val factory: DocumentBuilderFactory = DocumentBuilderFactory.newInstance
    val f : DocumentBuilder = factory.newDocumentBuilder()
    val document: Document = f.parse(new InputSource(new StringReader(xml)))
    document
  }

  def buildMap( tab: String,  nodes : NodeList) : util.Map[String, Object] = {
    val jsonMap: util.Map[String, Object] = new util.HashMap[String, Object]()

    for (iterator <- 0 until nodes.getLength) {
      val currentNode: Node = nodes.item(iterator)
      val currentKey = currentNode.getNodeName

      println(tab + currentNode.getNodeName + " has childs => " + currentNode.hasChildNodes + " => "
        + currentNode.getChildNodes.getLength)

      if (currentNode.getNodeType == Node.ELEMENT_NODE
        && currentNode.hasChildNodes
        && currentNode.getLastChild.getNodeType != Node.TEXT_NODE) {
            if (jsonMap.containsKey(currentKey)) {
              val array: util.List[util.Map[String, Object]] = new util.ArrayList[util.Map[String, Object]]()
              val existingMap = jsonMap.get(currentNode.getNodeName)
              if (existingMap.isInstanceOf[util.Map[String, Object]]) {
                array.add(existingMap.asInstanceOf[util.Map[String, Object]])
              } else {
                array.addAll(existingMap.asInstanceOf[util.List[util.Map[String, Object]]])
              }
              array.add(buildMap(tab + "\t", currentNode.getChildNodes))
              jsonMap.put(currentNode.getNodeName, array)
            } else {
              jsonMap.put(currentNode.getNodeName, buildMap(tab + "\t", currentNode.getChildNodes))
            }

            val attributes = currentNode.getAttributes
            if (attributes.getLength > 0 ) {
              for (it <- 0 until attributes.getLength) {
                val attributeNode = attributes.item(it)
                if (jsonMap.containsKey(currentKey)) {
                  jsonMap.get(currentKey).asInstanceOf[util.Map[String, Object]]
                    .put(attributeNode.getNodeName, String.valueOf(attributeNode.getNodeValue))
                } else {
                  val map = new util.HashMap[String, Object]()
                  map.put(attributeNode.getNodeName, String.valueOf(attributeNode.getNodeValue))
                  jsonMap.put(currentKey, map)
                }
              }
            }
        } else if (currentNode.hasChildNodes && currentNode.getLastChild.getNodeType == Node.TEXT_NODE) {
          if (jsonMap.containsKey(currentNode.getNodeName)) {
            val array: util.List[Object] = new util.ArrayList[Object]()
            val value = jsonMap.get(currentNode.getNodeName)
            value match {
              case list: util.List[util.Map[String, Object]] =>
                array.addAll(list)
              case _ =>
                array.add(value.asInstanceOf[String])
            }
            array.add(String.valueOf(currentNode.getTextContent))
            jsonMap.put(currentNode.getNodeName, array)
          } else {
            jsonMap.put(currentNode.getNodeName, String.valueOf(currentNode.getTextContent))
          }
        }
      }
    jsonMap
  }
}
