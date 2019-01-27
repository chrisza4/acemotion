(ns acemotion.core.utils.utils)

(defn- compare-with-uuid [a b]
  (if (uuid? a)
      (.equals a b)
      (= a b)))

(defn uuid [] (java.util.UUID/randomUUID))

(defn union-vector [vector member]
  (if (some #(compare-with-uuid % member) vector)
      vector
      (conj vector member)))
