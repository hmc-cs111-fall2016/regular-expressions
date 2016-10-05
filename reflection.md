# Reflection on implementing regular expressions of a DSL

## Which operators were easiest to implement and why?

All of the operators were easier to implement than expected.  Scala seems
to provide the coder with lots of freedom for choosing operators to define or
overload.  I would say that the operators ||, ~, and <*> were the easiest to
implement, since they were direct translations from the classes Union, Concat, and
Star, respectively.  After this, <+> only involved Concat-ing `this` with 
`Star(this)`

## Which operators were most difficult to implement and why?

The most difficult to implement was probably the repetition operator.  The most
difficult part was interpreting `'3'{7}` as a function of a literal that took an
integer as an argument literal, and was equivalent to `myExpression(7)`.  After
that, I had to do some googling before I found out that we were supposed to define
a function `apply` that would work as parentheses.  Finally, implementing it
involved recursion, and was much less trivial than the other operators.

Apart from the `apply` operator, I had a difficult time testing the implicit
conversions with the sbt console.  I wanted interact with the expressions, so I
opened sbt, typed `console`, and then did `import dsls.regex._`.  With this
setting, it would properly do the type conversions for `Literal('3') || '4'`, but
not for `'4' || Literal('3')`.  After much struggle, I resolved this issue (thanks
to advice from Jeb) by instead using  `import dsls.regex.RegularExpression._`,
which brought the implicit  conversion into scope. However, I'm not sure why
`Literal('3') || '4'` underwent the proper conversions.  From this experience, I
learned that there is lots of nuance in Scala's implicit conversion system, and it
is important to thoroughly understand how it works to prevent confusing bugs.

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

As a whole, I think the design is intuitive and not very verbose.  In particular,
|| seems like a natural union operator because of its other CS definitions.  * Is
a pretty common "zero or more" operator, and + has an expected behavior from other
languages.  In addition, the literal extensions work well to save the user from
concatenating many characters.

On the otherhand, the design differs from other regex languages.  I'm not sure why
we used the `<*>` operator, instead of just `*`, which is more common.  Same
applies to `<+>`.  More problematic, though, was the concatenation operator. 
Ideally, we would be able to concatenate expressions by simply writing them next
to each other.  However, I'm not sure we would have been able to implement this
behavior in Scala.  Also, it felt verbose using quotes to make a longer
expression.  If I were actually implementing a regex DSL, I would probably have
the user type a string, which would be parsed into a tree of Unions, Concats, and
Literals.  This way, instead of writing `("hello" <*>) ~ "world"`, the user could
write something like `(hello)*world` (I believe regex in sublime works this way). 
However, this format would not take advantage of Scala's powerful operator
defining or implicit type conversions.

On the other hand, this implementation has some benefits.  First of all, there is
no need for escape characters.  In other regex languages, if you parsed `*` as the
star operator, then in order to actually search for an asterisk, you would need an
escape character like `\`.  With our design, the operators were not part of
strings, so no distinction was needed.  In addition, this design was good for
building up complicated expressions or several different expressions with the same
subexpressions.  If you were trying to match 10 different patterns, you could
seperately define each subexpression, and then create one expression that is the
union of all of them.  If you instead implemented regex by parsing a user's
string, then the user could also create several subexpressions as strings and then
concatenate the strings.  However, any one of these strings could have an error,
it would be very hard to know which subexpression caused it.
