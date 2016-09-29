package dsls.regex

object RegexMatcher {
  /**
   * returns true if the given string matches the given pattern
   */
  def matches(string: String, pattern: RegularExpression): Boolean = 
    if (string.isEmpty)
      matchesEpsilon(pattern)
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
   *     ∂c( L1 ∪ L2 ) = ∂c( L1 ) ∪ ∂c( L2 )
   *     
   *     ∂c( L1 ⋅ L2 ) = ∂c( L1 ) ⋅ L2              if ε ∉ L1 
   *                    (∂c( L1 ) ⋅ L2) ∪ ∂c( L2 )  otherwise
   *                    
   *     ∂c( L* ) = ∂c( L ) ⋅ L*
   */
  def ∂(c: Char, pattern: RegularExpression): RegularExpression = 
    pattern match {
        case EMPTY | EPSILON ⇒ EMPTY        
        case Literal(d)      ⇒ if (c == d) EPSILON else EMPTY
        case Union(l1, l2)   ⇒ Union(∂(c, l1), ∂(c, l2))
        case Concat(l1, l2)  ⇒ if (!matchesEpsilon(l1)) 
                                  Concat(∂(c, l1), l2)
                                else
                                  Union(Concat(∂(c, l1),  l2), ∂(c, l2))
        case Star(expr)      ⇒ Concat(∂(c, expr),  pattern)
    }

  /**
   * returns true if the empty string matches the pattern
   */
  def matchesEpsilon(pattern: RegularExpression): Boolean = pattern match {
    case EPSILON | Star(_) ⇒ true
    case Union(a, b)   ⇒ matchesEpsilon(a) || matchesEpsilon(b)
    case Concat(a, b)  ⇒ matchesEpsilon(a) && matchesEpsilon(b)
    case _             ⇒ false
  }
}