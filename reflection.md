# Reflection on implementing regular expressions of a DSL

## Which operators were easiest to implement and why?
The Char implicit definition was the easiest to implement. This was because
it were basically already defined in the language as a Literal, it's just 
slightly clunkier to write out `Literal ('c')` rather than just `'c'` so
implicit definitions made it much easier.

## Which operators were most difficult to implement and why?
The reptition operator was the most difficult to implement. This is because,
based on how Scala is written, `'c'{3}` looks like a parameter for a character
or a parameter for a Regular Expression because we're able to change `'c'` 
implicitly. We don't want this to be a parameter, but a function, but this is not
what it looks like to Scala. So, we had to use a special property of Scala to be
able to write this.

## Comment on the design of this internal DSL
In this DSL, it works very well to write languages of characters and strings. It's
also rather easy to develop small sets of regular expressions or expressions based
on one letter or short phrase, such as `'a'` or `"ab"`. Additionally, it's very easy
to check these sets against specific members of the set thanks to RegexMatcher.

Currently, it's still rather cumbersome to use the star operator or the plus operator.
Both of these require `<>` and personally I am more used to seeing these functions without
the `<>`, which is also evident in how it is used in the README file when introducing
regular expressions. It may be possible to use the `*` and `+` operators, although rather
difficult because Char and String already have the `*` and `+` operators built in with
other uses, so we may run into some errors if we try to implement `*` and `+` instead of
`<*>` and `<+>`. This is because if we wrote `'c'*` it would be expecting another
argument to match the case of a Char with a star opertor after it, so I don't think
that Scala would allow this.

Additionally, it's still somewhat cumbersome to define a regular language quickly.
For each element, you would have to union every one and then star. 
So, even a regular language over the alphabet `'0'` and `'1'` is
`('0' || '1') <*> `. This should be easier to have with syntatic sugar. I'm not exactly
what the most natural way to describe a regular language would be, but Scala would
definitely allow it because we can have an arbitrary number of inputs, which would be
the letters of the alphabet. Internally, we could union each of the regular expressions
and then take the `<*>` of all of them together. I would choose all the inputs to be chars
so that we would have an alphabet as defined in the writeup. The one issue would be that
we could not take regular expressions as a part of the alphabet, but I think that would
prevent the user from giving it weird "alphabets" where the regular expressions given
were not actually characters. I'm not certain what I would call this method to make it
the most natural for users, but I think it would be useful.
