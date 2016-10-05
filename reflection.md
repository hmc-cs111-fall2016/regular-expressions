# Reflection on implementing regular expressions of a DSL

## Which operators were easiest to implement and why?

The easiest operators were `||`, `~`, and `<*>` as they are just syntactic sugar
for existing parts of our intermediate representation.

## Which operators were most difficult to implement and why?

I struggled with the implicit conversions, just because I forgot how they were
written. Additionally, I originally had incorrectly put EMPTY instead of EPSILON
in my implementation. This wasn't caught by the original test for matching "42",
but it was by the complicated patterns at the bottom. So that took slightly longer
to debug than if the original check had caught it.

## Comment on the design of this internal DSL

Write a few brief paragraphs that discuss:
   + This design seems like a simplistic version of many other regular expression
   libraries. I really like the literal conversions for clarity. It's also nice
   that we get parenthetical grouping for free since this is internal to Scala.
   + I really don't like the syntax for concatenation. In other libraries, we can
   write `ab+a` which in our DSL would be `'a' ~ 'b' <+> ~ 'a'`
   + I would like some kind of range operator. E.g. being able to write
   `'a' to 'e'` instead of `'a' || 'b' || 'c' || 'd' || 'e'`. This could be done
   by adding a new method for **Literals** `to` that takes another Literal and
   accepts all Literals within the Ascii range. It would probably have to be
   named `<to>` so as to avoid conflicts with the `to` on Chars
