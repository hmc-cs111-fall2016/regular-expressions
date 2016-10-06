package dsls.regex

import scala.language.implicitConversions

/** The top of a class hierarchy that encodes regular expressions. */
abstract class RegularExpression {
  /** returns true if the given string matches this regular expression */
  def matches(string: String) = RegexMatcher.matches(string, this)
  
  /** Our syntatic sugar functions to let the user use
   *    ||, ~, <*>, <+>, and {}
   *  as makes sense to our user. For {}, we use apply
   *  because Scala must have a 
   */
  def ||(right: RegularExpression) = Union(this, right)
  def ~(right: RegularExpression) = Concat(this, right)
  def <*> = Star(this)
  def <+> = Concat(this, Star(this))
  def apply(i: Int):RegularExpression =
    if (i == 0) EPSILON else Concat(this, this.apply(i-1))
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

/** the implicit definitions to add syntatic sugar so that Chars and Strings
    can be used as regular expressions*/
object ExpressionImplicits {
  implicit def char2literal(c: Char): Literal = Literal(c)
  implicit def string2literal (s: String): RegularExpression =
    if (s.length == 0) EPSILON else Concat(Literal(s.charAt(0)), s.substring(1))
}
