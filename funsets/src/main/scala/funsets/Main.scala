package funsets

object Main extends App {
  import FunSets._
  println(contains(singletonSet(1), 1))

  printSet(rangeSet(0,10))
  printSet(map(rangeSet(0,10), (x:Int) => x*10))
}
