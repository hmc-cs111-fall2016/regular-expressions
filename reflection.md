# Reflection on implementing regular expressions of a DSL

## Which operators were easiest to implement and why?
   The easiest operators to implement were the ones for Union, Concatenation. Once I implemented Union, doing Concatenation was trivial because it had the same structure. Kleene Star and <+> were a bit more interesting because I had to figure out how to define a postfix operator, in particular, how to access the value before the operator. The implementation on <+> was simply building on top of the Kleene Star to match one more literal/expression. 

## Which operators were most difficult to implement and why?
   The hardest one to implement was the apply function to return a regular expression that matches n reptitions of the element. I wasn't aware that apply is a special function and was trying to write it with an implicit function. Implementing implicit functions within the companion object was also hard at first. While it wasn't that much actual code to implement, it was a little confusing initially to understand how it worked. 

## Comment on the design of this internal DSL

Write a few brief paragraphs that discuss:
   + What works about this design? (For example, what things seem easy and
   natural to say, using the DSL?)
   
   It's very easy to write regular expressions in this DSL in a clean, intuitive way. Users no longer have to deal with the intermediate functions which are more clunky to use, in particular Union which has a recursive structure. 
   
   + What doesn't work about this design? (For example, what things seem
   cumbersome to say?)
   Think of a syntactic change that might make the language better. How would
   you implement it _or_ what features of Scala would prevent you from
   implementing it? (You don't have to write code for this part. You could say
   "I would use literal extension to..." or "Scala's rules for valid
   identifiers prevent...")   
   
   The Kleene star (<*>) and <+> operations still seems cumbersome. It would be cleaner to remove the symbols '<' and '>' around the '*' and '+' so that it would mirror the actual syntax for regular expressions. This may not implementable due to these '*' and '+' having a predefined meaning in Scala. 

   
   
