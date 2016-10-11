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


  /** Binary Operators */
  def ||(another: RegularExpression) = Union(this, another)  //union operator
  def ~(another: RegularExpression) 
  							 = Concat(this, another) //concatenation operator 
 
  /** Postfix Operators */
  def <*> = Star(this) //Kleene star operator
  def <+> = 
  	Concat( this, (this<*>) )//One of more repetitions of the preceding pattern

  /** Repetition Operator : {n} */
  def apply(n:Int): RegularExpression = 
    //assume(n>=0)
    if (n == 0) EPSILON
    else if (n ==1) this
    else Concat(this, apply(n-1))
 
}  

object RegularExpression {
  // Converts a character into a literal
	implicit def charToLit(c:Char) : Literal = Literal(c)
  // Converts a string into a regular expression
  implicit def strToReg(s:String) : RegularExpression =
    if (s.length() == 0) EPSILON
    else Concat(charToLit(s.head), strToReg(s.tail))
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
