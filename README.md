# hooks-workshop-clojured-2022

Getting hooked on clj-kondo!

## Introduction

[Clj-kondo](https://github.com/clj-kondo/clj-kondo) is a linter and static
analyzer for Clojure/Script. It has out of the box support for syntactic
constructs in Clojure, often defined by macros. For third party macros similar
to Clojure's own, clj-kondo has an easy configuration option: `:lint-as`. But
what about macros that invent their own language? You can disable linting for
those, but what if there was something better. Clj-kondo hooks are a flexible
way to teach clj-kondo's static analyzer about any macro. Not only will
clj-kondo understand them and will you get useful linting feedback, you can
insert your own checks and linting rules. As a library author you can export
your hooks so every library user can profit. In this workshop we will learn how
to write our own hooks using real-world examples. Only a basic understanding of
Clojure is required to get hooked!

## Prerequisites

Before entering the workshop, please make sure you have the following in place.

- A Clojure REPL with the following dependencies:

``` clojure
clj-kondo/clj-kondo {:mvn/version "2022.05.31"}
```

Recommended:

- A command line installation of `clj-kondo` version `2022.05.31` or newer
- If you are using `clojure-lsp`: version `2022.05.31-17.35.50` or newer. When using Calva, this will be installed automatically.
- When using Cursive, [clojure-extras](https://plugins.jetbrains.com/plugin/18108-clojure-extras/), the newest version

See [Installation](https://github.com/clj-kondo/clj-kondo/blob/master/doc/install.md) and [Editor integration](https://github.com/clj-kondo/clj-kondo/blob/master/doc/editor-integration.md) for further details.

## Agenda

- [Part 0](part-0/README.md): team up!
- [Part 1](part-1/README.md): dealing with macros without hooks
- [Part 2](part-2/README.md): `:macroexpand` hooks
- [Part 3](part-3/README.md): `:analyze-call` hooks
- [Part 4](part-4/README.md): hooks with customized lint warnings
- [Part 5](part-5/README.md): distributing configuration and hooks
