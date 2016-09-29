package dsls.regex

import org.scalacheck._
import Prop.forAll

/**
 * These tests are to make sure that the pattern-matching algorithm works.
 * 
 * BUT: you can modify these tests to use your internal DSL!
 */
object RegexCheck extends Properties("Regex") {
  property("empty") = forAll { s: String ⇒ !(EMPTY matches s) }
  
  property("literals") = forAll { c: Char ⇒ Literal(c) matches c.toString }
  
  property("concat") = forAll { (c1: Char, c2: Char) ⇒ 
    Concat(Literal(c1), Literal(c2)) matches (c1.toString + c2.toString) 
  }
  
  property("union") = forAll { (c1: Char, c2: Char) ⇒ 
    val pattern = Union(Literal(c1), Literal(c2)) 
    (pattern matches c1.toString) && (pattern matches c2.toString)  
  }
  
  property("star") = forAll { c: Char ⇒ 
    val pattern = Star(Literal(c)) 
    (pattern matches "") && (pattern matches c.toString) && 
      (pattern matches c.toString * 3) 
  }
  
  property("epsilon*") = forAll { c: Char ⇒ 
    !(Star(EPSILON) matches c.toString)
  }
}