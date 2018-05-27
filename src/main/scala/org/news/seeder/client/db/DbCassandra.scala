package org.news.seeder.client.db

import com.datastax.driver.core.{ Cluster, SimpleStatement }
import org.news.seeder.client.DbActor.ActionPerformed
import org.news.seeder.client.model.RssList

class DbCassandra extends DbAPI {

  implicit val session = Cluster.builder
    .addContactPoint("127.0.0.1")
    .withPort(9042)
    .build
    .connect()

  override def getRssList: RssList = {
    val stmt = new SimpleStatement(s"SELECT url, valid FROM rss_table")
    //    val rows = CassandraSource(stmt).runWith(Sink.seq)
    // TODO: parse rows
    RssList(List())
  }

  override def createRss(url: String): ActionPerformed = ???

  override def deleteRss(url: String): ActionPerformed = ???
}
