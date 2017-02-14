package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }

    println(balance("(1()5)3".toList))
    println(balance("(if (zero? x) max (/ 1 x))".toList))
    println(balance("I told him (that it’s not (yet) done). (But he wasn’t listening)".toList))
    println(balance(":-)".toList))
    println(balance("())(".toList))

    println(countChange(4, List(1, 2)))
  }

  /**
    * Exercise 1
    */


  def pascal(c: Int, r: Int): Int = {
    def getNextRow(array: Array[Int], returnAt: Int): Array[Int] = {
      val len = array.length
      val newRow = Array.fill[Int](len + 1)(1)
      if (len > 1) {
        for (i <- 1 until len) newRow(i) = array(i) + array(i - 1)
      }
      if (returnAt == len) newRow else getNextRow(newRow, returnAt)
    }

    getNextRow(Array[Int](), r)(c)
  }

  /**
    * Exercise 2
    */


  def balance(chars: List[Char]): Boolean = {
    val filtered = chars.filter(c => c == '(' || c == ')')

    def removeBraces(chars: List[Char], status: (Int, Int)): (Int, Int) = {

      if (chars.isEmpty) status
      else if (chars.head == '(') removeBraces(chars.tail, status.copy(_1 = status._1 + 1))
      else /*')'*/ if (status._1 > 0) removeBraces(chars.tail, status.copy(_1 = status._1 - 1))
      else removeBraces(chars.tail, status.copy(_2 = status._2 + 1))
    }

    val res = removeBraces(filtered, (0, 0))
    res == (0, 0)
  }

  /**
    * Exercise 3
    */
  def accum(sum: Int, money: Int, coins: List[Int]): Int = {
    if (money > got) {

    }
  }


  def countChange(money: Int, coins: List[Int]): Int = {
    val soretedCoins = coins.sorted

    accum(0, coins)
  }
}
