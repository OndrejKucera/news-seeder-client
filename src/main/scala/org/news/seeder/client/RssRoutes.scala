package org.news.seeder.client

import akka.actor.{ ActorRef, ActorSystem }
import akka.event.Logging
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import akka.util.Timeout
import org.news.seeder.client.DbActor.{ ActionPerformed, CreateRss, DeleteRss, GetRssList }
import org.news.seeder.client.model.{ RssList, RssRequest }

import scala.concurrent.Future
import scala.concurrent.duration._

trait RssRoutes extends JsonSupport {

  implicit def system: ActorSystem

  lazy val log = Logging(system, classOf[RssRoutes])

  def dbActor: ActorRef

  implicit lazy val timeout: Timeout = Timeout(5.seconds)

  val rssRoutes: Route =
    pathPrefix("seeder") {
      concat(
        get {
          concat(
            path("health") {
              complete(StatusCodes.NoContent)
            },
            path("rss-list") {
              val rssList: Future[RssList] =
                (dbActor ? GetRssList).mapTo[RssList]
              log.info("Listing all rss from db")
              complete(StatusCodes.OK, rssList)
            })
        },
        post {
          path("rss") {
            entity(as[RssRequest]) { req =>
              val rssCreated: Future[ActionPerformed] =
                (dbActor ? CreateRss(req.url)).mapTo[ActionPerformed]
              onSuccess(rssCreated) { message =>
                log.info("Created rss [{}]", req.url)
                complete(StatusCodes.Created, message)
              }
            }
          }
        },
        delete {
          path("rss") {
            entity(as[RssRequest]) { req =>
              val rssDeleted: Future[ActionPerformed] =
                (dbActor ? DeleteRss(req.url)).mapTo[ActionPerformed]
              onSuccess(rssDeleted) { message =>
                log.info("Deleted rss [{}]", req.url)
                complete(StatusCodes.OK, message)
              }
            }
          }
        })
    }

}
