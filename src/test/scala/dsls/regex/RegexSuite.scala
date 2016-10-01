package dsls.regex

import org.scalatest.FunSuite
import org.scalatest.Matchers
import scala.language.postfixOps

class RegexSuite extends FunSuite with Matchers {
   test("epsilon") {
     (EPSILON matches "") should be (true)
   }
   
   test("epsilon*") {
     ((EPSILON <*>) matches "") should be (true)
   }
}