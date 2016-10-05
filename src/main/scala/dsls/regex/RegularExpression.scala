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

  def ||(other: RegularExpression) = Union(this, other)

  def ~(other: RegularExpression) = Concat(this, other)

  def <*> = Star(this)

  def <+> = {
  	val zeroOrMoreDigits = Star(this)
  	Concat(this, zeroOrMoreDigits)
  }

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
