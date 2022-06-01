(ns hooks-workshop.macros)

;; part 1
(defmacro when-let*
  "Nested `when-let`."
  ([bindings & body]
   (if (seq bindings)
     `(when-let [~(first bindings) ~(second bindings)]
        (when-let* ~(drop 2 bindings) ~@body))
     `(do ~@body))))

(defmacro kdefn
  "A contrived `defn` with keyword arguments."
  [sym kargs & body]
  `(defn ~sym ~(vec (map symbol kargs)) ~@body))

;; part 2
(defmacro kdefn2
  "Identical to `kdefn` but with a different clj-kondo config."
  [sym kargs & body]
  `(defn ~sym ~(vec (map symbol kargs)) ~@body))

(defmacro fn->
  "A macro from plumbing.core. Same as (fn [x] (-> x ~@body))."
  [& body]
  `(fn [x#] (-> x# ~@body)))
