package org.avalon.sparklab.infrastructure

object StockFunc {
  
  /**
   * 移动平均线
   */
  /*def MA(n: Int, data: List[DayPeriodicity]): Int = {

    var result: Int = -1

    //需添加判断是否左向越界
    if (n <= index) {

      var sum: Int = 0
      var m: Int = n
      // 4 3 2 1 0  ---  data(0)- data(4)
      while (m > 0) {
        m -= 1
        if (price == CandlestickChartPrice.Close) {
          sum += data(index - m).close
        } else if (price == CandlestickChartPrice.Volume) {
          sum += data(index - m).tradingVolume
        }
      }
      result = sum / n
    }

    result
  }*/

  
}