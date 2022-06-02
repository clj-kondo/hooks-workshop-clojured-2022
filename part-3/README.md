# Part 3: `:analyze-call` hooks

In this stage of the workshop, still have `src/hooks_workshop/macro_usage.clj` open in your editor!

Now we arrive at the most powerful part of clj-kondo hooks, `:analyze-call`.

The analyze-call hook offers a more precise way of handling custom macros with
accurate preservation of location information.

Clj-kondo uses [rewrite-clj](https://github.com/clj-commons/rewrite-clj) nodes
to represent code. Analyze-call hooks receive the node exactly in the form how
clj-kondo uses it.

## Exercise 3.0

Read the part in the documentation to familiarize yourself with the node
representation
[here](https://github.com/clj-kondo/clj-kondo/blob/master/doc/hooks.md#clojure-code-as-rewrite-clj-nodes).

## Exercise 3.1

Open a REPL with clj-kondo as a dependency. Run the following code:

```
(require '[clj-kondo.hooks-api :as api])
(def node (api/parse-string "(denfk foo [:x :y])"))
```

The `node` object is a rewrite-clj node which gets printed as `<list: (denfk foo [:x :y])>`.

To access the `:children` of the node, try:

```
(:children node)
```

Each node also has a `:tag` key:

`(:tag node)`

What is the tag of the `node` and each of its children?

## API

The `clj-kondo.hooks-api` namespace exposes functions to deal with nodes: predicates to check what kind of node you are dealing with and functions to create new nodes.

## Exercise 3.2

Check out the API
[here](https://github.com/clj-kondo/clj-kondo/blob/master/doc/hooks.md#api) and
play around with it in the REPL.

## Exercise 3.3

How can we transform a vector of keys to a vector of symbols?

``` clojure
(def argvec-node (api/parse-string "[:x :y]"))
```

The keyword nodes are the `:children` of the argvec-node.

Let's start with just a keyword node:

``` clojure
(def k-node (api/parse-string ":x"))
```

Inspect the location metadata:

``` clojure
(meta k-node) {:row 1, :col 1, :end-row 1, :end-col 3}
```

To create a symbol node, we can use:

``` clojure
(api/token-node 'x)
```

But given the `k-node` that represents `:x`, how do we turn it into a symbol
node while also preserving the location information?

We can use `with-meta` and re-use the metadata of the old node.
Also we can use `api/sexpr` to get the keyword as s-expression of the old node.

Now you should have sufficient information to turn `argvec-node` into a new node
that contains symbols instead of keywords, that still have the location
information of the keyword nodes.

## Configuration

