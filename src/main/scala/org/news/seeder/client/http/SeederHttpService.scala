package org.news.seeder.client.http

import akka.event.Logging
import akka.http.scaladsl.model.{ContentType, _}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import org.news.seeder.client.Main.system
import org.news.seeder.client.api.HBaseAPI

class SeederHttpService() {

  private val log = Logging(system, this.getClass)
  private val hbase = new HBaseAPI()

  def routes: Route =
    pathPrefix("seeder") {
      get {
        path("health") {
          log.debug("health check")
          complete(StatusCodes.OK, HttpEntity.Empty)
        } ~
        path("rss-list") {
          log.debug("pulling rss-list")
          val json = hbase.getRssList()
          complete(StatusCodes.OK, HttpEntity(ContentType(MediaTypes.`application/json`), json))
        }
      } ~
      post {
        path("rss") {
          log.info("saving new rss")
          hbase.saveRss()
          complete(StatusCodes.OK, HttpEntity.Empty)
        }
      } ~
      delete {
        pathPrefix("rss") {
          log.info("deleting rss")
          hbase.deleteRss()
          complete(StatusCodes.OK, HttpEntity.Empty)
        }
      }
    }

  // TODO: failing

}
