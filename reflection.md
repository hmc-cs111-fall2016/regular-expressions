# Reflection on implementing regular expressions of a DSL

## Which operators were easiest to implement and why?

Relatively, the `||`, `~`, and `*` operators were the easiest to implement. They
were all just calling one function, and the hardest part was figuring out how to
call them and in what format.

## Which operators were most difficult to implement and why?

Probably the implicit conversions and the `<+>` operator. The implicit
conversions because I did not understand their purposes at first, and that
prevented me from implementing it correctly.

For chars, it was relatively easy to figure out that a simple `Literal` call
seemed to suffice. However, it took a little more regarding the strings because
I was experimenting different ways of getting the string to become chars that I
could then use the `Literal` or `charToRegex` on. I tried converting the string
into a list or sequence (`toList` and `toSeq`), but I finally settled on using
a `foldLeft` directly on the string. After trial and error, I figured out the
accumulator/base case should be an `EPSILON`, and that was that.

For the `<+>` operator, what threw me off was what to do after the first `~`,
and solified this operators difference from the Kleene Star operator.
I had to revisit stuff from 81 regarding how repetitions were defined so that
I could model what the operator should do. Once I figured that out, the operator
was pretty easy to code.

## Comment on the design of this internal DSL

Write a few brief paragraphs that discuss:
   + What works about this design? (For example, what things seem easy and
   natural to say, using the DSL?)

   It's easy to do the regex as defined in our syntax. So
   `"42" || ( ('a' <*>) ~ ('b' <+>) ~ ('c'{3}))` is easy to say and match.

   + What doesn't work about this design? (For example, what things seem
   cumbersome to say?)

   Some standard regex is hard to do. If I just wanted patterns that were made
   up of the 26 lowercase English characters, instead of writing `[a-z]*`, in
   this DSL, I'd have to write `('a' || 'b' || 'c' || ..... || 'z')*`, which is
   cumbersome and slow / inefficient. Since efficiency is one of the big
   motivations to use regex, this slowness kind of defeats the purpose of regex
   itself.

   + Think of a syntactic change that might make the language better. How would
   you implement it _or_ what features of Scala would prevent you from
   implementing it? (You don't have to write code for this part. You could say
   "I would use literal extension to..." or "Scala's rules for valid
   identifiers prevent...")

   Allowing something like `[a-z]*` to work would be nice! I think figuring out
   an efficient way of signalling that `[a-z]` means characters from a to z
   would start from changing the parsing, and then figuring out to code `-`, and
   then how to store it. In theory, since these are ASCII characters, the
   difference in their values as chars should gives us a range of ASCII chars.
   so the difference between 'z' and 'a' as ASCII values would indicate the
   range from 'a' that the pattern can match.
