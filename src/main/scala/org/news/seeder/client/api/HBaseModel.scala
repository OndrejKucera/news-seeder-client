package org.news.seeder.client.api

import akka.event.Logging
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.client.{Connection, ConnectionFactory, Table}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.news.seeder.client.Main.system


object HBaseModel {

  private val log = Logging(system, this.getClass)
  var table: Table = _
  private var connection: Connection = _

  def apply(tableName :String): this.type = {
    val hBaseConfig = initHBaseConfig()
    table = getTable(tableName, hBaseConfig)
    checkHBase()
    log.info("The connection with HBase was established")
    this
  }

  def apply(): Unit = {
    log.error("Don't use this constructor")
    // TODO: error
  }

  private def initHBaseConfig(): Configuration = {
    val hBaseConfig = HBaseConfiguration.create()
    hBaseConfig.addResource("hbase-site.xml")
    hBaseConfig
  }

  private def getTable(tableName: String, hBaseConfig: Configuration): Table = {
    connection = ConnectionFactory.createConnection(hBaseConfig)
    connection.getTable(TableName.valueOf(Bytes.toBytes(tableName)))
  }

  private def checkHBase(): Unit = {
    if (table.getName.getNameAsString != "rss_table") {
      log.error("The connection could not be established")
      // TODO: crash when the connection doesnt work
    }
  }

  def close (): Unit = {
    table.close()
    connection.close()
  }

}
