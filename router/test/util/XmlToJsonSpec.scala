package util

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import service.util.XmlToJson

import spray.json._
import DefaultJsonProtocol._ // if you don't supply your own Protocol (see below)



/**
 * Created by prayagupd
 * on 12/21/15.
 */

@RunWith(classOf[JUnitRunner])
class XmlToJsonSpec extends Specification {

  "toJson" should {
    "convert xml to json" in {

      val xml =
        """<?xml version="1.0" encoding="UTF-8"?>
          |<HireArtist>
          |   <HireCode>HR0001</HireCode>
          |   <Artists>
          |      <Artist>
          |         <ArtistId>1116500820000</ArtistId>
          |         <Date>186790800003</Date>
          |      </Artist>
          |      <Artist>
          |         <ArtistId>1250360220000</ArtistId>
          |         <Date>1250360220000</Date>
          |      </Artist>
          |   </Artists>
          |</HireArtist>
        """.stripMargin
           .replaceAll(">\\s+<", "><")

      val actualJson = new XmlToJson().toJson(xml)

      val expectedJson = """
        |{
        |  "HireArtist": {
        |    "HireCode": "HR0001",
        |    "Artists": {
        |      "Artist": [
        |        {
        |          "ArtistId": "1116500820000",
        |          "Date": "186790800003"
        |        },
        |        {
        |          "ArtistId": "1250360220000",
        |          "Date": "1250360220000"
        |        }
        |      ]
        |    }
        |  }
        |}
      """.stripMargin

//      println(actualJson)
      actualJson.parseJson mustEqual expectedJson.parseJson
    }
  }

  "toJson" should {
    "convert xml with attributes to json with metadata" in {

      val xml =
        """<?xml version="1.0" encoding="UTF-8"?>
          |<HireArtist eventPriority="high &quot;priority&quot;" earlyPay="yes" seats="1000">
          |   <HireCode>HR0001</HireCode>
          |   <Artists count="2">
          |      <Artist>
          |         <ArtistId>1116500820000</ArtistId>
          |         <Date>186790800003</Date>
          |      </Artist>
          |      <Artist>
          |         <ArtistId>1250360220000</ArtistId>
          |         <Date>1250360220000</Date>
          |      </Artist>
          |   </Artists>
          |</HireArtist>
        """.stripMargin
          .replaceAll(">\\s+<", "><")

      val actualJson = new XmlToJson().toJson(xml)

      val expectedJson = """
                           |{
                           |  "HireArtist": {
                           |   "eventPriority" : "high \"priority\"",
                           |   "earlyPay" : "yes",
                           |   "seats" : "1000",
                           |    "HireCode": "HR0001",
                           |    "Artists": {
                           |    "count" : "2",
                           |      "Artist": [
                           |        {
                           |          "ArtistId": "1116500820000",
                           |          "Date": "186790800003"
                           |        },
                           |        {
                           |          "ArtistId": "1250360220000",
                           |          "Date": "1250360220000"
                           |        }
                           |      ]
                           |    }
                           |  }
                           |}
                         """.stripMargin
      println("--")
      println(actualJson)
      println("--")
      actualJson.parseJson mustEqual expectedJson.parseJson
    }
  }
}
