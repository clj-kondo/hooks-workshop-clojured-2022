(ns hooks-workshop.macro-usage
  (:require [hooks-workshop.macros :refer [when-let*]]))

;; part 1

(when-let* [x (odd? 3)
            y (true? x)]
  :aaah) ;;=> :aaah

(when-let* [x 1]
  (assok :foo :bar))
