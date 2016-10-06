package dsls.regex

//we should use htis line to enable implicit conversions
import scala.language.implicitConversions
import LiteralImplicits._
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
  def <+> = Concat(this, Star(this))

  // for making a {n} method I was very confused how to do it 
  // I was trying to create a different Literal class that could
  // accept two parameters (but would give a defaut value for n)
  // but realized that didn't seem right. I also tried making an implicit
  // method that would take a char and a num or a literal and a num and return
  // the proper RegularExpression. I couldnt get this to work. Eventually stack
  // overflow http://stackoverflow.com/questions/3551366/can-i-overload-paranthesis-in-scala
  // led me to the apply method in order to overload the braces
  // I am not sure if this is the proper way but it passed my very singular
  // test. (when I did for all n: Int, the loop took too long to run so its
  // a simple test.)
  def apply(num: Int): RegularExpression = {
    var n: RegularExpression = EPSILON
    var count = num
    while(count > 0){
        n = Concat(this, n)
        count = count - 1
    }
    n
  }
  // {n} will need to use the curried function maybe

}


/** a regular expression that matches nothing */
object EMPTY extends RegularExpression 

/** a regular expression that matches the empty string */
object EPSILON extends RegularExpression 

/** a regular expression that matches a literal character */
case class Literal(literal: Char) extends RegularExpression 


// or a char and num to literal

// Literal Companion class for implicitConversions
object LiteralImplicits {
    implicit def char2literal(literal: Char): Literal = Literal(literal)

    implicit def string2regexp(literal: String): RegularExpression = {
        if (literal == ' ')
            EPSILON
        else
            Concat(Literal(literal.charAt(0)), string2regexp(literal.substring(1)))

    }

    // implicit def litandnum2regexp(literal: Literal, num: Int): RegularExpression = {
    //     if (num == 0)
    //         EPSILON
    //     else
    //         Concat(literal, litandnum2regexp(literal, num - 1))
    // }
}

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




