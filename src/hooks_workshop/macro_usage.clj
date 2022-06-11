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

  (kdefn my-fn [:foo :bar] (+ foo bar baz))
  (my-fn 1 2)

  )

;; Part 2

(comment

  (kdefn2 my-fn2 [:foo :bar] (+ foo baz))

  ;; Exercise 2.1
  (require '[plumbing.core :refer [fn->]])
  (def f (fn-> inc inc (inc)))
  (f 1) ;; expected: invalid arity warning

  ;; Exercise 2.2
  (require '[toucan.models :refer [defmodel IModel]])
  (defmodel User :user
    IModel
    (types [_]
           {:status :keyword}))

  #_'(do
       (def User ,,,)
       (defrecord UserInstance []
         IModel ;; & args
         (types [_]
           {:status :keyword})))

  User
  UserInstance

  )

;; Part 3

(comment

  (kdefn3 my-fn3 [:foo :bar baz] (+ foo baz))
  (inc :foo)
  )
