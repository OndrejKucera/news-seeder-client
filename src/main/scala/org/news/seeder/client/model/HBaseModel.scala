package org.news.seeder.client.model

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{CellUtil, HBaseConfiguration, TableName}


object HBaseModel {

  // TODO: config -> zookeper, table_name



  def getRssList(): List[String] = {
    val conf : Configuration = HBaseConfiguration.create()

    val ZOOKEEPER_QUORUM = "WRITE THE ZOOKEEPER CLUSTER THAT HBASE SHOULD USE"
    conf.set("hbase.zookeeper.quorum", ZOOKEEPER_QUORUM)

    val connection = ConnectionFactory.createConnection(conf)
    val table = connection.getTable(TableName.valueOf(Bytes.toBytes("emostafa:rss_table") ) )

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
