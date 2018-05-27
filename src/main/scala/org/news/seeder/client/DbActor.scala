package org.news.seeder.client

import akka.actor.{ Actor, ActorLogging, Props }
import org.news.seeder.client.db.{ DbAPI, DbCassandra, DbMock }

object DbActor {

  final case class ActionPerformed(message: String)

  final case object GetRssList

  final case class CreateRss(user: String)

  final case class DeleteRss(name: String)

  def propsWithMock: Props = Props(new DbActor(new DbMock))

  def propsWithCassandra: Props = Props(new DbActor(new DbCassandra))
}

class DbActor(db: DbAPI) extends Actor with ActorLogging {

  import DbActor._

  def receive: Receive = {
    case GetRssList =>
      sender() ! db.getRssList
    case CreateRss(url) =>
      sender() ! db.createRss(url)
    case DeleteRss(url) =>
      sender() ! db.deleteRss(url)
  }
}

