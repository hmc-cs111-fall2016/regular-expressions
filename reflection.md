# Reflection on implementing regular expressions of a DSL

## Which operators were easiest to implement and why?

Union, concatenation, and star operators were the easiest to implement. This is because there were functions already in the regex library that did these operations. As a result, the operators are just simple wrappers around a function.

## Which operators were most difficult to implement and why?
The other operators `<+>` and `{n}` were harder to implement. `<+>` is an operator not in the regex library and required a bit of thought to write the operator. The `{n}` was the hardest because it required using the apply function. From there, I had a bit of difficulty using recursion, so I used a for loop to implement it.

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


The easiest things to say are things with repetition. For me, regular expressions can be strong when using union operators with the Kleene star. One can then specify patterns that can be arbitrarily long (with the Kleene star) and relatively complex using the union operator within the Kleene star. In fact, the family of operators `<*>`, `<+>`, and `{n}` are all related to doing things with repetition. As a result, we can be very specific about our repetitions.

On the other hand, it seems that it is fairly difficult to write regular expressions when there are patterns that aren't easily represented using repetition. For example, the code we were given had the following example:

```
val digit = '0' || '1' || '2' || '3' || '4' || '5' || '6' || '7' || '8' || '9' 
```

This code made me a bit sad because there is an obvious pattern, but we were forced to union 10 literals. In general, union and concatenation operations can create clunky regular expressions. Not that they by themselves are bothersome, but when things get cumbersome, I believe these two operations are deeply involved.

Another trait about our DSL that slightly bothers me is how we have to explicitly use parentheses to correctly express our regular expression:

```
val pattern = "42" || ( ('a' <*>) ~ ('b' <+>) ~ ('c'{3}))
```

In this code, we are forced to put three pairs of parentheses to write this regular expression. It would be interesting to see if we can create some sort of rules that allows us to remove parentheses. Some parentheses can be good for clarifying the regular expressions, but for those that seem redundant, removing parentheses could be nice.

Besides removing parentheses, there are three other things I would consider modifying. One is to further simplify the syntax of the DSL. For example, it would be nice to write just a `*` in front of a regular expression like:

```
("42" || "6")*
```
to say I want 0 or more copies of what is inside the parentheses. However, I would like to mention that this change is probably not possible in Scala. It would probably be fairly difficult or impossible) to get Scala to recognize the `*` operator in the code above.

The second thing is to implement some sort of pattern generation within regular expressions. From the example above that takes the union of 10 literals 9 times, it might be nice to have something like a generator or a declarative approach that creates the values we want like in the following line:

```
val = (Integers from 1 to 8)
```

where the value inside the parentheses is supposed to represent any digits from 1 to 8.

The last thing I was thinking about was having support to not include certain expressions in a regular expression. For example, it would be nice to able modify `digit` so that it contains all digits 1-9 not including 3, 4, and 7. This was just a side thought however, so I don't want to elaborate on this idea too much. I'd hypothesize, however, that it might be difficult to implement into the DSL.
