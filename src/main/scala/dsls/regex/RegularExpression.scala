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

  def ||(second: RegularExpression) = Union(this, second)

  def ~(second: Regular Expression) = Concat(this, second)

  def <*> = Star(this)

  def <+> = this ~ (this <*>)

  def apply(repetitiosn: Int): RegularExpression = {
    if (n == 0) EPSILON else this ~ this{n - 1}
}

object RegularExpression {
  implitic def stringToRegex(str: String) = str.foldLeft(EPSILON: RegularExpression)({(l, acc) => Concat(Literal(l), acc)})

  implicit def charToRegex(char: Char) = Literal(c)

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
