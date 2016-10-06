package dsls.regex

// Allows for the conversion of strings and chars to Regexes
import scala.language.implicitConversions
// Allows for star to be written as <*> (syntactic sugar)
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
  /** returns a union of two regular expression */
  def ||(other: RegularExpression) = Union(this, other)
  /** returns the concatenation of two regular expressions */
  def ~(other: RegularExpression) = Concat(this, other)
  /** returns the stared version of the regular expression */
  def <*>() = Star(this)
  /** returns a regex which requires one copy of the regex
      followed by any number of copies following (via Star) */
  def <+>() = Concat(this, Star(this))
  /** returns a regex which is n concatenations of the regex */
  def apply(n: Int): RegularExpression = {
    if (n == 1) {
      this
    } else {
      Concat(this, this {n - 1})
    }
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

/** Ensure that we can perform implicit conversions 
 *  from chars and strings to our RegularExpressions
 */
object RegularExpressionImplicit {
  implicit def char2Literal(lit: Char) = new Literal(lit)
  implicit def string2Regex(str: String): RegularExpression = {
    str.foldLeft(EPSILON: RegularExpression)(
        (regex, c) => Concat(regex, Literal(c)))
  }
}


