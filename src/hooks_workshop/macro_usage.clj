(ns hooks-workshop.macro-usage
  (:require [hooks-workshop.macros :refer [when-let*
                                           kdefn
                                           kdefn2
                                           kdefn3
                                           fn->]]))

;; part 1

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

;; part 2

(comment

  (kdefn2 my-fn2 [:foo :bar] (+ foo baz))

  (def f (fn-> inc inc (inc)))
  (f 1 2) ;; expected: invalid arity warning
  )

;; part 3

(comment

  (kdefn3 my-fn3 [:foo :bar] (+ foo baz))

  )
