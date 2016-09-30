package dsls.regex

import scala.language.implicitConversions

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

  def ||(that: RegularExpression) = Union(this, that)

  def ~(that: RegularExpression) = Concat(this, that)

  // Kleene star - zero or more regex repetitions. Parameter-less method
  def <*> = Star(this)

  def <+> = this ~ this<*>
}

object RegularExpression {
  implicit def char2Regex(ch: Char): RegularExpression = Literal(ch)
  implicit def char2Regex(ch: Char)(n: =>Int): RegularExpression = ch.toString * n

  // for each character, concatenate it to the overall regex
  implicit def string2Regex(s: String): RegularExpression = {
    val charArray: Array[RegularExpression] = s.toCharArray.map(Literal)
    val e: RegularExpression = EPSILON
    charArray.foldLeft(e)(Concat)
  }

  implicit def regexTimesN(rx: RegularExpression)(n: Int): RegularExpression =
    (1 to n).foldLeft(rx)((result, _) => Concat(result, rx))

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
