package dsls.regex

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
  def ||(right: RegularExpression): Union =
    Union(this,right)
  def ~(right: RegularExpression): Concat =
    Concat(this,right)
  def <*>(): Star =
    Star(this)
  def <+>(): Concat =
    Concat(this, Star(this))
  def repeat(n: Int): Concat = {
    /** I don't feel that this is as functional as it should be and
     *  I would love some feedback on how to make it better stylistically
     *  I'm also not sure how to get the desired {3} syntax so instead have
     *  implemented it as repeat(3)
     */
    if (n == 1)
      Concat(this,null)
    if (n ==2)
      Concat(this,this)
    val temp = this
    var retVal: Concat = Concat(this,this)
    for (i <- 3 to n) { retVal = Concat(retVal,temp) }
    retVal
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

object RegularExpressionImplicits {
   implicit def CharToLiteral(value : Char) =
                                    new Literal(value)
   implicit def StringToConcat(value : String): Concat =
   {
     val literals : Seq[Literal] = value.map { x => Literal(x) }
     val result = literals.foldLeft(null : Concat) 
     { (z : Concat, i : RegularExpression) =>
       if (z != null)
         // we don't want null's in our concat's so we will check if left or
         // right is null and set either to i instead of null if it is
         if (z.left == null)
           Concat(z.right, i)
         else if (z.right == null)
           Concat(z.left, i)
         else
           Concat(z,i)
       else
         Concat(z,i)
     }
     result
   }
}