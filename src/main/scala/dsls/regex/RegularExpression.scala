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

  // Binary operator for Union
  def ||(right: RegularExpression) = Union(this, right)

  // Binary operator for concatination (Concat)
  def ~(right: RegularExpression) = Concat(this, right)
  
  // Postfix operator for Star
  def <*> = Star(this)

  /** Postfix operator <+>, 
   *  which means "one or more repetitions" of the expression.
   */
  def <+> = this ~ this<*>
  
  /** The repetition operator r{n},
   *  for a RegularExpression r.
   *  Takes in an integer n > 0.
   *  Concatenates n copies of the regular expression together.
   *  Should be called using curly braces.
   */
  def apply(n: Int): RegularExpression = 
    if (n == 1) 
      this
    else
      Concat(this, apply(n-1))
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

// Companion object for implicit conversions
object RegularExpression {
  // Literal extension for strings
  implicit def String2RegularExpression(string: String): RegularExpression = 
    string match {
      case ""        => EPSILON
      case s: String => Concat(Literal(s.head), 
                               String2RegularExpression(s.tail))
    }

  // Literal extension for characters
  implicit def Char2RegularExpression(literal: Char) = Literal(literal)
}
