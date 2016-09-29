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
  def matches(char: Char) = RegexMatcher.matches(char.toString, this)
  
  /** the union operator */
  def ||(other: RegularExpression) = Union(this, other)
  def ∪(other: RegularExpression) = this || other
  
  /** the concatenation operator */
  def ~(other: RegularExpression) = Concat(this, other)
  def ⋅(other: RegularExpression) = this ~ other
  
  /** the star operator (zero or more repetitions) */
  def <*> = Star(this)
  def * = this <*>

  /** the plus operator (one or more repetitions) */
  def <+> = this ~ (this <*>)
  def + = this <+>

  /** the repetition operator (n repetitions) */
  def apply(n: Int): RegularExpression = {
    require(n >= 0)
    if (n == 0)
      EPSILON
    else
      this ~ this{n - 1}
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

/**
 * A companion object that extends chars and strings to regexes
 * (and adds other sugar)
 */
object RegularExpression {
  // convert characters to regular expressions
  implicit def charToRegex(c: Char): RegularExpression = Literal(c)

  // convert strings to regular expressions
  implicit def stringToRegex(s: String): RegularExpression = {
    val chars: List[RegularExpression] = s.toList map Literal
    val initial: RegularExpression = EPSILON
    (chars :\ initial)(Concat)
  }
  
  // syntactic sugar
  val ε : RegularExpression = EPSILON
  val ∅ : RegularExpression = EMPTY
  
  // this might be too ridiculous, but it lets us say:
  //   string ∈ pattern   or   char ∈ pattern
  // and
  //   string ∉ pattern   or   char ∉ pattern
  case class Flipper(s: String) {
    def ∈(pattern: RegularExpression) = pattern matches s
    def ∉(pattern: RegularExpression) = !(pattern matches s) 
  }
  implicit def stringToMatcher(s: String) = Flipper(s)
  implicit def charToMatcher(c: Char) = Flipper(c.toString)
  
  // this bit lets us pattern-match on unions, using the binary operators
  object || {
    def unapply(arg: Union): Option[(RegularExpression, RegularExpression)] = 
      Some(arg.left, arg.right)
  }
  object ∪ {
    def unapply(arg: Union): Option[(RegularExpression, RegularExpression)] = 
      Some(arg.left, arg.right)
  }
  
  // this bit lets us pattern-match on concatenations, using the binary operators
  object ~ {
    def unapply(arg: Concat): Option[(RegularExpression, RegularExpression)] = 
      Some(arg.left, arg.right)
  }
  object ⋅ {
    def unapply(arg: Concat): Option[(RegularExpression, RegularExpression)] = 
      Some(arg.left, arg.right)
  }
}
