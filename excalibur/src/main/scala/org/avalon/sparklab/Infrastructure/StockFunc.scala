package org.avalon.sparklab.Infrastructure

object StockFunc {
  
  /**
   * 移动平均线
   */
  def MA(n: Int, data: List[DayPeriodicity]): Int = {

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
  }

  /**
   * EMA（X，N） = 2*当天的收盘价[递归时是按递归的那天值]/(N+1)+(N-1)/(N+1)*昨天的指数收盘平均值
   * @param n
   * @param index
   * @param data
   * @return
   */
  def EMA(price: CandlestickChartPrice, n: Int, index: Int, data: List[DayPeriodicity]): Int = {

    var result = -1
    if (price == CandlestickChartPrice.Close) {

      if (index == 0)
        result = data(0).close
      else if (index > 0) {
        //println(2*data(n).close/(n+1))

        //bug 只算出前半部  这里除法时，会不满1折算为0需要转成浮点数最后结果再 4舍5入取整
        //println("..."+ (n-1)/(n+1).toFloat +" * " + EMA(n,index-1,data).floor )
        result = (2 * data(index).close / (n + 1).toFloat + (n - 1) / (n + 1).toFloat * EMA(price, n, index - 1, data)).round
      }

    } else if (price == CandlestickChartPrice.Trend) {

      if (index == 0)
        result = data(0).trend
      else if (index > 0) {

        //println("data(index).trend ="+data(index).trend )
        //println(2*data(n).close/(n+1))

        //bug 只算出前半部  这里除法时，会不满1折算为0需要转成浮点数最后结果再 4舍5入取整
        //println("..."+ (n-1)/(n+1).toFloat +" * " + EMA(n,index-1,data).floor )
        result = (2 * data(index).trend / (n + 1).toFloat + (n - 1) / (n + 1).toFloat * EMA(price, n, index - 1, data)).round
      }

    } else if (price == CandlestickChartPrice.Volume) {

      if (index == 0)
        result = data(0).tradingVolume
      else if (index > 0) {

        //println("data(index).trend ="+data(index).trend )
        //println(2*data(n).close/(n+1))

        //bug 只算出前半部  这里除法时，会不满1折算为0需要转成浮点数最后结果再 4舍5入取整
        //println("..."+ (n-1)/(n+1).toFloat +" * " + EMA(n,index-1,data).floor )
        result = (2 * data(index).tradingVolume / (n + 1).toFloat + (n - 1) / (n + 1).toFloat * EMA(price, n, index - 1, data)).round
      }

    }

    result
  }

  def BBI(index: Int, data: List[DayPeriodicity]): Double = {
    (MA(CandlestickChartPrice.Close, 3, index, data) + MA(CandlestickChartPrice.Close, 6, index, data) + MA(CandlestickChartPrice.Close, 12, index, data) + MA(CandlestickChartPrice.Close, 24, index, data)) / 4
  }

  def STD(n: Int, index: Int, data: List[DayPeriodicity]): Double = {

    var sum: Int = 0
    var square: Long = 0
    var i, j = n - 1

    while (i >= 0) {
      sum += BBI(index - i, data).toInt
      i -= 1
    }
    var avg = sum / n

    while (j >= 0) {

      square += ((BBI(index - j, data).toInt - avg) * (BBI(index - j, data).toInt - avg))

      j -= 1
    }
    // square 溢出，导致后续NaN
    math.sqrt(square / n).round
  }

  /**
   * 比如HHV(C,25)
   * @param n
   * @param index 0..index
   * @param data
   * @return  n日的最高值
   */
  def HHV(price: CandlestickChartPrice, n: Int, index: Int, data: List[DayPeriodicity]): Int = {
    // 在刚开始遍历时索引小于周期 

    /* println("index "+index)
    println("n "+n)*/

    var result = -1

    if (index < n) {
      if (price == CandlestickChartPrice.Close)
        result = data.take(index + 1).sortBy { x =>
          x.close
        }.last.close
      else if (price == CandlestickChartPrice.High) {
        result = data.take(index + 1).sortBy { x =>
          x.high
        }.last.high
      } else if (price == CandlestickChartPrice.Low) {
        result = data.take(index + 1).sortBy { x =>
          x.low
        }.last.low
      }
    } else {
      // 按索引取集合
      // 对集合排序 排序后，选择头尾
      if (price == CandlestickChartPrice.Close)
        result = data.slice(index - n + 1, index + 1).sortBy { x =>
          x.close
        }.last.close
      else if (price == CandlestickChartPrice.High) {
        result = data.slice(index - n + 1, index + 1).sortBy { x =>
          x.high
        }.last.high
        //println((index - n + 1) + " " + (index + 1) + "==" + result)
        //data.slice(index - n + 1, index + 1).foreach { x => println(x.high) }
      } else if (price == CandlestickChartPrice.Low) {
        result = data.slice(index - n + 1, index + 1).sortBy { x =>
          x.low
        }.last.low
      }
    }
    result
  }

  /**
   * 比如HHV(C,25)
   * @param n
   * @param index 0..index
   * @param data
   * @return  n日的最高值
   */
  def LLV(price: CandlestickChartPrice, n: Int, index: Int, data: List[DayPeriodicity]): Int = {

    /*println("index "+index)
    println("n "+n)*/
    var result = -1

    if (index < n) {
      if (price == CandlestickChartPrice.Close)
        result = data.take(index + 1).sortBy { x =>
          x.close
        }.head.close
      else if (price == CandlestickChartPrice.High) {
        result = data.take(index + 1).sortBy { x =>
          x.high
        }.head.high
      } else if (price == CandlestickChartPrice.Low) {
        result = data.take(index + 1).sortBy { x =>
          x.low
        }.head.low
      }
    } else {
      // 按索引取集合
      // 对集合排序 排序后，选择头尾
      if (price == CandlestickChartPrice.Close)
        result = data.slice(index - n + 1, index + 1).sortBy { x =>
          x.close
        }.head.close
      else if (price == CandlestickChartPrice.High) {
        result = data.slice(index - n + 1, index + 1).sortBy { x =>
          x.high
        }.head.high
      } else if (price == CandlestickChartPrice.Low) {
        result = data.slice(index - n + 1, index + 1).sortBy { x =>
          x.low
        }.head.low
        //println((index - n + 1)+" "+(index + 1)+"=="+result)
      }
    }
    result
  }
}