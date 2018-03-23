package org.news.seeder.client

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import org.news.seeder.client.api.HBaseModel
import org.news.seeder.client.http.SeederHttpService

import scala.concurrent.ExecutionContextExecutor

object Main extends App with Directives {

  private val config = ConfigFactory.load()

  implicit val system: ActorSystem = ActorSystem("news-seeder-client", config)
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  private val log = Logging(system, this.getClass)

  log.info("===== Starting Seeder Client =====")

  HBaseModel(config.getString("hbase.table"))

  Http().bindAndHandle(new SeederHttpService().routes,
    config.getString("http.interface"), config.getInt("http.port"))
}