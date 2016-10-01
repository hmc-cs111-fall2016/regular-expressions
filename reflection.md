# Reflection on implementing regular expressions of a DSL

## Which operators were easiest to implement and why?

`||` and `~` were easy because I was just renaming `Union` and `Concat`. We weren't making the actual implementation from scratch or anything.

## Which operators were most difficult to implement and why?

Making the implicit `string2regex` was challenging because I had to be conscious of the types I was dealing with. I knew the overall strategy, but I tried too hard to make it a one-liner. However, putting all the functionality on one line made it difficult to debug, especially since types didn't implicitly convert when I wanted them to. I realize now that I could've written string2regex in one line, but it would be too long, so I split it up into readable sections.

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

