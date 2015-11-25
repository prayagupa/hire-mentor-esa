import java.util

import com.fasterxml.jackson.databind.{ObjectWriter, ObjectMapper}
import domain.MobileAdUser
import net.liftweb.json.DefaultFormats
import org.junit.runner.RunWith
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner
import service.domParser.{WeirdXmlDomParser, OrderMessage}
import net.liftweb.json.Serialization.write


/**
 * Created by prayagupd
 * on 11/21/15.
 */

@RunWith(classOf[JUnitRunner])
class DomParserSpec extends Specification {

  "DomParser" should {
    "parse a XML file properly" in {
        "two nums" in {
          1 + 1 mustEqual 2
        }
    }
  }

  "DomParser" should {
    "returns users of size 1" in {
      implicit val formats = DefaultFormats

      val service = new WeirdXmlDomParser
        val filename = "/Users/prayagupd/prayag.data/workspace.programming/java8/hire-artists/router/app/service/domParser/Weird.xml"
        val message : OrderMessage = service.parse(filename)
        val json = write(message.orders)
        println(json)
        val objectMapper : ObjectMapper = new ObjectMapper()
        val writer : ObjectWriter = objectMapper.writer()
        val json_ = writer.withDefaultPrettyPrinter().writeValueAsString(message.orders.get(0).lineItems)
        println(JsonUtil.toJson(message.header))

      val car = new Car()
      car.brand = "BMW"
      car.doors = 4

      println(JsonUtil.toJson(car))

      message.header.size must equalTo(7)
        message.orders.size() must equalTo(2)
    }
  }
}

class Car {
  var brand =""
  var doors = 0
}