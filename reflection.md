# Reflection on implementing regular expressions of a DSL

## Which operators were easiest to implement and why?
The Union `||` and Concatenation `~` operators were the easiest to implement. 
These simply called existing case classes Union and Concat, so they were very
straightforward.

## Which operators were most difficult to implement and why?
For me, the repitition operator was difficult to implement because I was
initially stumped by the `{}` curly braces and how to actually match curly braces
before realizing it would have to be some special method. The implementation
itself was not so difficult once realizing this.  The Star operator was also not
immediately obvious, despite its simplicity, because the Scala syntax of 
paramater-less functions are still relatively new to me. 

I would also say the string2regex implementation was a bit difficult too. I
had tried to implement it without explicitly specifying the types for things
like `EPSILON` and `charList`, but ran into issues doing so. I also probably
should have read the instructions and description of the regular language better
as it took me a while to realize that the RegEx representation of a string is
Concatenated `Literal`s.

## Comment on the design of this internal DSL
   Nice things about the DSL: 	
   
   It is certainly very nice to use the DSL for longer RegEx conditions. The
   implementation of the main example, `val pattern = "42" || ( ('a' <*>) ~ ('b' <+>) ~ ('c'{3}))` is much cleaner and intuitive using the DSL rather than the original implementation of 
   ```
   val aStar = Star(Literal('a'))
   val bPlus = Concat(Literal('b'), Star(Literal('b')))
   val pattern = Union(answer, Concat(aStar, Concat(bPlus, cThree)))`
   ```

   It's also very nice for cases with many Unions.  For example, `digit` is an
   eyesore to look at with the original implementation with "Union" repeated so
   many times and being deeply nested.  With the DSL, it is very clear what is
   meant by the condition and takes almost no deciphering to understand.

   In other words, the DSL is great for readability and succinctness. It removes repititon of longer words, which often have to be nested. It also removes
   the need of multiple line implementations for readability and provide an
   alternative way to create regular expressions readably.

   Things that don't really work: 

   I find it difficult to think of a way to say "anything". We had to define the
   digits regex ourselves, and it would be a huge burden to create one for all
   characters.  Finding a way to allow a sequence of any characters would be
   ideal. For example, how would I say "Any string that begins with 3 a's,
   anything inbetween, and ends with 3 c's"? I know I can do `a{3}` and `c{3}`,
   but there isn't a way for me to say "anything".  'Can I use an empty string?'
   is a question that instantly comes to mind that would likely come up with
   users, too. 

   This also applies to common sets or patterns. There are other common sets 
   other than `digits` that would be beneficial to easily call. For example, if 
   I wanted to say any _even_ digit, I would have to create antoher RegEx with 
   5 union operators, which seems unecessary. 
   
   I also think it is lacking negation. For example, if I wanted to say 
   "any string that has 3 a's followed by anything except c", or "a string with
   any number of a's except 2", I would probably have trouble with that. 

   Thus, two things I'd definitely want to implement are 
    1. some operator(s) that would allow representation of all characters/common 
    patterns/sets, and 
    2. a negation operator

   I feel that neither of these should be particularly challenging in Scala, and
   should be a matter of adding more case classes in the IR component to handle
   them.