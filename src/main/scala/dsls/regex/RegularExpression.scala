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
  
  /** Binary operator corresponding to Union. Returns the new regular expression. */
  def ||(regEx: RegularExpression) = Union(this, regEx)
  
  /** Binary operator corresponding to Concatination. Returns the new regular expression */
  def ~(regEx: RegularExpression) = Concat(this, regEx)
  
  /** Postfix "Kleene Star" operator indicating 0 or more reps of a pattern. Returns new regular expression. */
  def <*> = Star(this)
  
  /** Postfix operator indicating 1 or more reps of a pattern. Returns new regular expression. */
  def <+> = Concat(this, Star(this))
  
  /** Implementation of the Repetition Operator. Returns new regular expression. */
  def apply(reps: Int): RegularExpression = {
    reps match {
      case 0 => EPSILON
      case n if n > 0 => Concat(this, this{n-1}) 
      case _ => error("Illegal argument.") // Cannot have negative repetitions of a pattern.
    }
  }
}

/** Regular expression companion object to implicit conversions */
object RegularExpression { 
  implicit def string2regularExpression(s: String) = s.foldRight(EPSILON: RegularExpression)({(l, acc) => Concat(Literal(l), acc)})
  implicit def char2literal(c: Char) = Literal(c)
}

/** a regular expression that matches nothing */
object EMPTY extends RegularExpression

/** a regular expression that matches the empty string */
object EPSILON extends RegularExpression

/** a regular expression that matches a literal character */
case class Literal(val literal: Char) extends RegularExpression

/** a regular expression that matches either one expression or another */
case class Union(val left: RegularExpression, val right: RegularExpression) 
  extends RegularExpression {
}

/** a regular expression that matches one expression followed by another */
case class Concat(val left: RegularExpression, val right: RegularExpression) 
  extends RegularExpression
  
/** a regular expression that matches zero or more repetitions of another 
 *  expression
 */
case class Star(val expression: RegularExpression) extends RegularExpression
