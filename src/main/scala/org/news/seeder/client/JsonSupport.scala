package org.news.seeder.client

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import org.news.seeder.client.DbActor.ActionPerformed

trait JsonSupport extends SprayJsonSupport {

  import spray.json.DefaultJsonProtocol._

  implicit val rssJsonFormat = jsonFormat2(Rss)
  implicit val seqRssJsonFormat = jsonFormat1(RssList)

  implicit val actionPerformedJsonFormat = jsonFormat1(ActionPerformed)
}
