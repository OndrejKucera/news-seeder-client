package org.news.seeder.client

import akka.actor.{ ActorRef, ActorSystem }
import akka.event.Logging
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import akka.util.Timeout
import org.news.seeder.client.DbActor.{ ActionPerformed, CreateRss, DeleteRss, GetRssList }

import scala.concurrent.Future
import scala.concurrent.duration._

trait RssRoutes extends JsonSupport {

  implicit def system: ActorSystem

  lazy val log = Logging(system, classOf[RssRoutes])

  def dbActor: ActorRef

  implicit lazy val timeout = Timeout(5.seconds)

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
              log.info("???/ povedlose ?")
              complete(StatusCodes.OK, rssList)
            })
        },
        post {
          path("rss") {
            // TODO: acctept the json
            entity(as[String]) { url =>
              val rssCreated: Future[ActionPerformed] =
                (dbActor ? CreateRss(url)).mapTo[ActionPerformed]
              onSuccess(rssCreated) { message =>
                log.info("Created rss [{}]", url)
                complete(StatusCodes.Created, message)
              }
            }
          }
        },
        delete {
          path("rss") {
            // TODO: acctept the json
            entity(as[String]) { url =>
              val rssDeleted: Future[ActionPerformed] =
                (dbActor ? DeleteRss(url)).mapTo[ActionPerformed]
              onSuccess(rssDeleted) { message =>
                log.info("Deleted rss [{}]", url)
                complete(StatusCodes.OK, message)
              }
            }
          }
        })
    }

}
