# Reflection on implementing regular expressions of a DSL

## Which operators were easiest to implement and why?
The easiest operators were the `||` and `~` ones. This is partly because
we did a very similar example in class but also because Scala makes it
very easy to create operators that equate to a created method/constructor.
I also thought it was easy since Scala lets us create infix operators very
easily (and with no syntactic sugar) so I do anything super special to maintain
fluency. 

## Which operators were most difficult to implement and why?
The `{n}` operator was by far the most difficult - I still don't think I did
it properly :). This was the most difficult because we wanted to use braces
in order to envoke the repetitions. In Scala (and many languages) braces are
a special keyword for program control flow and cant easily be overridden. I
tried many methods to overide it like using closures/curried functions or creating
an implicit conversion when we see a number and a character. I really was very
troubled by this operator. Eventually a stack overflow question (linked in the
code) helped me figure it out but I dont know if it is the proper way of doing
it.


## Comment on the design of this internal DSL
I thought what worked and was easy was to simply pick between a few strings or
characters. It is also really easy to create a string with a variable or set
number of repetitions. Lastly, one can easily combine/chain these to make larger
commands.

The difficulties with the language is that putting together a few might be doable
with little effort; however, reading the code after or sharing it with someone
else is very difficult: seeing a bunch of characters in a single pattern
is definitely not readable. Also, since we only use symbols, a non-CS/Math user
would not naturally understand the symbols compared to using words like `or`.
To extrapolate that, math or cs student who has learned set theory (discrete)
understands what the `*, +, ||` operators do and could easily read the pattern
wheras a more basic user would be confused. Lastly, it is very difficult to
include another operation within a string. For example, if we want to say the
word "boring" but want to match with strings that have a variable amount of 'o's
(since they might be reallllllllllllly bored), we would have to say:

    `val pattern = "st" ~ ('r' <*>) ~ "ing"`
which is not that simple and something like 

    `val pattern = "str*ing"
would be much nicer. 

This leads into my improvements!
Depending on the desired user base, it could be a lot simpler to overide the 
`||, ~` operators with `or, +(or and)` respectively. Also, as I brought up above
it would be great to be able to include patterns within other patterns - as in
include the `*` inside a string literal. I think scala's literal conversions
would limit this since we cant convert a literal and then evaluate it necessarily
but it would be nice. Also, Scala's rules for valid identifiers prevents us from
using `&4` as an operator to perhaps replace `{n}` which was hard to implement.
The idea is that we could say `4 &2` to represent `44`. 