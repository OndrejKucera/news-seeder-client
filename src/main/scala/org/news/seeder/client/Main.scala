package org.news.seeder.client

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import org.news.seeder.client.http._

object Main extends App {

  private val config = ConfigFactory.load()

  private implicit val system = ActorSystem("news-seeder-client", config)
  private implicit val materializer = ActorMaterializer()
  private implicit val executionContext = system.dispatcher

  private val log = Logging(system, this.getClass)

  log.info("===== Starting Seeder Client =====")
  Http().bindAndHandle(new SeederHttpService().routes, "0.0.0.0", 8181)
}

