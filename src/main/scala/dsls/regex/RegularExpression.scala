package dsls.regex
import regexImplicits._

/**
 * Modify this file to implement an internal DSL for regular expressions. 
 * 
 * You're allowed to add anything you want to this file, but you're not allowed
 * to *remove* anything that currently appears in the file.
 */

 object regexImplicits {
 	implicit def charToLiteral(c : Char): RegularExpression =
 		Literal(c)
 	
 	implicit def stringToRegularExpression(s: String): RegularExpression = {
 		var firstConcat = Concat(Literal(s.charAt(0)),Literal(s.charAt(1)))

 		s.substring(2).toList.foldLeft(firstConcat) { (result, element) =>
 							   		Concat(result, Literal(element))
 							   	}
 	}
 }


/** The top of a class hierarchy that encodes regular expressions. */
abstract class RegularExpression {
  	/** returns true if the given string matches this regular expression */
  	def matches(string: String) = RegexMatcher.matches(string, this)

	def ||(other: RegularExpression) = {
		Union(this, other)
	}

	def ~(other: RegularExpression) = {
		Concat(this, other)
	}

	def <*> = {
		Star(this)
	}

	def <+> = {
		Concat(this, Star(this))
	}

	def apply(n: Integer) = {
		var regularExpr = this
		for( a <- 1 to (n-1)) {
			regularExpr = Concat(regularExpr, this)
		}
		regularExpr
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
