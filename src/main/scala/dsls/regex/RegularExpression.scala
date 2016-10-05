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

  /** returns a regular expression matching either one sub-expression or another */  
  def ||(other: RegularExpression) = Union(this, other)

  /** returns a regular expression matching one sub-expression followed by another */  
  def ~(other: RegularExpression) = Concat(this, other)

  /** returns a regular expression matching zero or more repetitions of the expression */  
  def <*> = Star(this)

  /** returns a regular expression matching one or more repetitions of the expression */  
  def <+> = {
  	val zeroOrMoreDigits = Star(this)
  	Concat(this, zeroOrMoreDigits)
  }

  /** returns a regular expression matching n repetitions of the expression */  
  def apply(n: Int): RegularExpression = {
  	if(n==0) EPSILON
  	else Concat(this, this{n-1})
  }


}

object RegularExpression{
	implicit def charToLiteral(c : Char) = Literal(c)
	implicit def stringToRegex(s: String): RegularExpression = charListHelper(s.toList)

	implicit def charListHelper(list: List[Char]): RegularExpression = list match{
		case List() => EPSILON
		case c::rest => Concat(c,rest)
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
