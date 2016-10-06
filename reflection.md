# Reflection on implementing regular expressions of a DSL

## Which operators were easiest to implement and why?
||, ~, <*>, <+> were all rather easy to implement because they are all pretty
much regular functions in scala.  The literal extension for characters was very
straight forward and was also pretty easy to implement.
## Which operators were most difficult to implement and why?
I couldn't figure out how to implement the {n}
function because it seems like it doesn't have a name.  It looks perhaps like I
could've done something with a curried function which is the way we did some of
the control flow stuff, however there is no name that comes before it either.
Hmmm... now I"m wondering if maybe I could've curried the constructor of a literal
or something like that but I'm doubtful. 

Also, I started to implement the literal
extension for strings using fold instead of foldl, which caused great headache
because fold is more picky about types than foldl.
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

   I think this language is very well designed.  It is easy to say what a pattern
   is and to combine multiple patterns. It is also easy to match a pattern to
   a string, which is critical.  

   It is difficult/impossible to say more than whether a string matches a pattern.
   Lots of modern regex implementations I believe offer ways to have capture
   groups and match certain parts of strings and extract information you want
   from strings using the pattern you specify.  This could be a nice extension
   of this project.

   Overall I like the syntax a lot.  It seems crisp and clear.  I might remove
   the angle brackets from <+> and <*> in which case I would probably have to
   add the override keyword to the function declarations.