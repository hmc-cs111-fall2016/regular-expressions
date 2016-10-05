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

  /** returns a regular expression matching either of its subexpressions */
  def ||(other: RegularExpression) = Union(this, other)

  /** returns a regular expression matching the left expression followed by the
   *  right
   */
  def ~(other: RegularExpression) = Concat(this, other)

  /** returns a regular expression matching 0 or more repetitions of the
   *  subexpression
   */
  def <*> = Star(this)

  /** returns a regular expression matching 1 or more repetitions of the
   *  subexpression
   */
  def <+> = Concat(this, Star(this))

  /** returns a regular expression matching exactly n repetitions of the
   *  subexpression
   */
  def apply(n: Int):RegularExpression = if (n == 0)
                                          EPSILON
                                        else
                                          Concat(this, this{n-1})
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
  /** Conversion from Char to RegularExpression */
  implicit def char2regex(c: Char): Literal = Literal(c)
  
  /** Conversion from String to RegularExpression */
  implicit def string2regex(s: String): RegularExpression = if (s.isEmpty)
                                                              EPSILON
                                                            else
                                                              Concat(s.head,
                                                                     s.tail)
}
