package dsls.regex

import scala.language.implicitConversions
import scala.language.postfixOps
 
/**
 * Modify this file to implement an internal DSL for regular expressions. 
 * 
 * You're allowed to add anything you want to this file, but you're not allowed
 * to *remove* anything that currently appears in the file.
 */

/** The top of a class hierarchy that encodes regular expressions. */
abstract class RegularExpression {
  /** returns true if the given string matches this regular expression */
  
  def matches(string: String) = RegexMatcher.matches(string, this)
  
  // || represents the Union of this, which is of type RegularExpression, 
  // with "other", which is also of type RegularExpression
  def || (other: RegularExpression) = Union(this, other)
  
  // || represents the Concat of this, which is of type RegularExpression, 
  // with "other", which is also of type RegularExpression
  def ~ (other: RegularExpression) = Concat(this, other) 
  
  def <*> = Star (this)
  
  // another way to do it is def <+> = this ~ (this <*>)
  def <+> = Concat (this, Star(this)) 
  

  def apply(n: Int): RegularExpression = {
    n match {
        case 0 => EPSILON // base case 
        case n if n >= 1 => this ~ apply(n-1) // concat "this" with what "apply" recursively returns 
        // anticipate a possible input error and tries to give an informative error message 
        case _ => error("Illegal argument: n cannot be negative.") // anticipate a possible input error and tries
      }
    }
}
  
object RegularExpression {
  
    // converts from a character to a literal 
   implicit def Char2Lit (c : Char): Literal = new Literal (c)
 
   /* Converts a string to a RegularExpression using concatenation */
    // recursively concatenates each subsequent character in a string until you've gone through entire string  
	  implicit def String2RegExpr(str: String): RegularExpression = {
      if (str.isEmpty)
           EPSILON
      else
           // recursively concat first elt of string to rest 
           str.head ~ (String2RegExpr(str.tail))
    }
}


/** a regular expression that matches nothing */
object EMPTY extends RegularExpression

/** a regular expression that matches the empty string */
object EPSILON extends RegularExpression

/** a regular expression that matches a literal character */
case class Literal(val literal: Char) extends RegularExpression

/** a regular expression that matches either one expression or another */
case class Union(val left: RegularExpression, val right: RegularExpression) 
  extends RegularExpression

/** a regular expression that matches one expression followed by another */
case class Concat(val left: RegularExpression, val right: RegularExpression) 
  extends RegularExpression
  
/** a regular expression that matches zero or more repetitions of another 
 *  expression
 */
case class Star(val expression: RegularExpression) extends RegularExpression

