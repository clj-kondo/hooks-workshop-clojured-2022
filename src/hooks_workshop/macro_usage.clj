(ns hooks-workshop.macro-usage
  (:require [hooks-workshop.macros :refer [when-let*
                                           kdefn
                                           kdefn2
                                           kdefn3
                                           ]]))

;; Part 1

(comment

  (when-let* [x (odd? 3)
              y (true? x)]
    :aaah) ;;=> :aaah

  (when-let* [x 1]
    (assok :foo :bar))

  )

(comment

  (kdefn my-fn [:foo :bar] (+ foo bar))
  (my-fn 1 2)

  )

;; Part 2

(comment

  (kdefn2 my-fn2 [:foo :bar] (+ foo baz))

  ;; Exercise 2.1
  (require '[prismatic.plumbing :refer [fn->]])
  (def f (fn-> inc inc (inc)))
  (f 1 2) ;; expected: invalid arity warning

  ;; Exercise 2.2
  (require '[toucan.model :refer [defmodel]])
  (defmodel User :user
    IModel
    (types [_]
           {:status :keyword}))

  User
  UserInstance

  )

;; Part 3

(comment

  (kdefn3 my-fn3 [:foo :bar] (+ foo baz))

  )
