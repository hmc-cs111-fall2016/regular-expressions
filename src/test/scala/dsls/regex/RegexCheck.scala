package dsls.regex

import org.scalacheck._
import Prop.forAll
import scala.language.implicitConversions
import LiteralImplicits._

/**
 * These tests are to make sure that the pattern-matching algorithm works.
 * 
 * BUT: you can modify these tests to use your internal DSL!
 */
object RegexCheck extends Properties("Regex") {
  property("empty") = forAll { s: String ⇒ !(EMPTY matches s) }
  
  property("literals") = forAll { c: Char ⇒ Literal(c) matches c.toString }

  property("stringLiterals") = forAll { (c1: Char, c2: Char) => 
    val pattern = c1.toString + c2.toString 
    pattern matches (c1.toString + c2.toString)}

  property("charImplicit") = forAll {c: Char =>
    val pattern = c
    pattern matches c.toString}

  property("concat") = forAll { (c1: Char, c2: Char) ⇒ 
    Concat(Literal(c1), Literal(c2)) matches (c1.toString + c2.toString) 
  }
  
  property("concat~") = forAll {(c1: Char, c2: Char) =>
    val pattern = c1 ~ c2
    pattern matches (c1.toString + c2.toString) }

  property("union") = forAll { (c1: Char, c2: Char) ⇒ 
    val pattern = Union(Literal(c1), Literal(c2)) 
    (pattern matches c1.toString) && (pattern matches c2.toString)  
  }

  property("union||") = forAll { (c1: Char, c2: Char) =>
    val pattern = Literal(c1) || Literal(c2)
    (pattern matches c1.toString) && (pattern matches c2.toString)
  }
  
  property("star") = forAll { c: Char ⇒ 
    val pattern = Star(Literal(c)) 
    (pattern matches "") && (pattern matches c.toString) && 
      (pattern matches c.toString * 3) 
  }

  property("star<*>") = forAll { c: Char => 
    val pattern = (Literal(c) <*>)
    (pattern matches "") && (pattern matches c.toString) &&
    (pattern matches c.toString * 3)
  }
  
  property("<+>") = forAll{ c: Char =>
    val pattern = (c <+>)
    (pattern matches c.toString) && (pattern matches c.toString * 3) &&
    !(pattern matches "")
  }

  property("{n}") = forAll{ c: Char =>
    val pattern = (c{3})
    pattern matches c.toString * 3

  }


  property("epsilon*") = forAll { c: Char ⇒ 
    !(Star(EPSILON) matches c.toString)
  }
}