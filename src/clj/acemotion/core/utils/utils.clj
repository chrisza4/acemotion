(ns acemotion.core.utils.utils)

(defn- compare-with-uuid [a b]
  (if (uuid? a)
      (.equals a b)
      (= a b)))

(defn uuid
  ([] (java.util.UUID/randomUUID))
  ([str] (try
           (java.util.UUID/fromString str)
           (catch Exception e nil))))

(defn union-vector [vector member]
  (if (some #(compare-with-uuid % member) vector)
      vector
      (conj vector member)))
