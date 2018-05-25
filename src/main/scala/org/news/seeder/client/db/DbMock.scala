package org.news.seeder.client.db

import org.news.seeder.client.DbActor.ActionPerformed
import org.news.seeder.client.{ Rss, RssList }

class DbMock extends DbAPI {
  var db: List[Rss] = List()

  override def getRssList(): RssList = {
    RssList(db)
  }

  override def createRss(url: String): ActionPerformed = {
    db = url match {
      case u: String => Rss(u, true) :: db
      case _ => db
    }
    ActionPerformed(s"""Rss ${url} created.""")
  }

  override def deleteRss(url: String): ActionPerformed = {
    db = url match {
      case u: String => db.filter(rss => rss.url != u)
    }
    ActionPerformed(s"""User ${url} deleted.""")
  }
}
