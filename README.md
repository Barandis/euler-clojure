# Euler Project algorithms in Clojure

I recently discovered [Project Euler][1] from a suggestion about things one could do to practice a new language. I've decided to find solutions to them as I can using Clojure, which is in fact a language I'm learning to use.

No actual solutions are printed inside any of these files, so if you want to use them to come up with solutions you're going have to actually run the code. Using these to help work out your own answer is perfectly fine.

If you're good at Clojure, opening an issue to tell me about things you see that are wrong or non-idiomatic would also be quite welcome. I am still quite new to this language.

This project isn't really set up to be executed as a single jar. (This may happen in the future but right now I'm just enjoying the problem solving.) Rather, the build system is used to run individual solutions. This build system is based on a deps.edn file by Practicalli and [is available here][2]. You will have to use it (or something similar) to run the individual solvers.

For example, to run the solver for Problem 757, run this from the root project directory:

```
clojure -X:p757
```

The solutions all default to the parameters necessary to solve the particular Project Euler problem, but you can pass keyword/value pairs when you run it to change the values. For example, Problem 757 works by default with the number 10^14, but the problem also lists the correct answer for 10^6. You can run the solver with that number like this:

```
clojure -X:p757 :input 1e6
```

There's not necessarily a lot of rhyme or reason to the keyword names. You may have to look at the code.

You could, of course, also run things from the REPL by loading in the appropriate file and running `(solve)`. If you want to use non-default arguments then, you can pass in a map with the appropriate keyword, as in this if you load `barandis.euler.p757` into your REPL:

```
(solve {:input 1e6})
```

The reason for the strange format is that this is the format that allows individual solutions to be run from the command line.

The list of currently solved problems follows. I just started working on this and will probably be doing a few every day for a while.

* [Problem 1: Multiples of 3 or 5][src/barandis/euler/p1.clj]
* [Problem 2: Even Fibonacci numbers][src/barandis/euler/p2.clj]
* [Problem 3: Largest prime factor][src/barandis/euler/p3.clj]
* [Problem 4: Largest palindrome product][src/barandis/euler/p4.clj]
* [Problem 5: Smallest multiple][src/barandis/euler/p5.clj]
* [Problem 757: Stealthy numbers][src/barandis/euler/p757.clj]

[1]: https://projecteuler.net/
[2]: https://practical.li/clojure/clojure-tools/install/community-tools.html
