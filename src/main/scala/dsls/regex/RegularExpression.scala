package dsls.regex

import scala.language.implicitConversions
import scala.language.postfixOps


/** The top of a class hierarchy that encodes regular expressions. */
abstract class RegularExpression {
  /** returns true if the given string matches this regular expression */
  def matches(string: String) = RegexMatcher.matches(string, this)

  /** returns a new RegularExpression representing the union of this
   *  expression and other
   */
  def ||(other: RegularExpression) = Union(this, other)

  /** returns a new RegularExpression representing the concatenation of
   *  this expression and other
   */
  def ~(other: RegularExpression) = Concat(this, other)

  /** returns a new RegularExpression representing the Kleene star of
   *  this expression, ie EPSILON || this || this~this || ...
   */
  def <*> = Star(this)

  /** returns a new RegularExpression which matches one or more
   *  repetitions of this expression.
   */
  def <+> = this ~ (this <*>)

  def apply(n: Int): RegularExpression =
  if (n == 0)
    EPSILON
  else
    this ~ this{n - 1}

  def apply(regex: RegularExpression): RegularExpression = this ~ regex
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

/** implicit conversions for RegularExpression
 *
 */
object RegularExpression {

  implicit def char2regex(c: Char): RegularExpression = Literal(c)

  implicit def string2regex(s: String): RegularExpression = charList2regex(s.toList)

  implicit def charList2regex(l: List[Char]): RegularExpression = l match {
    case List() => EPSILON
    case c :: cs => Concat(c, cs)
  }
}
