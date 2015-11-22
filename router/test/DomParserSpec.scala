import java.util

import domain.MobileAdUser
import net.liftweb.json.DefaultFormats
import org.junit.runner.RunWith
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner
import service.domParser.{WeirdXmlDomParser, AdUserMessage}
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
        val filename = "/Users/prayagupd/prayag.data/workspace.programming/java8/hire-artists/router/app/service/domParser/SuperWeird.xml"
        val message : AdUserMessage = service.parse(filename)
        val json = write(message)
        println(json)
//        list.forEach(user -> {
//          println(user.name)
//        })
        message.header.size must equalTo(3)
        message.users.size() must equalTo(2)
    }
  }
}
