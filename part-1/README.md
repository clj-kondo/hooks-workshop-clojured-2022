# Part 1: dealing with macros without hooks

Before hooks existed, clj-kondo already had a few ways to deal with macros. We will explain those using an example.

Check out the awesome `when-let*` macro by Thierry Smeekes:

``` clojure
(ns hooks-workshop.macros)

(defmacro when-let*
  ([bindings & body]
   (if (seq bindings)
     `(when-let [~(first bindings) ~(second bindings)]
        (when-let* ~(drop 2 bindings) ~@body))
     `(do ~@body))))
```

It allows you to nest when-lets without the nesting:

``` clojure
(when-let* [x (odd? 3)
            y (true? x)]
 :aaah) ;;=> :aaah
```

But clj-kondo has trouble understanding the syntax. The `x` and `y` bindings
appear as unresolved symbols, as you can see when opening the file
`src/hooks_workshop/macro_usage.clj` in an editor with clj-kondo integration, or
when linting this file from the command line.

## `:unresolved-symbol`

To get rid of those unresolved symbols, we can configure clj-kondo.

Read the documentation
[here](https://github.com/clj-kondo/clj-kondo/blob/master/doc/linters.md#unresolved-symbol).

## Exercise 1

Add / change configuration in `.clj-kondo/config.edn` to get rid of the unresolved
symbols.

## `:lint-as`

When you have successfully completed the previous exercise, you will notice that
typos like `(assok :foo bar)` (`assok` instead of `assoc`) will also go
unnoticed. Also notice that the unused binding `y` isn't reported. So even
though we got rid of the false positive unresolved symbols, we also suppress
real issues.

The `:lint-as` configuration allows you to tell clj-kondo to syntactically treat
a custom macro like a built-in macro.

Read the documentation
[here](https://github.com/clj-kondo/clj-kondo/blob/master/doc/config.md#lint-a-custom-macro-like-a-built-in-macro).

Since `hooks-workshop.when-let*` is syntactically equivalent to
`clojure.core/let`, that might be a better option.

## Exercise 2

Remove the configuration of exercise one and use `:lint-as` instead.

Notice that real unresolved symbols and unused bindings will be reported now.
