# Part 1: dealing with macros without hooks

Before hooks existed, clj-kondo already had a few ways to deal with macros. We will explain those using an example.

Check out the `when-let*` macro:

``` clojure
(ns hooks-workshop.macros)

(defmacro when-let*
  "Nested when-let."
  ([bindings & body]
   (if (seq bindings)
     `(when-let [~(first bindings) ~(second bindings)]
        (when-let* ~(drop 2 bindings) ~@body))
     `(do ~@body))))
```

It allows you to use multiple binding and conditions, without nesting `when-let`
expressions:

``` clojure
(when-let* [x (odd? 3)
            y (true? x)]
 :aaah) ;;=> :aaah
```

But clj-kondo has trouble understanding the syntax. The `x` and `y` bindings
appear as unresolved symbols, as you can see when opening the file
`src/hooks_workshop/macro_usage.clj` in an editor with clj-kondo integration, or
when linting this file from the command line.

You can lint on the command line with `clj-kondo --lint <file>` (see [docs](https://github.com/clj-kondo/clj-kondo#command-line)) or with `clj
-M:clj-kondo <file>` (see [docs](https://github.com/clj-kondo/clj-kondo/blob/master/doc/jvm.md#toolsdepsalpha)).

## Exercise 1.0

**Learn about `:unresolved-symbol` linter**

Open `src/hooks_workshop/macro_usage.clj` in your editor and notice that there
are lint warnings in the `comment` section of part 1.

## `:unresolved-symbol`

To get rid of those unresolved symbols, we can configure clj-kondo to suppress unresolved symbols. We can do so using the `:linters {:unresolved-symbol {:exclude ...}}` configuration.

Read about its documentation
[here](https://github.com/clj-kondo/clj-kondo/blob/master/doc/linters.md#unresolved-symbol).

## Exercise 1.1

**Apply `:unresolved-symbol` linter exclusion**

Add / change configuration in `.clj-kondo/config.edn` to get rid of the unresolved
symbols.

## `:lint-as`

When you have successfully completed exercise 1.0, you will notice that
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

## Exercise 1.2

Remove the configuration of exercise 1 and use `:lint-as` instead.

Notice that there will be no false positives and real issues like unresolved
symbols and unused bindings will be reported correctly!

## `def-catch-all`

There is a special value supported in `:lint-as`, `clj-kondo.lint-as/def-catch-all` that is a mix of `:lint-as` and `:unresolved-symbol :exclude`.

``` clojure
{:lint-as {hooks-workshop.macros/kdefn clj-kondo.lint-as/def-catch-all}}
```

It works for custom `def` or `defn` macros, by treating the first symbol as a
newly introduced var and will ignore any unresolved symbols in the body of the
call.

Behold the awesome `kdefn` macro which is the similar to as `defn`, but uses
keywords as argument bindings:

``` clojure
(defmacro kdefn [sym kargs & body]
  `(defn ~sym ~(vec (map symbol kargs)) ~@body))
```

Unfortunately `:lint-as` + `clojure.core/defn` won't work here, since clj-kondo
still expects symbols inside the argument vector. But using the above config, clj-kondo will not emit any warnings about this:

``` clojure
(kdefn my-fn [:foo :bar] (+ foo bar))
(my-fn 1 2)
```

## Exercise 1.3

Make up a custom `def` macro, put it inside `src/hooks_workshop/macros.clj`, use it from `src/hooks_workshop/macro_usage.clj`. Then use `def-catch-all` to fix linting.
