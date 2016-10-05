# Reflection on implementing regular expressions of a DSL

## Which operators were easiest to implement and why?
The Char implicit definition was the easiest to implement. This was because
it were basically already defined in the language as a Literal, it's just 
slightly clunkier to write out `Literal ('c')` rather than just `'c'` so
implicit definitions made it much easier.

## Which operators were most difficult to implement and why?
The reptition operator was the most difficult to implement. This is because,
based on how Scala is written, `'c'{3}` looks like a parameter for a character
or a parameter for a Regular Expression because we're able to change `'c'` 
implicitly. We don't want this to be a parameter, but a function, but this is not
what it looks like to Scala. So, we had to use a special property of Scala to be
able to write this.

## Comment on the design of this internal DSL
In this DSL, it works very well to 

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
   
