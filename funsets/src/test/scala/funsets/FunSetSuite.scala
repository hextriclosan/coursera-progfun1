package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
  * This class is a test suite for the methods in object FunSets. To run
  * the test suite, you can either:
  *  - run the "test" command in the SBT console
  *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
  */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
    * Link to the scaladoc - very clear and detailed tutorial of FunSuite
    *
    * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
    *
    * Operators
    *  - test
    *  - ignore
    *  - pending
    */

  /**
    * Tests are written using the "test" operator and the "assert" method.
    */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
    * For ScalaTest tests, there exists a special equality operator "===" that
    * can be used inside "assert". If the assertion fails, the two values will
    * be printed in the error message. Otherwise, when using "==", the test
    * error message will only say "assertion failed", without showing the values.
    *
    * Try it out! Change the values so that the assertion fails, and look at the
    * error message.
    */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
    * When writing tests, one would often like to re-use certain values for multiple
    * tests. For instance, we would like to create an Int-set and have multiple test
    * about it.
    *
    * Instead of copy-pasting the code for creating the set into every test, we can
    * store it in the test class using a val:
    *
    * val s1 = singletonSet(1)
    *
    * However, what happens if the method "singletonSet" has a bug and crashes? Then
    * the test methods are not even executed, because creating an instance of the
    * test class fails!
    *
    * Therefore, we put the shared values into a separate trait (traits are like
    * abstract classes), and create an instance inside each test method.
    *
    */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)

    val range0_10 = rangeSet(0, 10)
    val range5_15 = rangeSet(5, 15)


  }

  /**
    * This test is currently disabled (by using "ignore") because the method
    * "singletonSet" is not yet implemented and the test would fail.
    *
    * Once you finish your implementation of "singletonSet", exchange the
    * function "ignore" by "test".
    */
  test("singletonSet(1) contains 1") {

    /**
      * We create a new instance of the "TestSets" trait, this gives us access
      * to the values "s1" to "s3".
      */
    new TestSets {
      /**
        * The string argument of "assert" is a message that is printed in case
        * the test fails. This helps identifying which assertion failed.
        */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("rangeSet(0, 10) contains 5 and doesn't contain 15") {

    /**
      * We create a new instance of the "TestSets" trait, this gives us access
      * to the values "s1" to "s3".
      */
    new TestSets {
      /**
        * The string argument of "assert" is a message that is printed in case
        * the test fails. This helps identifying which assertion failed.
        */
      assert(contains(range0_10, 5), "Range 1")
      assert(!contains(range0_10, 15), "Range 2")
    }
  }


  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")

      val s_range = union(range0_10, range5_15)
      assert(contains(s_range, 8), "Union 8")
      assert(contains(s_range, 2), "Union 2")
      assert(contains(s_range, 13), "Union 13")
      assert(!contains(s_range, 20), "Union 20")
    }
  }

  test("intersect contains elements that are only in each set") {
    new TestSets {
      val s = intersect(s1, s2)
      assert(!contains(s, 1), "intersect 1")
      assert(!contains(s, 2), "intersect 2")
      assert(!contains(s, 3), "intersect 3")

      val s_range = intersect(range0_10, range5_15)
      assert(contains(s_range, 8), "intersect 8")
      assert(!contains(s_range, 2), "intersect 2")
      assert(!contains(s_range, 13), "intersect 13")
      assert(!contains(s_range, 20), "intersect 20")
    }
  }

  test("diff contains elements of all elements of `s` that are not in `t`") {
    new TestSets {

      val s_range = diff(range0_10, range5_15)
      assert(!contains(s_range, 8), "diff 8")
      assert(contains(s_range, 2), "diff 2")
      assert(!contains(s_range, 13), "diff 13")
      assert(!contains(s_range, 20), "diff 20")
    }
  }

  test("filter returns the subset of `s` for which `p` holds.") {
    new TestSets {

      val s_range = filter(range0_10, (x: Int) => x % 2 == 0)
      assert(contains(s_range, 2), "filter 2")
      assert(contains(s_range, 8), "filter 8")
      assert(!contains(s_range, 5), "filter 5")
      assert(!contains(s_range, 18), "filter 18")
      assert(!contains(s_range, 15), "filter 15")
    }
  }

  test("forall returns whether integers [-1000;1000] within `s` satisfy `p`.") {
    new TestSets {

      assert(!forall(range0_10, (x: Int) => x % 2 == 0), "forall 0_10 satisfy x % 2 == 0")
      assert(forall(range0_10, (x: Int) => x > -2 && x < 16), "forall 0_10 satisfy all x in [-2;16]")
    }
  }

  test("exists") {
    new TestSets {

      assert(exists(range0_10, (x: Int) => x % 2 == 0), "forall 0_10 satisfy x % 2 == 0")
      assert(exists(range0_10, (x: Int) => x >= 9 && x <= 16), "forall 0_10 satisfy all x in [-2;16]")
      assert(!exists(range0_10, (x: Int) => x >= 12 && x <= 16), "forall 0_10 satisfy all x in [-2;16]")
    }
  }

  test("map") {
    new TestSets {

      val modifiedSet = map(range0_10, (x: Int) => x + 5)
      assert(!contains(modifiedSet, 2), "map 2")
      assert(contains(modifiedSet, 8), "map 8")
      assert(contains(modifiedSet, 12), "map 12")
      assert(!contains(modifiedSet, 17), "map 17")
    }
  }

}
