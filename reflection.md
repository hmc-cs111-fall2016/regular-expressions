# Reflection on implementing regular expressions of a DSL

## Which operators were easiest to implement and why?
The ||, ~, and <*> operators were the easiest to implement since they just 
required the use of existing classes - Union, Concat, and Star - to give the 
users an alternative way of calling these methods. The <+> operator was slightly 
tricker to implement since it basically required the concatenation of the regular 
expression to a multiple of itself, but it was still fairly simple to implement.

## Which operators were most difficult to implement and why?
The {n} operator was by far the most difficult to implement because it took me
a frustratingly long time to figure out how to name the method. I initially
figured it was necessary to incorporate the brackets in the method definition
but slowly realized that this was neither possible (at least to my knowledge)
nor necessary. I then struggled to search for a way to create a method that 
takes the name of its parameter. After looking through the Scala documentation
and a lot of Stackoverflow forums, I finally realized that I could use the 
built-in apply method and implement the operator using simple recursion. 

I also had some initial confusion about where to define the methods for the
literal extensions. I originally created an object called "LiternalExtension"
and imported it in the Program class, but then I looked at the final program 
code and realized that an object called "RegularExpression" should be imported.
I thought I would have trouble using implicit conversions, but reading the 
article (linked in the assignment) and some basic Google-ing made it a smooth
process.

## Comment on the design of this internal DSL
It's nice that the user can simply declare characters and strings as they are
represented in most programming languages but be able to use them as regular
expressions. The method of checking whether or not a string or character 
corresponds to the right regular expression is also pretty intuitive. In 
addition, I would say that the || operator is fairly easy to use and makes 
the code readable from the user end.

As it stands, I think the syntax for calling the <*> and <+> operators is more 
verbose than it needs to be. Though it might seem like a small detail, the 
brackets make the code harder to read and make it more tedious for the user
to write out patterns. I also don't know if ~ is the best symbol for 
concatenation, as many programmers are more used to using + for concatenating 
strings and characters. Though because + would already used as an operator,
perhaps a symbol like :: could be used instead. 

It would be much nicer for the user if they could call the <*> operator as * 
and the <+> operator as ^ (I might refrain from changing <+> to + since it is
already such a commonly used operator embedded with lots of meanings). I 
imagine this would be a relatively simple change to make since Scala has a
relaxed framework for defining methods and "overloading" operators (which is
really just like defining a regular method in a specific class). Like I 
mentioned before, I would also be interested in defining a :: operator for 
concatenation of strings and characters. It seems like this would be distinctive
enough from the other postfix and repetition operators to allow for more 
readable code, and it's also an operator that programmers are familiar with 
in the context of concatenation.