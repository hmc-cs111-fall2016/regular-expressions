# Reflection on implementing regular expressions of a DSL

## Which operators were easiest to implement and why?
The binary operators were the easiest to implement.
It was very easy for me to specify the function names as the symbols for the
operator and to just call `Union` and `Concat`.
It is simply a wrapper function with symbols as the function name that 
constructs the appropriate object.

## Which operators were most difficult to implement and why?
The most difficult operator to implement is the repetition operator, where
it takes in the number of repetitions.
I was not aware of the `apply` function, so I was confused on how the 
`RegularExpression` object can take in an argument and how that calls a 
function.

I had a convoluted plan to use currying to create a function that takes in two
arguments: an operator and an integer.
Then I would use implicit conversion to convert the `RegularExpression` object
such that it can infer that it's the curry function that's been given the
concatenation operator but not the integer argument.

Thankfully, I talked to Prof. Ben and he told me about the `apply` function.
Once I was aware of this function, it was fairly straightforward to implement
the operator.

## Comment on the design of this internal DSL
It's easy to create categories of characters using the `||` operator.
For example, we have `digits` in `Program.scala`.
Since the English language has a small set of characters, relative to other
languages such as Mandarin Chinese, we can list all the characters within
a group.
This process of listing every character in the category may be tedious,
but it can be made less tedious by defining a variable which stores this 
information.

It is fairly easy to concatenate patterns where we know exactly how many 
repetitions are in the pattern.
For example, it's easy to pattern match with a US phone number, where we know
that there are exactly 10 digits.
It is also easy to concatenate patterns without a maximum number of repetitions
using the `<*>` operator.
However, it is harder to pattern match where we have a maximum number of
repetitions and we allow for 1 to `n` repetitions of the pattern.
For example, with our phone number example, we can relax our restriction and
consider phone numbers with country codes.
Since country codes can have 1 or 2 digits and assuming phone numbers have 10
digits within each country, then phone numbers can be 12 digits at most but can 
also be 11 digits long.
This can be accomplished by `digits || digits{2} || ... || digits{12}`.
Which is quite cumbersome to write for patterns with a higher maximum cap.

However, since this is an internal DSL, we can use Scala's loops to make this
easier.
For example,
```scala
var regex = digits
for (n <- 2 to 12)
  regex || digits{n}
```
But this is not as nice as writing one line of characters to create a 
regular expression.
We can create an operator with some symbol, which is implemented using a
for loop similar to the example above.

I would also prefer if we could restrict users from calling the repetition
operator using parentheses instead of curly braces.
Since we use parentheses for grouping, it would be better to not use it for
the repetition operator as well.
This would make the code easier to read as it would be clear what the 
parentheses are for.
Preferably, using parentheses for the repetition operator would result in a
syntax error.
However, we cannot do this within the host language Scala. Arguments in
Scala can be provided with either symbol.
