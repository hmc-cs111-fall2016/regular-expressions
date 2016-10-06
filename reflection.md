# Reflection on implementing regular expressions of a DSL

## Which operators were easiest to implement and why?

`||` and `~` were easy because I was just renaming `Union` and `Concat`. We 
weren't making the actual implementation from scratch or anything.

## Which operators were most difficult to implement and why?

Making the implicit `string2regex` was challenging because I had to be conscious 
of the types I was dealing with. I knew the overall strategy, but I tried too 
hard to make it a one-liner. However, putting all the functionality on one line 
made it difficult to debug, especially since types didn't implicitly convert 
when I wanted them to. I realize now that I could've written string2regex in one 
line, but it's more understandable when it's split into several lines.

## Comment on the design of this internal DSL

Write a few brief paragraphs that discuss:
   + What works about this design? (For example, what things seem easy and
   natural to say, using the DSL?)

   It is easy to express regular expressions with a notation many people (in 
   this domain) are familiar with. For example, `||` is easily understood as a 
   union, and `~` is often used for concatenation. Moreover, users don't need to 
   understand the background work (i.e. Literal and Star) in order to use this 
   DSL.


   + What doesn't work about this design? (For example, what things seem
   cumbersome to say?)

   `EMPTY` and `EPSILON` look cumbersome, but it's also hard to describe them in 
   simpler terms. For EPSILON, you could use the ε character, but this symbol 
   isn't easily accessible via a normal QWERTY keyboard. In my solution, I 
   called EPSILON `e`, but `e` has different meanings in other domains (i.e. 
   Euler's number).

   Another cumbersome thing is having to use parentheses to ensure correct 
   binding even though regex intuition says otherwise. More is explained below.


   + Think of a syntactic change that might make the language better. How would
   you implement it _or_ what features of Scala would prevent you from
   implementing it? (You don't have to write code for this part. You could say
   "I would use literal extension to..." or "Scala's rules for valid
   identifiers prevent...")

   Currently, we can convert a string into a regular expression, but I want to 
   convert a regular expression into a string (i.e. `"[[a][b]|[[c][d]*]""`). 
   This seems like a pretty big task. But I can use implicit definitions to 
   convert different regular expressions into strings. For example, `Union(this, 
   that)` can implicitly be converted into `"[this]|[that]"` with string 
   interpolation.

   Another useful syntactic change is knowing how tightly bound operators are. 
   For example, `<*>` should be more tightly bound than `||`, but currently, 
   everything reads from right to left, since this is Scala's default operator 
   precedence. 

   However, operator precedence is very difficult (impossible?) to change. After 
   Googling for a bit, I found a 
   [Scala reference](http://www.scala-lang.org/files/archive/spec/2.11/06-expressions.html#prefix-operations).
   It says that infix operators are ordered by its first character. Furthermore, 
   postfix operators have lower precedence than infix operators, which means 
   `||` will unintuitively bind more tightly than `<*>`, a postfix operator. 
   Unfortunately, a fixed operator preference appears to be one of Scala's 
   downsides for the regular expressions domain, but it is probably very 
   difficult to implement a mutable operator precedence.
