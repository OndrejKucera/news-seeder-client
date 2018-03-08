package org.news.seeder.client

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import org.news.seeder.client.http.SeederHttpService

object Main extends App with Directives {

  private val config = ConfigFactory.load()

  implicit val system = ActorSystem("news-seeder-client", config)
  implicit val materializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher

  val log = Logging(system, this.getClass)

  log.info("\n===== Starting Seeder Client =====")
  Http().bindAndHandle(new SeederHttpService().routes, "0.0.0.0", 8181)
}

