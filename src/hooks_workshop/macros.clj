(ns hooks-workshop.macros)

;; part 1
(defmacro when-let*
  ([bindings & body]
   (if (seq bindings)
     `(when-let [~(first bindings) ~(second bindings)]
        (when-let* ~(drop 2 bindings) ~@body))
     `(do ~@body))))

(defmacro kdefn [sym kargs & body]
  `(defn ~sym ~(vec (map symbol kargs)) ~@body))
