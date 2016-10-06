package dsls.regex

import ExpressionImplicits._

object Program extends App {
  
  /****************************************************************************
   * Through implicit definitions we are able to write
   *   val zero = '0'
   * and have it understand that as a regular expression
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
   * It is now possible to write
   *   val answer = "42"
   * and have that be accepted as the regular expression of concatenating
   * '4' and '2'
   ***************************************************************************/
  val answer = "42"
  val anotherTest = "531"

  require(answer matches "42")
  require(anotherTest matches "531")
              
  /****************************************************************************
   * The union operator for regular expressions makes it possible to say:
   *   val digit = '0' || '1' || '2' || '3' || '4' || '5' || '6' || '7' || '8' || '9'
   * and have that match with each individual regular expression 
   ***************************************************************************/
  val digit = zero || one || two || three || four || five || six || seven || eight || nine

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
   * The concatenation operator to makes it valid to write:
   *   val pi = '3' ~ '1' ~ '4'
   * which matches with the string "314", which we also defined to be the
   * concatenation of '3', '1', and '4'.
   ***************************************************************************/
  val pi = '3' ~ '1' ~ '4'

  require(pi matches "314")
  
  /****************************************************************************
   * The star operator for regular expressions make it possible to say:
   *   val zeroOrMoreDigits = digit <*>
   * and have that match with any string with 0 or more digits, as written
   * in the union testing.
   ***************************************************************************/
  val zeroOrMoreDigits = digit <*>
  
  require(zeroOrMoreDigits matches "")
  require(zeroOrMoreDigits matches "0")
  require(zeroOrMoreDigits matches "9")
  require(zeroOrMoreDigits matches "09")
  require(zeroOrMoreDigits matches "987651234")
  
  /****************************************************************************
   * The plus operator makes it possible to write:
   *   val number = digit <+> 
   * and have that match with anything that is 0 or more 
   ***************************************************************************/
  val number = digit <+> 
  
  require(!(number matches ""))
  require(number matches "0")
  require(number matches "9")
  require(number matches "09")
  require(number matches "987651234")

  /****************************************************************************
   * It is now possible to write:
   *    val cThree = 'c'{3}
   * where {} is the repition operator. This means that this will be the same
   * as "ccc". 
   ***************************************************************************/
  val cThree = 'c'{3}
  
  require(cThree matches "ccc")
  
  /****************************************************************************
   * Additional patterns
   ***************************************************************************/
  // Pattern 1
  val pattern = "42" || ( ('a' <*>) ~ ('b' <+>) ~ ('c'{3}))
  
  require(pattern matches "42")
  require(pattern matches "bccc")
  require(pattern matches "abccc")
  require(pattern matches "aabccc")
  require(pattern matches "aabbccc")
  require(pattern matches "aabbbbccc")
 
  // Pattern 2
  val helloworld = ("hello" <*>) ~ "world"
  
  require(helloworld matches "helloworld")
  require(helloworld matches "world")
  require(helloworld matches "hellohelloworld")
  
  // Pattern 3
  val telNumber = '(' ~ digit{3} ~ ')' ~ digit{3} ~ '-' ~ digit{4}
  
  require(telNumber matches "(202)456-1111")
}
