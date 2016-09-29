package dsls.regex

import org.scalatest.FunSuite
import org.scalatest.Matchers

import scala.language.postfixOps

class RegexSuite extends FunSuite with Matchers {
  import RegularExpression._

  test("epsilon") {
    (ε matches "") should be (true)
  }

  test("epsilon*") {
    ((ε *) matches "") should be (true)
  }
}