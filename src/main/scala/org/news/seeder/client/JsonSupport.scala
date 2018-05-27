package org.news.seeder.client

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import org.news.seeder.client.DbActor.ActionPerformed
import org.news.seeder.client.model.{ Rss, RssList, RssRequest }
import spray.json.RootJsonFormat

trait JsonSupport extends SprayJsonSupport {

  import spray.json.DefaultJsonProtocol._

  implicit val rssJsonFormat: RootJsonFormat[Rss] = jsonFormat2(Rss)
  implicit val rssListJsonFormat: RootJsonFormat[RssList] = jsonFormat1(RssList)
  implicit val rssRequestJsonFormat: RootJsonFormat[RssRequest] = jsonFormat1(RssRequest)

  implicit val actionPerformedJsonFormat: RootJsonFormat[ActionPerformed] = jsonFormat1(ActionPerformed)
}
