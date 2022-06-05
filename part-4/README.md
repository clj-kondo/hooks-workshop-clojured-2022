# Part 4: hooks with customized lint warnings

In this stage of the workshop, still have `src/hooks_workshop/macro_usage.clj` open in your editor!

Remember that `kdefn3` (and all other `kdefn`s) takes a vector of keywords
instead of symbols:

``` clojure
(kdefn3 my-fn3 [:foo :bar] (+ foo baz))
```

A common error when calling this macro is that people pass symbols instead of
keywords. Wouldn't it be great if clj-kondo could warn you while you're typing?

This is what the `api/reg-finding!` function is for. It takes a map with
location information: `:row`, `:end-row`, `:col`, `:end-col`, a `:type` (the
linter keyword identifier), and a `:message`.

Remember that location information is stored as metadata on nodes. So to call `reg-finding!` we can just take the metadata of a node and add the `:type` and `:message` key:

``` clojure
(api/reg-finding! (assoc (meta node) :type :my-lib/my-linter :message "This is bad!"))
```

To support a custom linter, there _must_ be a configuration entry that sets the
default lint warning `:level`, which the user can tweak or turn `:off`
completely:

In `.clj-kondo/config.edn`:

``` clojure
{:linters {my-lib/my-linter {:level :warning}}}
```

Without this, clj-kondo will ignore the custom linter even if calls to `reg-finding!` were made.

When creating custom linters it is recommended to use namespaced keywords that
contain an organization name or library name. For example `clojure-lsp` has one
custom linter with the name `:clojure-lsp/unused-public-var`. This way, your
custom linter will never conflict with a name that clj-kondo or another library
chooses.

## Exercise 4.0

Let's add a custom lint warning named `:hooks-workshop/kdefn-args` which prints
a warning when people call `kdefn3` like this:

``` clojure
(kdefn3 my-fn [a])
               ^ Expected keyword, but got symbol: a
```
