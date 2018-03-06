package org.news.seeder.client.http

import akka.http.scaladsl.model.{HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

class SeederHttpService() {

  def routes: Route =
    pathPrefix("seeder") {
      get {
        path("health") {
          complete(StatusCodes.OK, HttpEntity.Empty)
        } ~
        path("rss-list") {
          // TODO: call hbase - get list of all rss url
          val hBaseService = HBaseService

          hBaseService.getRssList()
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
