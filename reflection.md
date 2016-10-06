# Reflection on implementing regular expressions of a DSL

## Which operators were easiest to implement and why?

I thought the easiest operators to implement were || and ~. The method for implementing these was very similar to the example with complex numbers in class. There is no recursion to consider -- all it required was to use a method, with 2 arguments, that returned the 



## Which operators were most difficult to implement and why?

Something that I struggled with was getting  " val cThree = 'c'{3} " to work. The error message kept displaying that Char('c') did not take parameters, even though I wrote a method, "  implicit def Char2Lit (c : Char) = new Literal (c); " to convert from character to literal. 

I also thought that implementing 

## Comment on the design of this internal DSL

Write a few brief paragraphs that discuss:
   + What works about this design? (For example, what things seem easy and
   natural to say, using the DSL?)
   
   It's much more convenient to chain literals together in a union. Under the previous language, the user had to specify the exact "associative order" of what pairs of literals are put into a union first, before which other pairs of literals are added on as unions. It's much simpler just to say '0' || '1' || '2' || '3' || '4' || '5' || '6' || '7' || '8' || '9' and not have to specify the order that characters are chained together in an expression that expresses a union of elements. 
 
   
   
   + What doesn't work about this design? (For example, what things seem
   cumbersome to say?)
   
What seems a bit more cumbersome to say the following: " val telNumber = '(' ~ digit{3} ~ ')' ~ digit{3} ~ '-' ~ digit{4} ". However, given the flexibility and constraints we want users to be able to follow in creating a valid phone number, I honestly cannot think of a simpler solution that does not compromise either the flexibility and/or the constraints. The flexibility includes being able to choose any 3 digits between the parentheses, to choose any of the 3 digits before the dash, and to choose any 4 digits after the dash. The constraints include making sure that only (, ), -, and digits are included. Given the specific function of each element in the series of characters that compose a valid tedNumber, I can't think of any simpler way to say this (although this isn't a critique of the design -- this is my way to express how the design is "cumbersome" but as simple as I think it can get). 
   
  
   + Think of a syntactic change that might make the language better. How would
   you implement it _or_ what features of Scala would prevent you from
   implementing it? (You don't have to write code for this part. You could say
   "I would use literal extension to..." or "Scala's rules for valid
   identifiers prevent...")
   
