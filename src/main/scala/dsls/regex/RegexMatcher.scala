package dsls.regex

import scala.language.implicitConversions

object RegexMatcher {
  import RegularExpression._   // might as well take advantage of the DSL :)
  
  // add a bit more DSL-ness (this definitely crosses the border of ridiculous)
  // but it lets us say:
  //    ε ∈ language
  // and
  //    ε ∉ language
  object EpsilonChecker {
    def ∈(language: RegularExpression) = matchesEpsilon(language)
    def ∉(language: RegularExpression) = !(matchesEpsilon(language)) 
  }
  implicit def epsilonToChecker(e: ε.type) = EpsilonChecker
  
  /**
   * returns true if the given string matches the given pattern
   */
  def matches(string: String, pattern: RegularExpression): Boolean = 
    if (string.isEmpty)
      ε ∈ pattern
    else 
      matches(string.tail, ∂(string.head, pattern))      

  /**
   *   The derivative of a regular expression, with respect to a character. 
   *   This parsing technique is based on a 1964 paper by Janusz A. Brzozowski:
   *       http://dl.acm.org/citation.cfm?doid=321239.321249
   *   
   *   The rules are as follows:
   *   
   *     ∂c( ∅ ) = ∅
   *     
   *     ∂c( {ε} ) = ∅
   *     
   *     ∂c( {d} ) = {ε} if c = d; ∅ otherwise
   *     
   *     ∂c( l1 ∪ l2 ) = ∂c( l1 ) ∪ ∂c( l2 )
   *     
   *     ∂c( l1 ⋅ l2 ) = ∂c( l1 ) ⋅ l2              if ε ∉ l1 
   *                    (∂c( l1 ) ⋅ l2) ∪ ∂c( l2 )  otherwise
   *                    
   *     ∂c( l* ) = ∂c( l ) ⋅ l*
   */
  def ∂(c: Char, pattern: RegularExpression): RegularExpression = 
    pattern match {
        case `∅` | `ε`       ⇒ ∅        
        case Literal(d)      ⇒ if (c == d) ε else ∅
        case l1 ∪ l2         ⇒ ∂(c, l1) ∪ ∂(c, l2)
        case l1 ⋅ l2         ⇒ if (ε ∉ l1)
                                  ∂(c, l1) ⋅ l2
                                else
                                  (∂(c, l1) ⋅ l2) ∪ ∂(c, l2)
        case Star(l)         ⇒ ∂(c, l) ⋅ pattern
    }

  /**
   * returns true if the empty string matches the pattern
   */
  def matchesEpsilon(pattern: RegularExpression): Boolean = pattern match {
    case `ε` | Star(_) ⇒ true
    case a ∪ b         ⇒ (ε ∈ a) || (ε ∈ b)
    case a ⋅ b         ⇒ (ε ∈ a) && (ε ∈ b)
    case _             ⇒ false
  }
}