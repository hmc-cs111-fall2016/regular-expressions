package dsls.regex

/**
 * Modify this file to implement an internal DSL for regular expressions. 
 * 
 * You're allowed to add anything you want to this file, but you're not allowed
 * to *remove* anything that currently appears in the file.
 */

import scala.language.implicitConversions
import scala.language.postfixOps

/** The top of a class hierarchy that encodes regular expressions. */
abstract class RegularExpression {
  /** returns true if the given string matches this regular expression */
  def matches(string: String) = RegexMatcher.matches(string, this)

  def ||(other: RegularExpression) = Union(this, other)

  def ~(other: RegularExpression) = Concat(this, other)

  def <*> = Star(this)

  /** Returns a regular expression consisting of one or more of `this`*/
  def <+> = Concat(this, Star(this))

  /** Returns a regular expression consisting of n copies of `this`
  For example 'ab'{3} matches 'ababab' */
  def apply(n: Int): RegularExpression = {
  	if (n == 0)
  		EPSILON
  	else
  		Concat(this, this(n-1))
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

object RegularExpression {

	implicit def Char2RegularExpression(n : Char): RegularExpression = Literal(n)
	
	/* Converts a string to a regular expression by concatenating literal characters */
	implicit def String2RegularExpression(str: String): RegularExpression = {
		if (str.isEmpty)
			EPSILON
		else if (str.length == 1)
			str.head
		else
			/* recursively calls the implicit conversion of str.tail*/
			Concat(str.head, str.tail)
	}
}

