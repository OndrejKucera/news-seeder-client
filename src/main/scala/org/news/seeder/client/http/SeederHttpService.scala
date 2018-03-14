package org.news.seeder.client.http

import akka.event.Logging
import akka.http.scaladsl.model.{HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import org.news.seeder.client.Main.system
import org.news.seeder.client.api.HBaseModel

class SeederHttpService() {

  private val log = Logging(system, this.getClass)

  def routes: Route =
    pathPrefix("seeder") {
      get {
        path("health") {
          log.info("call: health check")
          complete(StatusCodes.OK, HttpEntity.Empty)
        } ~
        path("rss-list") {
          log.info("call: get rss list")
          val hBaseModel = HBaseModel
          hBaseModel.getRssList()
          complete(StatusCodes.OK, HttpEntity.Empty)
        }
      }
    }

// ~
//      post {
//        path("rss") {
//          // TODO: call habse - save the rss url
//
//        }
//      } ~
//      delete {
//        pathPrefix("rss") {
//          // TODO: call habse - delete the rss url
//
//        }


}
