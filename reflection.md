# Reflection on implementing regular expressions of a DSL

## Which operators were easiest to implement and why?
I found the binary operators and the star operator intuitive to implement, 
since there were already case classes built for these operators such as Union and
Concat. 

## Which operators were most difficult to implement and why?
I spent a long time trying to think of how to implement the repetition operator,
{n}. Just like other operators like <*> and <+>, I thought the curly braces 
should be the function's name when defining it. However, I was having a problem because there's an integer between the curly braces and I didn't know how to 
include that in the function's name, which made me wonder for a long time if I 
can have a function's own parameter in the name of the function. It also took 
me a long time to actually find out that there's a method 'apply' available for 
situations like this. 

The followings are things that I found frustrating about scala, not specific
operators that I was implementing. 

I did not find it convenient that scala did not throw any error with specific
   reasons when I ran the program without having imported scala.language.implicitConversions and scala.language.postfixOps.
   When I included the line to make sure the input integer is positive, "assume(n>=0)" when defining the repetition operator, {n}, I got the following errors:
    [error] expressions/src/main/scala/dsls/regex/RegularExpression.scala:31: type mismatch;
	[error]  found   : Unit
	[error]  required: dsls.regex.RegularExpression
	[error]     require(n>=0)
	[error]            ^
    [error] expressions/src/main/scala/dsls/regex/RegularExpression.scala:32: not found: value n
	[error]     if (n == 0) EPSILON
	[error]         ^
	[error] expressions/src/main/scala/dsls/regex/RegularExpression.scala:33: not found: value n
	[error]     else if (n ==1) this
	[error]              ^
    [error] expressions/src/main/scala/dsls/regex/RegularExpression.scala:34: not found: value n
	[error]     else Concat(this, apply(n-1))
	[error]                             ^
	However, when I remove the assume line while keeping everything else the 
	same, everything runs smoothly. I wish the error messages were more kind,
	as I still haven't figured out why this is happening. 



## Comment on the design of this internal DSL

Write a few brief paragraphs that discuss:
   # What works about this design? (For example, what things seem easy and
   natural to say, using the DSL?)
   I believe it takes a shorter time for users to get used to using regular
   expression operators than learning to use the existing methods. For example,
   the operator '||' is intuitive enough as opposed to the method 'Union' because 
   the method requres a certain number of inputs in certain ways that users might
   not be familiar with. 

   # What doesn't work about this design? (For example, what things seem
   cumbersome to say?) Think of a syntactic change that might make the language
   better. How would you implement it _or_ what features of Scala would prevent 
   you from implementing it? (You don't have to write code for this part. You 
   could say "I would use literal extension to..." or "Scala's rules for valid
   identifiers prevent...")
   While some regular expression operators are easy to use, there are some 
   exceptions: the Kleene star operator (<*>) and the repetition operator ({n}, 
   where n is the number of repetitions of the expression). They are not even
   one-character long, which means it is as convenient for users to type as other
   operators. Plus, I don't think they are intuitive enough. I would rather have
   "^n" to represent as the repetition operator. Also, I would have just the star 
   operator. I would do the same for the '+'' operator, but I see why it is between
   the angle brackets: there's already universally understood purpose of just the 
   '+' operator.


  