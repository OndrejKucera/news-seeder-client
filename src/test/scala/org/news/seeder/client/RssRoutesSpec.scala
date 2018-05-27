package org.news.seeder.client

import akka.actor.ActorRef
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.{ ContentTypes, HttpRequest, MessageEntity, StatusCodes }
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.news.seeder.client.model.RssRequest
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{ Matchers, WordSpec }

class RssRoutesSpec extends WordSpec with Matchers with ScalaFutures with ScalatestRouteTest with RssRoutes {

  // TODO: cassandra mock
  override val dbActor: ActorRef = system.actorOf(DbActor.propsWithMock, "dbActor")

  lazy val routes = rssRoutes

  "RssRoutes" should {
    "return empty rss list if no present (GET /seeder/rss-list)" in {
      val request = HttpRequest(uri = "/seeder/rss-list")

      request ~> routes ~> check {
        status should ===(StatusCodes.OK)
        contentType should ===(ContentTypes.`application/json`)
        entityAs[String] should ===("""{"rssList":[]}""")
      }
    }

    "return 200 status for health check (GET /seeder/health)" in {
      val request = HttpRequest(uri = "/seeder/health")

      request ~> routes ~> check {
        status should ===(StatusCodes.NoContent)
      }
    }

    "be able to add rss (POST /seeder/rss)" in {
      val rss = RssRequest("http://url.request.com")
      val rssEntity = Marshal(rss).to[MessageEntity].futureValue

      val request = Post("/seeder/rss").withEntity(rssEntity)

      request ~> routes ~> check {
        status should ===(StatusCodes.Created)
        contentType should ===(ContentTypes.`application/json`)
        entityAs[String] should ===("""{"message":"Rss http://url.request.com created."}""")
      }
    }

    "be able to remove rss (DELETE /seeder/rss)" in {
      val rss = RssRequest("http://url.request.com")
      val rssEntity = Marshal(rss).to[MessageEntity].futureValue

      val request = Delete(uri = "/seeder/rss").withEntity(rssEntity)

      request ~> routes ~> check {
        status should ===(StatusCodes.OK)
        contentType should ===(ContentTypes.`application/json`)
        entityAs[String] should ===("""{"message":"User http://url.request.com deleted."}""")
      }
    }
  }
}
