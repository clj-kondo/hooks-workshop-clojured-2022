# Part 2: `:macroexpand` hooks

In this stage of the workshop, still open `src/hooks_workshop/macro_usage.clj` in your editor!

The macroexpand hook lets you hook into the clj-kondo analysis using a macro
that is part of your clj-kondo configuration.

Let us revisit the (contrived) `kdefn` macro from part 1. We'll now call it
`kdefn2` because we will have a different configuration for it.

``` clojure
(defmacro kdefn2 [sym kargs & body]
  `(defn ~sym ~(vec (map symbol kargs)) ~@body))

(kdefn2 my-fn [:foo :bar] (+ foo bar))
(my-fn 1 2)
```

The config we had for `kdefn` worked great:

``` clojure
{:lint-as {hooks-workshop.macros/kdefn clj-kondo.lint-as/def-catch-all}}
```

but came with some downsides. E.g. in:

``` clojure
(kdefn my-fn [:foo :bar] (+ foo baz))
```

we would not see that `baz` was an unresolved symbol and that `bar` was an
unused binding.

Let's introduce a `:macroexpand` hook. There are two parts to this:

- An entry in `.clj-kondo/config.edn`:

``` clojure
:hooks {:macroexpand {hooks-workshop.macros/kdefn2 hooks-workshop.macros/kdefn2}}
```

- The macro code in `.clj-kondo/hooks_workshop/macros.clj_kondo`:

``` clojure
(ns hooks-workshop.macros)

(defmacro kdefn2 [sym kargs & body]
  `(defn ~sym ~(vec (map symbol kargs)) ~@body))
```

Note that the macro in the `.clj_kondo` is identical to the macro we wrote. In
real life, you will likely encounter macros that have to be adapted. Macros in
`.clj_kondo` files are executed using [SCI](https://github.com/babashka/sci), a
Clojure interpreter which allows sandboxed execution of code in the JVM and
within the `clj-kondo` binary. Because the interpreter environment is
constrained, you have to write your macro in terms of pure Clojure code. You are
not allowed to write to disk. But you are allowed to call `prn` and `println`
for debugging.

When we use `kdefn2` (as we do in `src/hooks_workshop/macro_usage.clj`), we now
see more information than with `def-catch-all`:

``` clojure
(kdefn2 my-fn [:foo :bar] (+ foo baz))
^ Unused binding bar             ^ Unresolved symbol baz
```

As you can see, we have the useful linting feedback from clj-kondo, but the
location of the unused binding is a bit off. This is because with `:macroexpand`
there is a transformation from clj-kondo's representation of nodes (objects
representing structure of the code) to s-expressions. The s-expression runs
through the macro and is then converted back into nodes. Locations are saved as
metadata on nodes. But when converting a keyword node into an s-expression, this
location information is lost. As a best-effort solution, clj-kondo moves a
warning which should be normally located at the keyword up to the nearest
list. In the above example that is the outer list with the symbol `kdefn2` as
the first element. Locations for symbols are preserved and this is why we see
the unresolved symbol `baz` warning at the expected location.

## Exercise 2.0

Open the `:macroexpand`
[documentation](https://github.com/clj-kondo/clj-kondo/blob/master/doc/hooks.md#macroexpand)
and familiarize yourself with it.

You can work on exercise 2.1 in parallel if you prefer learning by doing and
reading up when necessary.

## Exercise 2.1

TODO
