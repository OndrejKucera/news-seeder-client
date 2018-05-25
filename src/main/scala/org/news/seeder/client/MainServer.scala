package org.news.seeder.client

import akka.actor.{ ActorRef, ActorSystem }
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object MainServer extends App with RssRoutes {

  private val config = ConfigFactory.load()

  implicit val system: ActorSystem = ActorSystem("news-seeder-client")
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  // TODO: create the Cassandra API
  val dbActor: ActorRef = system.actorOf(DbActor.propsWithMock, "dbActor")

  lazy val routes: Route = rssRoutes

  Http().bindAndHandle(routes, config.getString("http.interface"), config.getInt("http.port"))

  println(s"Server online at http://localhost:8080/")

  Await.result(system.whenTerminated, Duration.Inf)

}