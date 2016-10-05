# Reflection on implementing regular expressions of a DSL

## Which operators were easiest to implement and why?

Union, concatenation, and star operators were the easiest to implement. This is because there were functions already in the regex library that did these operations. As a result, the operators are just simple wrappers around a function.

## Which operators were most difficult to implement and why?
The other operators <+> and {n} were harder to implement. <+> is an operator not in the regex library and required a bit of thought to write the operator. The {n} was the hardest because it required using the apply function. From there, I had a bit of difficulty using recursion, so I used a for loop to implement it.

## Comment on the design of this internal DSL

Write a few brief paragraphs that discuss:
   + What works about this design? (For example, what things seem easy and
   natural to say, using the DSL?)
   + What doesn't work about this design? (For example, what things seem
   cumbersome to say?)
   + Think of a syntactic change that might make the language better. How would
   you implement it _or_ what features of Scala would prevent you from
   implementing it? (You don't have to write code for this part. You could say
   "I would use literal extension to..." or "Scala's rules for valid
   identifiers prevent...")


The easiest things to say are things with repetition. For me, regular expressions can be strong when using union operators with the Kleene star. One can then specify patterns that can be arbitrarily long (with the Kleene star) and relatively complex using the union operator within the Kleene star. In fact, the family of operators <*>, <+>, and {n} are all related to doing things with repetition. As a result, we can be very specific about our repetitions.
