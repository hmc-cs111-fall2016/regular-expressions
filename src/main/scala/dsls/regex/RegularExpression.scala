package dsls.regex

import scala.language.implicitConversions
/**
 * Modify this file to implement an internal DSL for regular expressions. 
 * 
 * You're allowed to add anything you want to this file, but you're not allowed
 * to *remove* anything that currently appears in the file.
 */

/** The top of a class hierarchy that encodes regular expressions. */
abstract class RegularExpression{
  /** returns true if the given string matches this regular expression */
  def matches(string: String) = RegexMatcher.matches(string, this)

  // union operator 
  def ||(in: RegularExpression) = Union(in, this)

  // concatenation operator
  def ~(in: RegularExpression) = Concat(in, this)

  // star operator (zero or more repititions)
  def <*> = Star(this)

  // plus operator (one or more repititions)
  def <+> = Concat(this, Star(this))

  // the repitition operator (n repititions)
  def apply(n: Int): RegularExpression = {
  	if (n == 0) EPSILON
  	else Concat(this, apply(n-1))
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

/* Comapanion class */
object RegularExpression {
	// Implicitly convert Chars to RegEx
	implicit def char2regex(c: Char): Literal = Literal(c)

	// Implicitly convert Strings to RegEx
	implicit def string2regex(s: String): RegularExpression = {
		val charList: Array[RegularExpression] = s.toArray.map(Literal)
		charList.foldLeft(EPSILON: RegularExpression)(Concat)
	}
}