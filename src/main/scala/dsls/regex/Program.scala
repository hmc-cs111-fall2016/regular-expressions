package dsls.regex

import scala.language.postfixOps

object Program extends App {
  
  import RegularExpression._
  
  /****************************************************************************
   * Extend characters to support regular expressions
   * 
   * Make it possible to replace the definition of the numbers with:
   *   val zero = '0'
   * etc.
   ***************************************************************************/
  val zero  = '0'
  val one   = '1'
  val two   = '2'
  val three = '3'
  val four  = '4'
  val five  = '5'
  val six   = '6'
  val seven = '7'
  val eight = '8'
  val nine  = '9'
  
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
   * Extend strings to support regular expressions
   * 
   * Make it possible to replace the definition of answer with:
   *   val answer = "42"
   ***************************************************************************/
  val answer = "42"

  require(answer matches "42")
              
  /****************************************************************************
   * Add the union operator for regular expressions
   * 
   * Make it possible to replace the definition of digit with:
   *   val digit = '0' || '1' || '2' || '3' || '4' || '5' || '6' || '7' || '8' || '9' 
   ***************************************************************************/
  val digit = '0' || '1' || '2' || '3' || '4' || '5' || '6' || '7' || '8' || '9' 

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
   * Add the concatenation operator for regular expressions
   * 
   * Make it possible to replace the definition of digit with:
   *   val pi = '3' ~ '1' ~ '4'
   ***************************************************************************/
  val pi = '3' ~ '1' ~ '4'

  require(pi matches "314")
  
  /****************************************************************************
   * Add the star operator for regular expressions
   * 
   * Make it possible to replace the definition of zeroOrMoreDigits with:
   *   val zeroOrMoreDigits = digit <*>
   ***************************************************************************/
  val zeroOrMoreDigits = digit <*>
  
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
  val number = digit <+>
  
  require(!(number matches ""))
  require(number matches "0")
  require(number matches "9")
  require(number matches "09")
  require(number matches "987651234")

   /****************************************************************************
   * Add the repetition operator for regular expressions
   * 
   * Make it possible to replace the definition of cThree with:
   *    val cThree = 'c'{3}
   ***************************************************************************/
  val cThree = 'c'{3}
  
  require(cThree matches "ccc")
  
  /****************************************************************************
   * Additional pattern
   * Once you've added all the operators, it should be possible to replace 
   * the following several definitions with:
   *   val pattern = "42" || ( ('a' <*>) ~ ('b' <+>) ~ ('c'{3}))
   ***************************************************************************/
  val pattern = "42" || ( ('a' <*>) ~ ('b' <+>) ~ ('c'{3}))
  
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
  val helloworld = ("hello" <*>) ~ "world"
  
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
  val telNumber = '(' ~ digit{3} ~ ')' ~ digit{3} ~ '-' ~ digit{4}
  
  require(telNumber matches "(202)456-1111")
}
