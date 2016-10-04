# Reflection on implementing regular expressions of a DSL

## Which operators were easiest to implement and why?

Union (`||`), concatenation (`~`), and star (`<*>`) were easiest to implement,
because they could be translated directly to the abstract syntax data structures `Union`, `Concat`, and `Star`, which were already provided.

## Which operators were most difficult to implement and why?

Implicit conversions could also be implemented in terms of the given data
structures (in this case `Literal`). However, I did run into some Scala issues
when implementing these. It took some trial and error and some Google-ing to
figure out that the line
```scala
import RegularExpression._
```
needs to be included in `Program` to make the implicit conversions visible to
Scala.

The operators `<+>` and `{n}` were also nontrivial to implement. For `<+>`, it
took a bit of thought to figure out how to implement the operator in terms of
the given data structures. Eventually, I realized that `pattern <+>` is the
same as `pattern ~ (pattern <*>)`. The `{n}` operator had a recursive
definition that required some thought. It also took some quick research to
figure out that I could apply an instance of a `class` as if it were a function
by defining `apply(n: Int)`.

## Comment on the design of this internal DSL

I think literal extension is one of the most powerful features of this design. Specifically, the ability to treat `Char`s and `String`s as regular expressions allows the language to integrate so smoothly into the surrounding code that the user may not even realize that they are using a DSL. I think this is a desirable property for this kind of language, which is likely intended to be used in a fragmentary way as part of a larger Scala program.

The syntax for regular expression operators is not quite as clean as the syntax for literal expressions. For example, having to use `~` for concatenation is a bit verbose, it would be nice to be able to just write one expression followed by another, as we would when formally writing regular expressions. Likewise, the star syntax `<*>` and the plus syntax `<+>` are a bit more verbose than we might like.

One change I would like to implement to improve the syntax of the language is to replace `<*>` with `*` and `<+>` with `+`. This would make the syntax more concise and more intuitive for those familiar with other regular expression DSLs. Based on the sample solution, this can be implemented by simply renaming the methods which implement this part of the syntax.

I'd also like to get rid of the `~` operator. Unfortunately, there is no way to just say something like `('0' *) ('0' || '1')` to mean "any number of zeros followed by either a 0 or a 1", because Scala does not have a syntactic construct that would allow this. Unfortunately, this is a limitation of implementing the DSL internally. I did notice that the sample solution uses the unicode `⋅` character, which is slightly less obtrusive, but still not a perfect solution to the problem.
