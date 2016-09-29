package dsls.regex

import org.scalacheck._
import Prop.forAll

import scala.language.postfixOps

/**
 * These tests are to make sure that the pattern-matching algorithm works.
 * 
 * BUT: you can modify these tests to use your internal DSL!
 */
object RegexCheck extends Properties("Regex") {
  import RegularExpression._
  
  property("empty") = forAll { s: String ⇒ s ∉ ∅ }
  
  property("literals") = forAll { c: Char ⇒ c matches c }
  
  property("concat") = forAll { (c1: Char, c2: Char) ⇒ 
    (c1 ⋅ c2) matches (c1.toString + c2.toString) 
  }
  
  property("union") = forAll { (c1: Char, c2: Char) ⇒ 
    val union = c1 ∪ c2
    (c1 ∈ union) && (c2 ∈ union)
  }
  
  property("star") = forAll { c: Char ⇒ 
    val pattern = (c <*>) 
    (pattern matches "") && (pattern matches c) && 
      (pattern matches c.toString * 3) 
  }
  
  property("epsilon*") = forAll { c: Char ⇒ c ∉ (ε *) } 
}