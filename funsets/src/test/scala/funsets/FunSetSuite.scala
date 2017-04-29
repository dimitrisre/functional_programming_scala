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
   test("string take") {
     val message = "hello, world"
     assert(message.take(5) == "hello")
   }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
   test("adding ints") {
     assert(1 + 2 === 3)
   }


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
   *   val s1 = singletonSet(1)
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

    val su1 = union(s1, s2)
    val su2 = union(s2, s3)
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

  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("intersection contains only common elements of each set"){
    new TestSets {
      val si = intersect(s1, s2)
      val si2 = intersect(su1, su2)

      assert(!contains(si,1), "Interection 1")
      assert(contains(si2, 2), "Intersection 2")
    }
  }

  test("difference contains elements of one set and not the elements of the other set"){
    new TestSets {
      val sd = diff(su1, s1)
      val sd2 = diff(su2, s3)

      assert(!contains(sd,1), "Difference 1")
      assert(contains(sd2, 2), "Difference 2")
    }
  }

  test("Check if forall function for integers greater than zero"){
    new TestSets {
      val s4 = singletonSet(-1)
      val su3 = union(su2, s4)

      val b = forall(su3, (x: Int)=>x>0)
      assert(!b, "Forall integers are not greater than zero")

      val b2 = forall(su2, (x: Int)=>x>0)
      assert(b2, "Forall integers are greater than zero")

    }
  }

  test("check if exists contains at least one bounded member that satisfies p"){
    new TestSets {
      val b = exists(su2, (x => x<3))

      assert(b, "Check if su2 has at least one less than zero")
    }
  }

  test("Map a function on all elements of a set"){
    new TestSets {
      val sm = map(su2, (x => x+1))

      assert(!contains(sm, 2), "Check if Sm does not contain 2")
      assert(contains(sm, 3), "Check if Sm contains 3")
      assert(contains(sm, 4), "Check if Sm contains 4")
    }
  }

}
