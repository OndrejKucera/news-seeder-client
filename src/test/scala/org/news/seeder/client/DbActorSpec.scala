package org.news.seeder.client

import akka.actor.ActorSystem
import akka.pattern.ask
import akka.testkit.TestKit
import akka.util.Timeout
import org.news.seeder.client.DbActor.{ActionPerformed, CreateRss, GetRssList}
import org.news.seeder.client.model.RssList
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}

class DbActorSpec(_system: ActorSystem) extends TestKit(_system) with Matchers with WordSpecLike with BeforeAndAfterAll {

  implicit val executionContext: ExecutionContext = ExecutionContext.Implicits.global

  implicit lazy val timeout: Timeout = Timeout(5.seconds)

  def this() = this(ActorSystem("ActorTest"))

  override def afterAll: Unit = {
    shutdown(system)
  }

  "Database Actor" should {
    "return empty rss list" in {
      // TODO: Casandra Mock
      val dbActor = system.actorOf(DbActor.propsWithMock)

      val rssList: Future[RssList] =
        (dbActor ? GetRssList).mapTo[RssList]

      rssList.map(list =>
        list.rssList should ===(List())
      )
    }

    "create rss record" in {
      // TODO: Casandra Mock
      val dbActor = system.actorOf(DbActor.propsWithMock)

      val url = "http://rss.com"
      val action: Future[ActionPerformed] =
        (dbActor ? CreateRss(url)).mapTo[ActionPerformed]

      action.map( answer =>
        answer.message should ===("")
      )

    }

    "delete rss record" in {
      // TODO:
    }
  }


}
