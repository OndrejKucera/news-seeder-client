package org.news.seeder.client.api

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{CellUtil, HBaseConfiguration, TableName}


object HBaseModel {

  // TODO: load config

  val conf : Configuration = HBaseConfiguration.create()
//  conf.set("hbase.zookeeper.quorum", "zookeeper-1.vnet:2181")

  private val connection = ConnectionFactory.createConnection(conf)
  // TODO: load table
  private val table = connection.getTable(TableName.valueOf(Bytes.toBytes("rss_table") ) )


  def getRssList(): List[String] = {
    val scan = table.getScanner(new Scan())

    var result: Result = scan.next()
    while (result != null) {
      printRow(result)
      result = scan.next()
    }

    table.close()
    connection.close()

    List()
  }

  def printRow(result : Result) = {
    val cells = result.rawCells()
    print( Bytes.toString(result.getRow) + " : " )
    for(cell <- cells){
      val col_name = Bytes.toString(CellUtil.cloneQualifier(cell))
      val col_value = Bytes.toString(CellUtil.cloneValue(cell))
      print("(%s,%s) ".format(col_name, col_value))
    }
    println()
  }


}
