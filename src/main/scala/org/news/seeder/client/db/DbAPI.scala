package org.news.seeder.client.db

import org.news.seeder.client.DbActor.ActionPerformed
import org.news.seeder.client.RssList

trait DbAPI {
  def getRssList(): RssList

  def createRss(url: String): ActionPerformed

  def deleteRss(url: String): ActionPerformed
}
