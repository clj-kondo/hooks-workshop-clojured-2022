# Part 4: hooks with customized lint warnings

In this stage of the workshop, still have `src/hooks_workshop/macro_usage.clj` open in your editor!

Remember that `kdefn3` (and all other `kdefn`s) takes a vector of keywords
instead of symbols:

``` clojure
(kdefn3 my-fn3 [:foo :bar] (+ foo baz))
```

A common error when calling this macro is that people pass symbols instead of
keyword. Wouldn't it be great if clj-kondo could warn you while you're typing?

This is what the `api/reg-finding!` function is for. It takes a map with
location information: `:row`, `:end-row`, `:col`, `:end-col`, a `:type` (the
linter keyword identifier), and a `:message`.

Remember that location information is stored as metadata on nodes. So to call `reg-finding!` we can just take the metadata of a node and add the `:type` and `:message` key:

``` clojure
(api/reg-finding! (assoc (meta node) :type :my-lib/my-linter :message "This is bad!"))
```

