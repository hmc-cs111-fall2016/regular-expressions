# Reflection on implementing regular expressions of a DSL

## Which operators were easiest to implement and why?

The `||` and `~` operators were easiest to implement (after figuring out the
implicit conversion code, see below) since they closely resembled the sample we
did in class with `add`/`+`. Their implementation was also a simple one-to-one call
on the predefined case classes which contributed to their level of ease.

## Which operators were most difficult to implement and why?

Although not directly an operator, the implicit conversion code (`string2regularExpression`
and `char2literal`) was difficult to implement because it required
putting the code in the appropriate *companion* class. At first, I had put
`char2literal` in a `Literal` companion object, but it required me to add type
annotations in `Program.scala` which seemed unnatural. After experimenting with
moving the code up in the object inheritance hierchy, this problem was resolved.

`string2regularExpression` came naturally after but was tricky to decide on the
basecase for the fold: should it be `EMPTY` or `EPSILON`? After creating a buggy
implementation with `EMPTY`, the distinction between `EMPTY` being a pattern
and `EPSILON` being a character came back from 81! Debugging this was a bit tricky
since it compiled but failed tests in unexpected places.

`<*>` and `<+>` being postfix operators were difficult to figure out the syntax
for. I had to recall that they (1) do not take an argument and (2) have `this`
available in their implementation scope since they are defined on the `RegularExpression`
class itself.

The `{n}` (repetition operator) implementation came naturally, but figuring out
to use `apply` took some documentation digging. At first, I simply defined
it as `regEx.repetition{47}` to ensure the logic was correct and then investigated
more Scala to figure out how I could call a method on an object without using
a name.

## Comment on the design of this internal DSL

This Regular Expression DSL has a straightforward way of saying precisely what
characters you want to include in your pattern. For instance, including only
numbers:

```
('0' || '1' || '2' || '3' || '4' || '5' || '6' || '7' || '8' || '9')*
```

can be written as above; however that being said, it would be much nicer if one
could define standard sets (like a fuller implementation of RegEx's do):

```
[0-9]*
```

At the very least, a set syntactic sugar would be nicer; writing something like:

```
{0,1,2,3,4,5,6,7,8,9}*
```

thereby ommitting characters. The above, I believe would be (almost) doable since
Scala allows a variable number of arguments for a function, although the syntax
would need to used `(`/`)` and be preceeded by some instantiation of a regular
expression object; it may end up looking like

```scala
r(0,1,2,3,4,5,6,7,8)*
```

where `r` is some placeholder object.

In the `Program.scala`, patterns were built up using native Scala values which
made it easier to have a concise syntax that `[0-9]*` aims to immitate. (i.e.
We were able to write this as `digits<*>`.)
