package dsls.regex

import org.scalacheck._
import Prop.forAll
import RegularExpression._
import scala.language.postfixOps

/**
 * These tests are to make sure that the pattern-matching algorithm works.
 * 
 * Modified to use our internal DSL
 */
object RegexCheck extends Properties("Regex") {
  property("empty") = forAll { s: String ⇒ !(EMPTY matches s) }
  
  property("literals") = forAll { c: Char ⇒ c matches c.toString }
  
  property("concat") = forAll { (c1: Char, c2: Char) ⇒ 
    c1 ~ c2 matches (c1.toString + c2.toString)
  }
  
  property("union") = forAll { (c1: Char, c2: Char) ⇒ 
    val pattern = c1 || c2
    (pattern matches c1.toString) && (pattern matches c2.toString)  
  }
  
  property("star") = forAll { c: Char ⇒
    val pattern = (c <*>)
    (pattern matches "") && (pattern matches c.toString) &&
      (pattern matches c.toString * 3)
  }
  
  property("epsilon*") = forAll { c: Char ⇒ 
    !((EPSILON <*>) matches c.toString)
  }
}