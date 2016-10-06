# Reflection on implementing regular expressions of a DSL

## Which operators were easiest to implement and why?

I found operators `||` and `~` the easiset to implement, since they were
essentially just functions which take another RegularExpression.

The operators `<*>` and `<+>` were slightly more interesting, since it
required an investigation into how to define a postfix operator. I originally
thought it would require using a variant of the `unary_` modifier used for
prefix unary operators, but it turns out that the `unary_` modifier is only
used to distinguish between pre and post fix unary operators.

## Which operators were most difficult to implement and why?

I found the repetition operator (`c {3}`) more difficult, because I had
forgotten that for single argument methods, the parenthesis operator
can be exchanged with the braces, and declared using the method name `apply`.

Once I had the syntax for declaring this, it was alright.  I also
found the implicit conversion from strings to RegularExpressions a bit
difficult, since I initially wrote it as follows:
`str.foldLeft(EPSILON)((regex, c) => Concat(regex, Literal(c)))`
However, this led to the compiler error:
```scala
found    : dsls.regex.Concat
required : dsls.regex.EPSILON.type
```
I found this a bit confusing, since both EPSILON and the result of Concat
are `RegularExpressions`.  Eventually, I fixed this by declaring the type
of `EPSILON` to be a `RegularExpression`.  It seemed a little strange because
`EPSILON` *is* a `RegularExpression`, so I had expected compilation, even
without noting the return type.

## Comment on the design of this internal DSL

There were certainly improvements to the original form of the DSL.
 * The syntax for users of the DSL is certainly improved through the
 assignment.  Users are no longer required to use the Intermediate
 Representation to declare their Regular Expressions.
 * In addition to the reduction in verbosity from both literal extensions
 and operators, it is also easier to see when code is correct because
 there is less nesting when our operators are associative and thus do
 not need parentheticals.

There are several issues with the syntax of our language which remain:
 * It would be preferable if the user didn't have to put the `*` or 
 the `+` in angle brackets. However, the syntax of scala restricts us
 in this case, since a lone `*` or `+` in scala has arithmetic meaning.
 * It would also be nice if we could get rid of the tilde as a concatenation
 operator for a pair of regular expressions.

