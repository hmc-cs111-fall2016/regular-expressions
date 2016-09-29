package dsls.regex

object Program extends App {
  
  /****************************************************************************
   * TODO: Extend characters to support regular expressions
   * 
   * Make it possible to replace the definition of the numbers with:
   *   val zero = '0'
   * etc.
   ***************************************************************************/
  val zero  = Literal('0')
  val one   = Literal('1')
  val two   = Literal('2')
  val three = Literal('3')
  val four  = Literal('4')
  val five  = Literal('5')
  val six   = Literal('6')
  val seven = Literal('7')
  val eight = Literal('8')
  val nine  = Literal('9')
  
  require(zero matches "0")
  require(one matches "1")
  require(two matches "2")
  require(three matches "3")
  require(four matches "4")
  require(five matches "5")
  require(six matches "6")
  require(seven matches "7")
  require(eight matches "8")
  require(nine matches "9")
  
  /****************************************************************************
   * TODO: Extend strings to support regular expressions
   * 
   * Make it possible to replace the definition of answer with:
   *   val answer = "42"
   ***************************************************************************/
  val answer = Concat(four, two)

  require(answer matches "42")
              
  /****************************************************************************
   * TODO: Add the union operator for regular expressions
   * 
   * Make it possible to replace the definition of digit with:
   *   val digit = '0' || '1' || '2' || '3' || '4' || '5' || '6' || '7' || '8' || '9' 
   ***************************************************************************/
  val digit = Union(zero, Union(one, Union(two, Union(three, Union(four, 
              Union(five, Union(six, Union(seven, Union(eight, nine)))))))))

  require(digit matches "0")
  require(digit matches "1")
  require(digit matches "2")
  require(digit matches "3")
  require(digit matches "4")
  require(digit matches "5")
  require(digit matches "6")
  require(digit matches "7")
  require(digit matches "8")
  require(digit matches "9")      

  /****************************************************************************
   * TODO: Add the concatenation operator for regular expressions
   * 
   * Make it possible to replace the definition of digit with:
   *   val pi = '3' ~ '1' ~ '4'
   ***************************************************************************/
  val pi = Concat(Literal('3'), Concat(Literal('1'), Literal('4')))

  require(pi matches "314")
  
  /****************************************************************************
   * TODO: Add the star operator for regular expressions
   * 
   * Make it possible to replace the definition of zeroOrMoreDigits with:
   *   val zeroOrMoreDigits = digit <*>
   ***************************************************************************/
  val zeroOrMoreDigits = Star(digit)
  
  require(zeroOrMoreDigits matches "")
  require(zeroOrMoreDigits matches "0")
  require(zeroOrMoreDigits matches "9")
  require(zeroOrMoreDigits matches "09")
  require(zeroOrMoreDigits matches "987651234")
  
  /****************************************************************************
   * TODO: Add the plus operator for regular expressions
   * 
   * Make it possible to replace the definition of number with:
   *   val number = digit <+> 
   ***************************************************************************/
  val number = Concat(digit, zeroOrMoreDigits)
  
  require(!(number matches ""))
  require(number matches "0")
  require(number matches "9")
  require(number matches "09")
  require(number matches "987651234")

  /****************************************************************************
   * TODO: Add the repetition operator for regular expressions
   * 
   * Make it possible to replace the definition of cThree with:
   *    val cThree = 'c'{3}
   ***************************************************************************/
  val cThree = Concat(Literal('c'), Concat(Literal('c'), Literal('c')))
  
  require(cThree matches "ccc")
  
  /****************************************************************************
   * Additional pattern
   * Once you've added all the operators, it should be possible to replace 
   * the following several definitions with:
   *   val pattern = "42" || ( ('a' <*>) ~ ('b' <+>) ~ ('c'{3}))
   ***************************************************************************/
  val aStar = Star(Literal('a'))
  val bPlus = Concat(Literal('b'), Star(Literal('b')))
  val pattern = Union(answer, Concat(aStar, Concat(bPlus, cThree)))
  
  require(pattern matches "42")
  require(pattern matches "bccc")
  require(pattern matches "abccc")
  require(pattern matches "aabccc")
  require(pattern matches "aabbccc")
  require(pattern matches "aabbbbccc")
 
   /****************************************************************************
   * Additional pattern
   * 
   * Once you've added all the operators, it should be possible to replace 
   * the following several definitions with:
   *   val helloworld = ("hello" <*>) ~ "world"
   ***************************************************************************/
  val hello = Concat(Literal('h'), Concat(Literal('e'), Concat(Literal('l'), 
              Concat(Literal('l'), Literal('o'))))) 
  
  val world = Concat(Literal('w'), Concat(Literal('o'), Concat(Literal('r'), 
              Concat(Literal('l'), Literal('d'))))) 

  val helloworld = Concat(Star(hello), world)
  
  require(helloworld matches "helloworld")
  require(helloworld matches "world")
  require(helloworld matches "hellohelloworld")
  
   /****************************************************************************
   * Additional pattern
   * 
   * Once you've added all the operators, it should be possible to replace 
   * the following several definitions with:
   *   val telNumber = '(' ~ digit{3} ~ ')' ~ digit{3} ~ '-' ~ digit{4}
   ***************************************************************************/
  val threeDigits = Concat(digit, Concat(digit, digit))
  val fourDigits = Concat(threeDigits, digit)
  val areaCode = Concat(Literal('('), Concat(threeDigits, Literal(')')))
  val telNumber = Concat(areaCode, Concat(threeDigits, Concat(Literal('-'), fourDigits)))
  
  require(telNumber matches "(202)456-1111")
}
