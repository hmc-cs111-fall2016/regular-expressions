# Reflection on implementing regular expressions of a DSL

## Which operators were easiest to implement and why?

I thought the easiest operators to implement were || and ~. The method for implementing these was very similar to the example with complex numbers in class. There is no recursion to consider!

## Which operators were most difficult to implement and why?

Something that I struggled with was getting  " val cThree = 'c'{3} " to work. The error message kept displaying that Char('c') did not take parameters, even though I wrote a method, "  implicit def Char2Lit (c : Char) = new Literal (c); " to convert from character to literal. 

It also took me a while to understand " object RegularExpression " versus "abstract class RegularExpression ", and the relationship between the two. 

## Comment on the design of this internal DSL

Write a few brief paragraphs that discuss:
   + What works about this design? (For example, what things seem easy and
   natural to say, using the DSL?)
   
   It's much more convenient to chain literals together in a union. Under the previous language, the user had to specify the exact "associative order" of what pairs of literals are put into a union first, before which other pairs of literals are added on as unions. It's much simpler just to say '0' || '1' || '2' || '3' || '4' || '5' || '6' || '7' || '8' || '9' and not have to specify the order that characters are chained together in an expression that expresses a union of elements. 
 
   + What doesn't work about this design? (For example, what things seem
   cumbersome to say?)
   
What seems a bit more cumbersome to say the following: " val telNumber = '(' ~ digit{3} ~ ')' ~ digit{3} ~ '-' ~ digit{4} ". However, given the flexibility and constraints we want users to be able to follow in creating a valid phone number, I honestly cannot think of a simpler solution that does not compromise either the flexibility and/or the constraints. The flexibility includes being able to choose any 3 digits between the parentheses, to choose any of the 3 digits before the dash, and to choose any 4 digits after the dash. The constraints include making sure that only (, ), -, and digits are included. Given the specific function of each element in the series of characters that compose a valid tedNumber, I can't think of any simpler way to say this (although this isn't a critique of the design -- this is my way to express how the design is "cumbersome" but as simple as I think it can get). Overall, the < > surrounding * and surrounding + make the language look cumbersome, but then again, I really can't come up with an alternative way to express what <*> and <+> express.
   
 
   + Think of a syntactic change that might make the language better. How would
   you implement it _or_ what features of Scala would prevent you from
   implementing it? (You don't have to write code for this part. You could say
   "I would use literal extension to..." or "Scala's rules for valid
   identifiers prevent...")

I actually have a question about how Scala itself is designed. Consider the two declarations:

 def || (other: RegularExpression) = Union(this, other)

and

case class Literal(val literal: Char) extends RegularExpression .

I think it's interesting that the argument for def || is "other: RegularExpression" whereas the argument for case class Literal is "val literal: Char". I knew to add "val" for case class Literal and not for def || because of look at previous examples, but I don't think I understand Scala enough to reason out, for myself, why one requires "val" and why the other does not. 

Additionally, I think it would be interesting to implement a more complicated version of digit <*>. As of now, strings such as "",  "00239", and "371987049187" are all valid, because digit <*> can express any number of digits. I think it would be interesting to specify a specific range of the number of digits that you would allow. Off the top of my head, the most intuitive way to represent this would be "val rangeInNumOfDigits = digit <*6><*10>", where as 6 and 10 are the lower and upper limits, inclusive, of the number of digits that can be allowed. Of course, I'm not considering the difficult of parsing when I say that "digit <*6><*10>" is the "best" way to represent this sort of expression. However, as a user, this would be the first thing I would try to write to express what I want to express. 
   
