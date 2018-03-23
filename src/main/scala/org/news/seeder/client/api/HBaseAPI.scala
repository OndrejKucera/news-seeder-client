package org.news.seeder.client.api

import org.apache.hadoop.hbase.CellUtil
import org.apache.hadoop.hbase.client.{Result, Scan}
import org.apache.hadoop.hbase.util.Bytes
import org.news.seeder.client.api.HBaseModel

seal trait HBaseFunctionality {
  def getRssList(): String
  def saveRss(): Unit
  def deleteRss(): Unit
}

class HBaseAPI() extends HBaseFunctionality {

  def getRssList(): String = {
    val scan = HBaseModel.table.getScanner(new Scan())

    var seqRows: Seq[String]= Seq()

    var result: Result = scan.next()
    while (result != null) {
      seqRows = getRow(result) +: seqRows
      result = scan.next()
    }

    // TODO: adjust into one json
    seqRows.toString()
  }


  private def getRow(result : Result): String = {
    // TODO: return it in json

    val cells = result.rawCells()
    var data = Bytes.toString(result.getRow) + " : "
    for(cell <- cells){
      val col_name = Bytes.toString(CellUtil.cloneQualifier(cell))
      val col_value = Bytes.toString(CellUtil.cloneValue(cell))
      val colum = "(%s,%s) ".format(col_name, col_value)
      data += colum
    }

    data
  }

}
